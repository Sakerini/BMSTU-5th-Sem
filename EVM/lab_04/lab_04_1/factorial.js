"use strict"

function factorial(n) {
    return (n != 1) ? n * factorial(n - 1) : 1;
}


let number = process.argv[2];
const result = factorial(number);
console.log(result)

