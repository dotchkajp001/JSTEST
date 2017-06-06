// ES5のﾃｽﾄ
console.log("ES5");

var obj = function(name,age){
    this.name = name;
    this.age = age;
}
obj.prototype.getName = function(){
    return this.name;
}

// ﾌﾘｰｽﾞ
var cons = {
    name1 : 'test',
    name2 : 'test2'
}
Object.freeze(cons);
cons.name1 = 'update';
console.log('cons:' + cons.name1);

var test = ['test1','test2'];

// console.log(test);
// console.log(test[0]);
var list = [new obj('oota1','10'), new obj('oota2','11')];

list.push(new obj('oota3','10'));

// forEach
list.forEach(function(val,index){
    console.log(val.getName());
})

// JSON
var strjson = JSON.parse('{"test1":"val1","test2":"val2"}');
// strjson.test1 = '更新';

console.log(strjson);
console.log(JSON.stringify(strjson));

// isArray
if(Array.isArray(strjson)){
    console.log('isArray');
}else{
    console.log('notArray');
}

