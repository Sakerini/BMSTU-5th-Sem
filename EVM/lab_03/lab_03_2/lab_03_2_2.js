"use strict"

class User{
    constructor(login, password, age, hobby) {
        this.login = login;
        this.password = password;
        this.age = age;
        this.hobby = hobby;
    }
}

let users = {};
let user1 = new User("qwerty", "12345", "football", 16);
let user2 = new User("rikobomba", "9912", "swimming", 10);
let user3 = new User("letherboy", "ziron", "gaming", 21);
let user4 = new User("shakerito", "aarara", "gaming", 18);
let user5 = new User("userwithoutpasword", "password", "football", 16);
let user6 = new User("unknown", "5555", "football", 30);

users[user1.login] = user1
users[user2.login] = user2
users[user3.login] = user3
users[user4.login] = user4
users[user5.login] = user5
users[user6.login] = user6

// импорт библиотеки
const express = require("express");
const fs = require("fs");

const cookieSession = require("cookie-session");

// запускаем сервер
const app = express();
const port = 5015;
app.listen(port);
console.log(`Server on port ${port}`);

app.use(function(req, res, next) {
    res.header("Cache-Control", "no-cache, no-store, must-revalidate");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use(cookieSession({
    name: 'session',
    keys: ['hhh', 'qqq', 'vvv'],
  
    // Cookie Options
    maxAge: 24 * 60 * 60 * 1000 // 24 hours
  }))

app.get("/login", function(request, response) {
    const nameString = "login.html";
    if (fs.existsSync(nameString)) {
        const contentString = fs.readFileSync(nameString, "utf8");
        response.end(contentString);
    } else {
        const contentString = fs.readFileSync("bad.html", "utf8");
        response.end(contentString);
    }
});

app.get("/account", function(request, response) {
    if(!request.session.login) return response.redirect("/login")
    if(!request.session.password) return response.redirect("/login")

    let username = request.session.login
    let age = users[username].age
    let hobby = users[username].hobby

    const infoObject = {
        descriptionValue: "Информация",
        username,
        age,
        hobby
    };
    response.render("account.hbs", infoObject);
});

app.get("/login_process", function(request, response) {
     // получаем параметры запроса
     const login = request.query.login;
     const password = request.query.password;
     if (users[login]) {
         if (users[login].password == password) {
            request.session.login = login;
            request.session.password = password;
            response.redirect("/account")
         }
         response.end("Wrong Password")
    }
     response.end("Not Registered");
});

app.get("/logout", function(request, response) {
    request.session = null;
    response.redirect("/login")
});