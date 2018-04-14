const express = require('express')
const bodyParser = require('body-parser')
const app = express()

var mysql = require('mysql')

var connection = mysql.createConnection({
    host: '192.168.0.94',
    user: "root",
    password: "kramptopedal",
    database: "db"
})



app.listen(3000, function() {
    console.log('3000')
    connection.connect()
})

app.get('/', function(req, res){
    res.send('ye')
})

app.get('/test', function(){
    connection.query('INSERT INTO users (login, password) VALUES ("kramp","asd");', function(err, result){
        if (err) throw err

        console.log('1 row added');
    })
})

app.get('/dej', function(){
    connection.query('SELECT * FROM users', (err, result, fields) => {
        if (err) throw err

        console.log('result: ')
        console.log(result[0].login)
    })
})