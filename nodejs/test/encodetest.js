var fs = require('fs');
var base64 = require('urlsafe-base64');

console.log('encodetest');

var test = 'あいうえお';
// var btest = new Buffer(test,'base64');
var btest = base64.encode(new Buffer(test));

var dtest = base64.decode(btest);

-- 元の文字列
console.log(test);

-- base64にエンコードした文字列
console.log(btest);

-- デコードした文字列
console.log(dtest);

// console.log(btest.toString());

// console.log(new Buffer(test,'base64'));
// console.log(new Buffer(test,'base64').toString());