"use strict";

const fs = require("fs");

const express = require("express");

const app = express();
const port = 5015;
app.listen(port);
console.log("My server on port " + port);

app.get("/me/page", function(request, response) {
    const nameString = request.query.p;
    if (fs.existsSync(nameString)) {
        const contentString = fs.readFileSync(nameString, "utf8");
        response.end(contentString);
    } else {
        const contentString = fs.readFileSync("bad.html", "utf8");
        response.end(contentString);
    }
});

app.get("/find/max", function(request, response) {
    const a = request.query.a;
    const b = request.query.b;
    const c = request.query.c;

    const aInt = parseInt(a);
    const bInt = parseInt(b);
    const cInt = parseInt(c);
    const answer = Math.max(Math.max(aInt,bInt), cInt);
    const answerJSON = JSON.stringify({result: answer});
    response.end(answerJSON);
});

app.get("/find/index", function(request, response) {
    const index = request.query.index;
    const indexInt = parseInt(index);

    const fileName = "test.json";
    const fileContent = fs.readFileSync(fileName, "utf8");

    const jsonObj = JSON.parse(fileContent)
    //console.log(jsonObj.results[indexInt]);

    const answerJSON = JSON.stringify({result: jsonObj.results[indexInt]});
    response.end(answerJSON);
});

app.get("/find/points", function(request, response) {
    const a = request.query.a;
    const b = request.query.b;
    const c = request.query.c;

    const aInt = parseInt(a);
    const bInt = parseInt(b);
    const cInt = parseInt(c);

    let numbers = []
    for (let i = aInt; i <= bInt; i++) {
        if (i % cInt == 0) {
            numbers.push(i);
        }
    }
    const answerJSON = JSON.stringify({result: numbers});
    response.end(answerJSON);
});