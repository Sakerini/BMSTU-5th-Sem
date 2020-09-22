"use strict";

const readlineSync = require('readline-sync');
const fs = require("fs");


const fields = [];

let value = true;

while(value !== "") {
    value = readlineSync.question("Input field name: ");
    if(value !== "") fields.push(value);
}

const address = readlineSync.question("Input address: ");

for(let i = 0; i < fields.length; i++) {
    const message = i + ") " + fields[i];
    console.log(message);
}


function buildRequestHtmlForm() {
    var formTittle = "<form method=\"GET\" action=\""+ address> +"\">"
    var formBody = "";

    for (let i = 0; i < fields.length; i++) {
        formBody += "<p>" + fields[i] + "</p>" +
        "<input name =\"" + i + "\" spellcheck=\"false\" autocomplete=\"off\">"
    }
    formBody += "<br><br><input type=\"submit\" value=\"Отправить запрос\"></form>"

    return formTittle+formBody
}

function buildHtml(req) {
    var header = "<meta charset=\"UTF-8\"><title>Сгенерированый HTML</title>";
    var form = buildRequestHtmlForm();
    var body = form;
  
    // concatenate header string
    // concatenate body string
  
    return '<!DOCTYPE html>'
         + '<html><head>' + header + '</head><body>' + body + '</body></html>';
  };

let htmlDoc = buildHtml();

const fileName = "test.html";
fs.writeFileSync(fileName, htmlDoc);

console.log("Create File OK");