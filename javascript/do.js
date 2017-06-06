"option strict";
console.log('do start');

var Test = function(list){
    this.list = list;
}

var list = ['test1','test2'];

var obj = new Test(list);

obj.list.forEach(function(i){
    console.log(i);
})
