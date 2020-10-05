"use strict"

const readlineSync = require('readline-sync');
const fs = require('fs');

const folderPath = readlineSync.question("input path to folder for scan:")

const fileNames = fs.readdirSync(folderPath);

function checkIfDir(fileName) {
    try {
        fs.readFileSync(fileName, "utf8");
        return false
    } catch (error) {
        return true;
    }
}

const extension = ".txt"
function getAllFiles(folderPath, extension) {
    const fileNames = fs.readdirSync(folderPath);
    for(let i = 0; i < fileNames.length; i++) {
        const fileName = fileNames[i];
        /*
        if (checkIfDir(fileName)) {
            getAllFiles(folderPath + fileName + "/", extension)
        }
        */
        
        if (fileName.includes(extension)) {
            console.log(fileName);
        } else if (!fileName.includes(".")) {
            getAllFiles(folderPath + fileName + "/", extension)
        }
    }
}

getAllFiles(folderPath, extension)




