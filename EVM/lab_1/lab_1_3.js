"use strict"

class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

class PointRepository {
    constructor() {
        this.dict = {}
    }

    addPoint(name, point) {
        if (this.dict[name]) {
            return;
        }
        this.dict[name] = point;
    }

    findPoint(name) {
        console.log(name + ":");      
        console.log(this.dict[name]);
    }
    
    findAllPoints(){ 
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                console.log(key + ":");      
                console.log(this.dict[key]);
            }
        }
    }

    updatePoint(name) {
        this.dict[name] = point;
    }

    deletePoint(name) {
        if (this.dict[name]) {
            delete this.dict[name];
        }
    }
    
    findDistanceBetweenPoints(point1, point2) {
        let distance = Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) +
        (point1.y - point2.y) * (point1.y - point2.y));

        return distance;
    }

    findTwoFarestPoints() {
        let distance = null;
        let point1 = null;
        let point2 = null;
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                for (let key2 in this.dict) {
                    if (this.dict.hasOwnProperty(key2)) {  
                        if (distance = null) {
                            distance = this.findDistanceBetweenPoints(this.dict[key], this.dict[key2]);
                            point1 = this.dict[key];
                            point2 = this.dict[key2];
                        }else{
                            let temp = this.findDistanceBetweenPoints(this.dict[key], this.dict[key2]);
                            console.log(temp);
                            if (temp > distance) {
                                distance = temp;
                                point1 = this.dict[key];
                                point2 = this.dict[key2];
                            }
                        }
                    }
                }
            }
        }
        console.log(point1);
        console.log(point2);
    }

    findPointFromAtRange(point, range) {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                let distance = this.findDistanceBetweenPoints(this.dict[point], this.dict[key]);
                //console.log(distance)
                if (distance <= range) {
                    console.log(this.dict[key]);
                }
            }
        }
    }

    findPointsAboveOx() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].y > 0) {
                    console.log(this.dict[key]);
                }
            }
        }
    }

    findPointsUnderOx() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].y < 0) {
                    console.log(this.dict[key]);
                }
            }
        }
    }

    findPointsLeftFromY() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].x < 0) {
                    console.log(this.dict[key]);
                }
            }
        }
    }

    findPointsRightFromY() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].x > 0) {
                    console.log(this.dict[key]);
                }
            }
        }
    }

    // Note it should be rectangle 2 points bottom-left and top-right
    isInRectangle(x1, y1, x2, y2, x, y) {
        if (x > x1 && x < x2 &&  
            y > y1 && y < y2) 
            return true; 
          
        return false; 
    }

    findPointsInRectangle(x1, y1, x2, y2) {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.isInRectangle(x1, y1, x2, y2, this.dict[key].x, this.dict[key].y)){
                    console.log(this.dict[key]);
                }
            }
        }
    }
}

//MAIN

//Create Repository
let pointRepo = new PointRepository();
//Create Points
let point1 = new Point(1,6);
let point2 = new Point(6,2);
let point3 = new Point(-5,12);
let point4 = new Point(0,-6);
//Add points to repo
pointRepo.addPoint("first", point1);
pointRepo.addPoint("second", point2);
pointRepo.addPoint("third", point3);
pointRepo.addPoint("fourth", point4);

//pointRepo.findPoint("first");
//pointRepo.findTwoFarestPoints();
//pointRepo.findPointFromAtRange("first", 11);
//pointRepo.findPointsAboveOx();
//pointRepo.findPointsUnderOx();
//pointRepo.findPointsLeftFromY();
//pointRepo.findPointsRightFromY();
//pointRepo.findPointsInRectangle(-4, -8, 3, -5);