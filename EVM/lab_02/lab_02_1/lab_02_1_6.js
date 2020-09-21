"use strict"

const fs = require('fs');

let count = 1;

let obj = {$: '$'};

for (let i = 0; ; i++) {
try {
fs.writeFileSync('test.json', JSON.stringify(obj));
} catch(err) {
console.log(err);
console.log(`Result: ${count}`);
break;
}

obj = {
$: {...obj}
};
count++;
}