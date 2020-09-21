"use strict"

//Outputs from 1 - 10;
function firstOutput() {
    setTimeout(function () {
        for (let i = 1; i <= 10; i++) {
            console.log(i);
        }
    }, 200)
}

//Outputs from 11 - 20
function secondOutput() {
    setTimeout(function() {
        for (let i = 11; i <= 20; i++) {
            console.log(i);
        }
    }, 1000)
}


function timeout() {
    setTimeout(function () {
        firstOutput()
        secondOutput()
        timeout()
    }, 1000);
}

timeout()