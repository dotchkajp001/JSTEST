// var counter = function(){
//     var cnt = 0;

//     function countUp() {
//         cnt += 1;
//         console.log(cnt);
//     }
// }
var counter = {
    count:0,
    countUp: function(){
        this.count += 1;
        console.log(this.count);
    }
}

counter.countUp();
counter.countUp();
console.log(counter.count);

// クロージャ
var count = (function(){
    var cnt = 0;

    return function(){
        cnt += 1;
        console.log(cnt);
    }
}());

count();
count();