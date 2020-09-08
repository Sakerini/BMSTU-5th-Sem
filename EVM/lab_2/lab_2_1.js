"use strict"

class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }

    setX(x) {
        this.x = x;
    }

    setY(y) {
        this.y = y;
    }

    showPoint() {
        console.log("X: " + this.x + " Y: " + this.y);
    }
}

class Line {
    constructor(point1, point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    setPoint1(point) {
        this.point1 = point;
    }

    setPoint2(point) {
        this.point2 = point;
    }

    showLine() {
        console.log("X1: " + this.point1.x + " Y1: " + this.point1.y + "\n" +
        "X2: " + this.point2.x + " Y2: " + this.point2.y)
    }

    getLen() {
        let len = Math.sqrt((this.point1.x - this.point2.x) * (this.point1.x - this.point2.x) +
        (this.point1.y - this.point2.y) * (this.point1.y - this.point2.y));

        return len;
    }
}

//MAIN

//Make 2 Points
let point1 = new Point(0,5);
let point2 = new Point(5,5);
//Make Line
let line = new Line(point1, point2);

line.showLine();

console.log(line.getLen());