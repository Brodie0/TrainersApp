const express = require('express')
const bodyParser = require('body-parser')
const app = express()

var mysql = require('mysql')

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended : false}))

var connection = mysql.createConnection({
    host: '172.20.10.4',
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

app.get('/users', function(req, res, next){
    connection.query('SELECT * FROM users', (err, result, fields) => {
        if (err) throw err
        
        res.json({result})
    })
})

app.get('/users/:id', (req, res, next) => {
    connection.query('SELECT * FROM users WHERE user_id=' + req.params.id, (err, result, fields) => {
        if(err) throw err
        console.log(req.params.id)
        res.json(result[0])
    })
})

app.post('/users', (req, res, next) => {
    connection.query(`SELECT * FROM users WHERE login="${req.body.login}"`, (err, result, fields) => {
        console.log(result)
        if(result == undefined || result.length == 0){
            
            connection.query(`INSERT INTO users(login, password) VALUES ("${req.body.login}","${req.body.pass}");`, (err, result) => {
                if (err) throw err
                
                console.log('user added')
                res.json({'message' : 'user added'})
            })   
        
        } else {
            console.log('user login exists')
            res.json({'message' : 'user login exists'})
        }
    })  
})

app.put('/updateName', (req, res, next) => {
    connection.query(`UPDATE users SET name="${req.body.name}", lastname="${req.body.lastname}" WHERE login="${req.body.login}";`, (err, result) => {
        if(err) throw err

        console.log('user name updated')
    })
})

app.put('/updatePass', (req, res, next) => {
    connection.query(`UPDATE users SET password="${req.body.pass}" WHERE login="${req.body.login}";`, (err, result) => {
        if (err) throw err

        console.log('user pass updated')
    })
})

app.post('/newRecord', (req, res, next) => {
    connection.query(`INSERT INTO history(user,partner,distance,total_time) VALUES("${req.body.user}", "${req.body.partner}", "${req.body.distance}", "${req.body.time}");`, (err, result) => {
        if (err) throw err

        console.log('record added')
    })
})

app.get('/activeUsers', (req, res, next) => {
    connection.query('SELECT * FROM activeUsers;', (err, result, fields) => {
        if(err) throw err

        res.json(result)
    })
})