"use strict"

class Triangle {
    constructor(a, b, c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    setA(a) {
        this.a = a;
    }

    setB(b) {
        this.b = b;
    }

    setC(c) {
        this.c = c;
    }

    isCorrect() {
        if (this.a + this.b < this.c) {
            return false;
        }
        if (this.b + this.c < this.a) {
            return false;
        }
        if (this.a + this.c < this.b) {
            return false;
        }

        return true;
    }

    getPerimeter() {
        return this.a + this.b + this.c;
    }

    getS() {
        let p = this.getPerimeter() / 2;
        let s = Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
        return s;
    }

    findBiggest() {
        let temp = null
        if (this.a >= this.b) {
            temp = this.a;
        } else {
            temp = this.b;
        }
        if (this.c > temp) {
            temp = this.c;
        }
        return temp;
    }

    isPryqmougolnyi() {
        let temp = this.findBiggest();
        if (temp == this.a) {
            if (this.a * this.a == ((this.c * this.c) + (this.b * this.b))) {
                return true;
            }
        }
        if (temp == this.b) {
            if (this.b * this.b  == ((this.a * this.a) + (this.c * this.c))){
                return true;
            }
        }
        if (temp == this.c) {            
            if (this.c * this.c == ((this.a * this.a) + (this.b * this.b))){
                return true;
            }
        }
        return false;
    }
}

//Main

//Create triangle
let triangle = new Triangle(3,4,5)
console.log(triangle.getPerimeter())
console.log(triangle.getS())
console.log(triangle.isCorrect())
console.log(triangle.isPryqmougolnyi())