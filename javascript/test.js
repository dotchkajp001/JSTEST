// ｵﾌﾞｼﾞｪｸﾄのﾃｽﾄ
console.log('test');

var test = function(name,age){
    this.name = name;
    this.age = age;
}

test.prototype.getName = function(){
    return this.name;
}
test.prototype.getAge = function(){
    return this.age;
}

// 継承
var inherits = function(cchild, cparent){
    Object.setPrototypeOf(cchild.prototype, cparent.prototype);
}

var test_ko = function(name,age){
    test.call(this, name, age);
}
inherits(test_ko,test);

var test_ko2 = function(name,age){
    test.call(this,name + "です",age);
}
inherits(test_ko2,test);

var obj_ko = new test_ko2('ko',11);
console.log(obj_ko.getName());


// var obj1 = new test('namae',32);
// console.log(obj1.getName());
// console.log(obj1.getAge());
