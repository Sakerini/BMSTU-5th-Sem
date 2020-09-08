"use strict"

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

class ChildrenDB {
    
    constructor() {
        this.dict = {};
    }

    addChild(surname, age) {
        if (this.dict[surname]) {
            return;
        }
        this.dict[surname] = age;
    }

    deleteChild(surname){
        if (this.dict[surname]) {
            delete this.dict[surname];
        }
    }

    getChild(surname) {
        if (this.dict[surname]) {
            return this.dict[surname];
        }
    }
    
    getAllChildren() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
                console.log("Child: " + key + " " + "Age: " + this.dict[key]);
            }
        }
    }

    updateAge(surname , age) {
        if (this.dict[surname]) {
            this.dict[surname] = age
        }
    }

    findAvarageAge() {
        let result = 0;
        let count = 0;
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
                count++;
                result += this.dict[key];
            }
        }
        if (count != 0) {
            return result/count;
        } else{
            return 0;
        }
    }

    findOldest() {
        let oldestKey = null;
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
                if (oldestKey == null) {
                    oldestKey = key;
                } else if (this.dict[key] > this.dict[oldestKey]) {
                    oldestKey = key;
                }
            }
        }

        console.log("Child: " + oldestKey + " " + "Age: " + this.dict[oldestKey]);
    }

    findInRange(from, to) {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
                if (this.dict[key] > from && this.dict[key] < to) {
                    console.log("Child: " + key + " " + "Age: " + this.dict[key]);
                }
            }
        }
    }

    findFirstSymbolName(symbol) {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
               if (key[0] == symbol) {
                console.log("Child: " + key + " " + "Age: " + this.dict[key]);
               }
            }
        }
    }

    findNameLongerThan(value) {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
               if (key.length > value) {
                console.log("Child: " + key + " " + "Age: " + this.dict[key]);
               }
            }
        }
    }

    findNameStartOnVowel() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {              
               if (isVowel(key[0])){
                console.log("Child: " + key + " " + "Age: " + this.dict[key]);
               }
            }
        }
    }
}

console.log("Hello");
let db = new ChildrenDB();

// db.addChild("petrovich", 15);
// db.addChild("Chaushev", 20);
// db.addChild("Ivchenko", 19);
// test on unique
// db.addChild("petrovich", 20);

// db.getAllChildren();

// console.log(db.findAvarageAge());
// console.log("Deleting petrovhich");
// db.deleteChild("petrovich");

// db.getAllChildren();

// console.log(db.findAvarageAge());
// db.findOldest();

// db.findInRange(18,25);

// db.findFirstSymbolName("C");    
// db.findNameLongerThan(8);
// db.findNameStartOnVowel();
console.log("Bye");