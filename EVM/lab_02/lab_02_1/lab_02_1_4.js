"use strict"

const readlineSync = require('readline-sync');
const fs = require('fs');

const folderPath = readlineSync.question("input path to folder for scan:")

const fileNames = fs.readdirSync(folderPath);

const extension = ".txt"
function getAllFiles(folderPath, extension) {
    const fileNames = fs.readdirSync(folderPath);
    for(let i = 0; i < fileNames.length; i++) {
        const fileName = fileNames[i];
        if (fileName.includes(extension)) {
            console.log(fileName);
        } else if (!fileName.includes(".")) {
            getAllFiles(folderPath + fileName + "/", extension)
        }
    }
}

getAllFiles(folderPath, extension)

