const path = require('path')
const express = require('express')
const { MongoClient } = require('mongodb')
const multer = require('multer')
const { marked } = require('marked')

// minio
const minio = require('minio')

const app = express()
const port = process.env.PORT || 3000
const mongoURL = process.env.MONGO_URL || 'mongodb://localhost:27017/dev'
const client = new MongoClient(mongoURL)

// minio
const minioHost = process.env.MINIO_HOST || 'localhost'
const minioBucket = 'image-storage'

async function initMongo() {
  console.log('Initialising MongoDB...')
  let success = false
  while (!success) {
    try {
      await client.connect()
      success = true
    } catch {
      console.log('Error connecting to MongoDB, retrying in 1 second')
      await new Promise(resolve => setTimeout(resolve, 1000))
    }
  }
  console.log('MongoDB initialised')
  return client.db(client.s.options.dbName).collection('notes')
}

// minio
async function initMinIO() {
  console.log('Initialising MinIO...')
  const client = new minio.Client({
    endPoint: minioHost,
    port: 9000,
    useSSL: false,
    accessKey: process.env.MINIO_ACCESS_KEY,
    secretKey: process.env.MINIO_SECRET_KEY,
  })
  let success = false
  while (!success) {
    try {
      if (!(await client.bucketExists(minioBucket))) {
        await client.makeBucket(minioBucket)
      }
      success = true
    } catch {
      await new Promise(resolve => setTimeout(resolve, 1000))
    }
  }
  console.log('MinIO initialised')
  return client
}

async function start() {
  const db = await initMongo()
  
  // minio
  const minio = await initMinIO()

  app.set('view engine', 'pug')
  app.set('views', path.join(__dirname, 'views'))
  app.use(express.static(path.join(__dirname, 'public')))
  app.use(express.static(path.join(__dirname, '/images')))

  app.get('/', async (req, res) => {
    res.render('index', { notes: await retrieveNotes(db) })
  })

  app.post(
    '/note',
    
    // minio
    multer({ storage: multer.memoryStorage() }).single('image'),
    
    async (req, res) => {
      if (!req.body.upload && req.body.description) {
        await saveNote(db, { description: req.body.description })
        res.redirect('/')
      } else if (req.body.upload && req.file) {
      
        // minio
        await minio.putObject(
          minioBucket,
          req.file.originalname,
          req.file.buffer,
        )
        
        // minio
        const link = `/img/${encodeURIComponent(req.file.originalname)}`
        
        res.render('index', {
          content: `${req.body.description} ![](${link})`,
          notes: await retrieveNotes(db),
        })
      }
    },
  )

  // minio
  app.get('/img/:name', async (req, res) => {
    const stream = await minio.getObject(
      minioBucket,
      decodeURIComponent(req.params.name),
    )
    stream.pipe(res)
  })

  app.listen(port, () => {
    console.log(`App listening on http://localhost:${port}`)
  })
}

async function saveNote(db, note) {
  await db.insertOne(note)
}

async function retrieveNotes(db) {
  const notes = await db.find().toArray()
  const sortedNotes = notes.reverse()
  return sortedNotes.map(it => ({ ...it, description: marked(it.description) }))
}

start()
