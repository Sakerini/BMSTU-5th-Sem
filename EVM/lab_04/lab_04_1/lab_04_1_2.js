"use strict";

const execSync = require('child_process').execSync;

for (let i = 2; i < process.argv.length; i++) {
    printFactorial(parseInt(process.argv[i]))
}

function printFactorial(number) {
    console.log("Printing factorial for number: " + number)
    console.log(useCmd("nodejs factorial.js " + number))
}

function useCmd(s) {
	const options = {encoding: 'utf8'};
	const cmd = s.toString();
	const answer = execSync(cmd, options);
	return answer.toString();
}