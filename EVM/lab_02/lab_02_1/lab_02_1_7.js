'use strict';

const obj = require('./user.json');

const isVarTypeOf = (_var, _type) => {
  return typeof _var === _type;
}

const height = obj => {
  if (!obj) return [0, null];

  let maxHeight = 0;
  let maxNode = {};
  Object.keys(obj).forEach(key => {
    if (isVarTypeOf(obj[key], "object")) {
      const [h, n] = height(obj[key]);
      if (maxHeight < h) {
        maxHeight = h;
        maxNode = {[key]: n};
      }
    } else {
      if (maxHeight < 1) {
        maxHeight = 1;
        maxNode = {[key]: obj[key]};
      }
    }
  });

  return [maxHeight + 1, maxNode]
};

console.log(JSON.stringify(height(obj), 0, 2));