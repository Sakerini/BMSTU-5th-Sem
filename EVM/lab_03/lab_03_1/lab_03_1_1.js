"use strict"

const fs = require("fs");
const express = require("express");
const bodyParser = require('body-parser');

const app = express();
const port = 5015;

app.listen(port);
app.use(bodyParser.urlencoded({ extended: true })); 
console.log("My server on port " + port);


app.get("/send", function(request, response) {
    if (fs.existsSync("input_form.html")) {
        const contentString = fs.readFileSync("input_form.html", "utf8");
        response.end(contentString);
    } else {
        const contentString = fs.readFileSync("bad.html", "utf8");
        response.end(contentString);
    }
});

app.get("/find", function(request, response) {
    if (fs.existsSync("find_by_email.html")) {
        const contentString = fs.readFileSync("find_by_email.html", "utf8");
        response.end(contentString);
    } else {
        const contentString = fs.readFileSync("bad.html", "utf8");
        response.end(contentString);
    }
});

app.get("/find/user", function(request, response) {
    let email = request.query.email;
    let user = extractByEmail(email)

    if (user != null) {
        response.end(JSON.stringify(user))
    } else
        response.end("NOT FOUND")
});

app.post("/send", function(request, response) {
    let user = new User(request.body.email, request.body.name, parseInt(request.body.telephone))
    

    if (writeUniqueUserToFile(user)) {
        response.end("Added")
    } else {
        response.end("Not Added")
    }
});

function extractByEmail(email) {
    const userDataString = fs.readFileSync("usersdata.txt", "utf8");
    const userDataObj = userDataString.split(/\r?\n/);
    for (let i = 0; i < userDataObj.length; i++) {
        let writtenUser = userDataObj[i].split(" ");
        console.log(writtenUser[0] + " ? " + email)
        if (writtenUser[0] == email)
        {
            console.log("Email Found")
            return new User(writtenUser[0], writtenUser[1], writtenUser[2])
        }
    }
    return null;
}

function writeUniqueUserToFile(user) {    
    const userDataString = fs.readFileSync("usersdata.txt", "utf8");
    if(checkUniqueUser(userDataString , user)) {
        let stringToAppend = user.email + " " + user.name + " " + user.telephone + "\n";
        fs.appendFileSync("usersdata.txt", stringToAppend);
        return true;
    }
    return false;

}

function checkUniqueUser(userDataString, user) {
    const userDataObj = userDataString.split(/\r?\n/);
    for (let i = 0; i < userDataObj.length; i++) {
        let writtenUser = userDataObj[i].split(" ");
        console.log(writtenUser[0] + " ? " + user.email)
        if (writtenUser[0] == user.email)
        {
            console.log("Email Found")
            console.log(writtenUser[1] + " ? " + user.name)
            if (writtenUser[1] == user.name)
            {
                console.log("Name Found")
                console.log(writtenUser[2] + " ? " + user.telephone)
                if (writtenUser[2] == user.telephone){
                    console.log("Phone Found")
                    console.log("NOT UNIQUE")
                    return false;
                }
            }
        }
    }
    return true;
}

class User {
    constructor(email, name, telephone) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
    }
}