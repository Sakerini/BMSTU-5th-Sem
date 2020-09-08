"use strict"

//Outputs from 1 - 10;
function firstOutput() {
    for (let i = 1; i <= 10; i++) {
        console.log(i);
    }
}

//Outputs from 11 - 20
function secondOutput() {
    for (let i = 11; i <= 20; i++) {
        console.log(i);
    }
}

//        |-_-|
setTimeout(() => firstOutput(), 2000);
setTimeout(() => secondOutput(), 1000);
setTimeout(() => firstOutput(), 2000);
setTimeout(() => secondOutput(), 1000);