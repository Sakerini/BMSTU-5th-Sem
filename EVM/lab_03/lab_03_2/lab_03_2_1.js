"use strict"

// импорт библиотеки
const express = require("express");

// запускаем сервер
const app = express();
const port = 5015;
app.listen(port);
console.log(`Server on port ${port}`);

// активируем шаблонизатор
app.set("view engine", "hbs");

// заголовки в ответ клиенту
app.use(function(req, res, next) {
    res.header("Cache-Control", "no-cache, no-store, must-revalidate");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    res.header("Access-Control-Allow-Origin", "*");
    next();
});

const gamesLib = [
    {a: "Counter-Strike", b: "16"},
    {a: "GTA V", b: "18"},
    {a: "Left 4 Dead", b: "18"},
    {a: "Need For Speed", b: "12"},
    {a: "FIFA 20", b: "8"},
    {a: "World of Warcraft", b: "12"}
]

app.get("/games", function(request, response) {
    const gamesArray = gamesLib.slice()
    const infoObject = {
        descriptionValue: "Игры",
        gamesArray
    };
    response.render("games.hbs", infoObject);``
});

app.get("/games/:age", function(request, response) {
    let gamesArray = [];

    for (let i = 0; i < gamesLib.length; i++) {
        if (parseInt(gamesLib[i].b) == parseInt(request.params.age))
        gamesArray.push(gamesLib[i])
    }

    console.log(gamesArray);

    const infoObject = {
        descriptionValue: "Игры",
        gamesArray
    };
    response.render("games.hbs", infoObject);
});