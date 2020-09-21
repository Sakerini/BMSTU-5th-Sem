"use strict"

const readlineSync = require('readline-sync');
const fs = require('fs');

const fileExtension = readlineSync.question("Input file extension: ")
const folderPath = readlineSync.question("input path to folder for scan:")

const files = fs.readdirSync(folderPath);

for(let i = 0; i < files.length; i++) {
    const fileName = files[i];
    if (fileName.includes(fileExtension)) {
        console.log(fileName);
    }
}