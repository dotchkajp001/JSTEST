var cls01 = function(no,name){
    this.no = no;
    this.name = name;
}

cls01.prototype.getProperty = function(){
    return "no:" + this.no + " name:" + this.name;
}

var obj = new cls01(100,"oota");
// console.log(obj.getProperty());

var obj2 = new cls01(101,"oota2");
// console.log(obj.getProperty());

var list = [];
list.push(obj);
list.push(obj2);

// list.forEach(function(o){
//     console.log(o.getProperty());
// });

// for(var i = 0; i < 2; i++){
//     console.log(list.length);
// }