"use strict";

function isVowel(char)
{
  if (char.length == 1)
  {
    var vowels = new Array('a','e','i','o','u', 'A', 'E', 'I', 'O', 'U');
    var isVowel = false;

    for(let e in vowels)
    {
      if(vowels[e] == char)
      {
        isVowel = true;
      }
    }

    return isVowel;
  }
}

function onlyVowels(string) {
    for (let i = 0; i < string.length; i++) {
        if (!isVowel(string[i])) {
            return false
        }
    }
    return true;
}

class Message {
    constructor(message){
        this.message = message;
    }
}

const fs = require("fs");
const fileName = "result.txt";
const fileContent = fs.readFileSync(fileName, "utf8");

const messages = JSON.parse(fileContent)

for (let i = 0; i < messages.length; i++) {
    if (onlyVowels(messages[i].message)){
        console.log(messages[i].message)
    }
}

