"use strict"

class Student {

    constructor(group, number, grades) {
        this.group = group;
        this.number = number;
        this.grades = grades;
    }

    changeGroup(group) {
        this.group = group;
    }

    findAvarageGrade() {
        let count = 0;
        let result = 0;
        for(let i=0; i < this.grades.length; i++) {
            result += this.grades[i];
            count++;
        }

        if (count != 0) {
            return result/count;
        } else{
            return 0;
        }
    }
}

class StudentsDB {

    constructor() {
        this.dict = {};
    }

    addStudent(number, student) {
        if (this.dict[number]) {
            return;
        }
        this.dict[number] = student;
    }

    findAllStudents() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {        
                console.log(this.dict[key]);
            }
        }
    }

    updateStudent(number, student) {
        this.dict[number] = student;
    }

    deleteStudent(number){
        if (this.dict[number]) {
            delete this.dict[number];
        }
    }

    getAvarageGradeOfStudent(number) {
        return this.dict[number].findAvarageGrade();
    }

    findStudentsByGroup(group) {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].group == group)      
                    console.log(this.dict[key]);
            }
        }
    }

    findStudentWithMostGradesByGroup(group) {
        let mostGrades = null;
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].group == group)
                    if (mostGrades == null) {
                        mostGrades = this.dict[key].number;
                    } else {
                        if (this.dict[mostGrades].grades.length < this.dict[key].grades.length) {
                            mostGrades = key;
                        }
                    }
            }
        }

        console.log(this.dict[mostGrades]);
    }

    findStudentsWithoutGrades() {
        for (let key in this.dict) {
            if (this.dict.hasOwnProperty(key)) {  
                if (this.dict[key].grades.length == 0) {
                    console.log(this.dict[key])
                }
            }
        }

    }
}

//MAIN
//Create DB
let studentsDB = new StudentsDB();
//Create 4 students
let student1 = new Student("IU7","1872", [5,5,3,5]);
let student2 = new Student("IU9","1922", [3,3,3,5]);
let student3 = new Student("IU6","1112", [5,5,5,5]);
let student4 = new Student("IU7", "1728", []);
//Add students to DB
studentsDB.addStudent(student1.number, student1);
studentsDB.addStudent(student2.number, student2);
studentsDB.addStudent(student3.number, student3);
studentsDB.addStudent(student4.number, student4);
//Print Students
//studentsDB.getAllStudents();
//Get Avarage Grade Of Student by number
//console.log(studentsDB.getAvarageGradeOfStudent("1922"));
//Get information of students by group
//studentsDB.getStudentsByGroup("IU7");
//Find Student with the most grades in a group
//studentsDB.findStudentWithMostGradesByGroup("IU7");
// Find StudentWithout grades
//studentsDB.findStudentsWithoutGrades()
