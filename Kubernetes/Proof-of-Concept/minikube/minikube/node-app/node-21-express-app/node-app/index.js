const express = require('express')

const app = express()
const port = process.env.PORT || 3000
const utc = new Date().toUTCString()

app.get('/', (req, res) => {
  res.send("Node.js Version: " + process.version + " - " + "Coordinated Universal Time (UTC): " + utc + "\n")
});

app.listen(port, () => {
  console.log(`App listening on http://localhost:${port}`)
});
