"use strict";

const express = require("express");
const request = require("request");
const fs = require("fs");


const app = express();
const port = 5017;
app.listen(port);
console.log("Server C on port " + port);

app.use(function(req, res, next) {
    res.header("Cache-Control", "no-cache, no-store, must-revalidate");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    res.header("Access-Control-Allow-Origin", "*");
    next();
});

app.get("/add/warehouse", function(request, response) {
    const contentString = fs.readFileSync("input.html", "utf8");
    response.end(contentString);
});

app.get("/find/warehouse", function(request, response) {
    const contentString = fs.readFileSync("getWarehouse.html", "utf8");
    response.end(contentString);
});

app.get("/add/car", function(request, response) {
    const contentString = fs.readFileSync("addCar.html", "utf8");
    response.end(contentString);
});

app.get("/find/car", function(request, response) {
    const contentString = fs.readFileSync("getCar.html", "utf8");
    response.end(contentString);
});

app.get("/insert/warehouse", function(request, response) {
    const wareHouseName = request.query.warehouse;
    const fields = request.query.field
    
    sendPost("http://localhost:5016/insert/record", JSON.stringify({
        wareHouseName: wareHouseName,
        fields: fields
    }), function(answerString) {
        response.end(answerString);
    });
});


app.get("/select/warehouse", function(request, response) {
    const wareHouseName = request.query.warehouse;

    sendPost("http://localhost:5016/select/record", JSON.stringify({
        warehouse: wareHouseName,
    }), function(answerString) {
        response.end(answerString);
    });
});


app.get("/insert/car", function(request, response) {
    const model = request.query.model;
    const price = request.query.price;

    sendPost("http://localhost:5015/insert/record", JSON.stringify({
        model: model,
        price: price
    }), function(answerString) {
        response.end(answerString);
    });
});


app.get("/select/car", function(request, response) {
    const model = request.query.model;

    sendPost("http://localhost:5015/select/record", JSON.stringify({
        model: model
    }), function(answerString) {
        response.end(answerString);
    });

});

function sendPost(url, body, callback) {
    // задаём заголовки
    const headers = {};
    headers["Cache-Control"] = "no-cache, no-store, must-revalidate";
    headers["Connection"] = "close";
    // отправляем запрос
    request.post({
        url: url,
        body: body,
        headers: headers,
    }, function (error, response, body) {
        if(error) {
            callback(null);
        } else {
            callback(body);
        }
    });
}