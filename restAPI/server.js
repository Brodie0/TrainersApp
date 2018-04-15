const express = require('express')
const bodyParser = require('body-parser')
const app = express()

var mysql = require('mysql')

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended : false}))

var connection = mysql.createConnection({
    host: '192.168.43.196',
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

app.get('/users', function(req, res, next){
    connection.query('SELECT * FROM users', (err, result, fields) => {
        if (err) throw err
        
        res.json({result})
    })
})

app.get('/users/:id', (req, res, next) => {
    connection.query('SELECT * FROM users WHERE id=' + req.params.id, (err, result, fields) => {
        if(err) throw err
        console.log(req.params.id)
        res.json(result[0])
    })
})

app.post('/users', (req, res, next) => {
    connection.query(`SELECT * FROM users WHERE login="${req.body.login}"`, (err, result, fields) => {
        console.log(result)
        if(result == undefined || result.length == 0){
            
            connection.query(`INSERT INTO users(login, password, email) VALUES ("${req.body.login}","${req.body.password}", "${req.body.email}");`, (err, result) => {
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

app.get('/history/:id', (req, res, next) => {
    connection.query(`SELECT users.name, users.lastname, history.distance, history.total_time, history.workout_date FROM history INNER JOIN users ON history.partner=users.id WHERE history.user=` + req.params.id, (err, result, fields) => {
        if(err) throw err
        res.json(result);
    })
})

app.get('/history/', (req, res, next) => {
    connection.query('SELECT * FROM history', (err, result, fields) => {
        if(err) throw err

        res.json(result)
    })
})

app.post('/checkUser', (req, res, next) => {
    connection.query(`SELECT * FROM users WHERE login="${req.body.login}" AND password="${req.body.password}"`, (err, result, fields) => {
        if(result == undefined || result.length == 0){
            res.json({"status" : false})
        } else {
            res.json({"status" : true, "id":result[0].id})
        }
    })
})

app.get('/activeUsers', (req, res, next) => {
    console.log("asdasdas")
    connection.query('SELECT users.name, users.lastname, activeUsers.longt, activeUsers.latt FROM activeUsers INNER JOIN users ON activeUsers.user=users.id', (err, result, fields) => {
        if(err) throw err

        res.json(result)
    })
})

app.get('/activeUsers/:id', (req, res, next) => {
    console.log("DEBIG ID")
    console.log(req.params.id)
    connection.query('SELECT users.name, users.lastname, activeUsers.longt, activeUsers.latt FROM activeUsers INNER JOIN users ON activeUsers.user=users.id WHERE activeUsers.id=' + req.params.id, (err, result, fields) => {
        if(err) throw err
       
        res.json(result[0])
    })
})

app.post('/update', (req, res, next) => {
    connection.query(`UPDATE activeUsers SET longt=${req.body.longt}, latt=${req.body.latt} WHERE user=${req.body.user}`, (err, result) => {
        connection.query(`SELECT * FROM requests WHERE requestee=${req.body.id}`, (err, result, fields) => {
            if(err) throw err

            res.json(result)
        })
    })
})