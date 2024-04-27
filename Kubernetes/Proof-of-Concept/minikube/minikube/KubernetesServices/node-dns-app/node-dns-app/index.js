const express = require('express')
const dns = require('node:dns');

const app = express()
const port = process.env.PORT || 3000
const utc = new Date().toUTCString()
const domain = process.env.DOMAIN || 'www.github.com'

async function start() {

  let records = []

  const getCnames = (enodata, result) => {
    if (enodata) {
      return records;
    } else {
      const cname = result[0];
      records.push(cname);
      return dns.resolveCname(cname, getCnames);
    }
  }

  dns.resolveCname(`${domain}`, getCnames);

  app.get('/', async (req, res) => {
    console.log(records);
    res.send("Node.js Version: " + process.version + " - " + "Coordinated Universal Time (UTC): " + utc + "\n" + "<br/><br/>" + "\n" + `${records}` + "\n")
  });

  app.listen(port, () => {
    console.log(`App listening on http://localhost:${port}`)
  })
}

start()
