"use strict"

const readlineSync = require('readline-sync');
const numberOfLines = parseInt(readlineSync.question(
    "How many files would you like to input: "));

const files = []

for (let i = 0; i < numberOfLines; i++) {
    files.push(readlineSync.question("Enter file name: "))
}

let bigBigString = "";


const fs = require("fs");

// Its not very optimal because strings are IMMUTABLE
for(let i = 0; i < files.length; i++) {
    bigBigString += fs.readFileSync(files[i], "utf-8")
}
console.log(bigBigString);