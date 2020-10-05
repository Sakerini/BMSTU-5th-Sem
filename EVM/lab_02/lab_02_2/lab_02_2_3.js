"use strict";

const readlineSync = require('readline-sync');
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

app.get("/input", function(request, response) {
    const address = request.query.address;
    let fields = request.query.field

    let htmlDoc = buildHtml(address, fields);
    const fileName = "test.html";
    fs.writeFileSync(fileName, htmlDoc);
    console.log("Create File OK");

    const contentString = fs.readFileSync("test.html", "utf8");
    response.end(contentString);
});

function buildRequestHtmlForm(address, fields) {
    var formTittle = "<form method=\"GET\" action=\""+ address +"\">"
    var formBody = "";

    if (!Array.isArray(fields)) {
        formBody += "<p>" + fields + "</p>" +
        "<input name =\"" + 1 + "\" spellcheck=\"false\" autocomplete=\"off\">"
    } else {
        for (let i = 0; i < fields.length; i++) {
            formBody += "<p>" + fields[i] + "</p>" +
            "<input name =\"" + i + "\" spellcheck=\"false\" autocomplete=\"off\">"
        }
    }
    formBody += "<br><br><input type=\"submit\" value=\"Отправить запрос\"></form>"
    console.log(formTittle)
    return formTittle+formBody
}

function buildHtml(address, fields) {
    var header = "<meta charset=\"UTF-8\"><title>Сгенерированый HTML</title>";
    var form = buildRequestHtmlForm(address, fields);
    console.log(form)
    var body = form;
    return '<!DOCTYPE html>'
         + '<html><head>' + header + '</head><body>' + body + '</body></html>';
  };