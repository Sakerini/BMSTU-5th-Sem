"use strict";

const express = require("express");
const fs = require("fs");


const app = express();
const port = 5015;
app.listen(port);
console.log("Server A on port " + port);

const fileName = "records.json"

app.use(function(req, res, next) {
    res.header("Cache-Control", "no-cache, no-store, must-revalidate");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    res.header("Access-Control-Allow-Origin", "*");
    next();
});

function loadBody(request, callback) {
    let body = [];
    request.on('data', (chunk) => {
        body.push(chunk);
    }).on('end', () => {
        body = Buffer.concat(body).toString();
        callback(body);
    });
}

app.post("/insert/record", function(request, response) {

    loadBody(request, function(body) {
        const obj = JSON.parse(body);
        const model = obj.model
        const price = obj.price
        const readRecords = fs.readFileSync(fileName, "utf8");
        let recordsObj = JSON.parse(readRecords);
        
        recordsObj[model] = price;

        fs.writeFileSync(fileName, JSON.stringify(recordsObj))
        response.end(body);
    });
});


app.post("/select/record", function(request, response) {

    loadBody(request, function(body) {
        const obj = JSON.parse(body);
        const model = obj.model
        const readRecords = fs.readFileSync(fileName, "utf8");
        let recordsObj = JSON.parse(readRecords);
        response.end(JSON.stringify({model: model,
                                    price: recordsObj[model]}));
    });
});