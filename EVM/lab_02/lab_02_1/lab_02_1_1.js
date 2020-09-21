"use strict";

class Message {
    constructor(message){
        this.message = message;
    }
}
const readlineSync = require('readline-sync');
const numberOfLines = parseInt(readlineSync.question(
    "How many strings would you like to input: "));

const messages = []

for (let i = 0; i < numberOfLines; i++) {
    messages.push(new Message(readlineSync.question("Input string: ")))
}

const jsonString = JSON.stringify(messages);
console.log(jsonString)

const fs = require("fs");

fs.writeFileSync("result.txt", jsonString);

console.log("Create File OK");