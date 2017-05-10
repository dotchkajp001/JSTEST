function main(params){
    console.log("hello" + params.name);
    fileMake();
    return {"result1":"hello" + params.name};
}

function fileMake(){
    var fs = require('fs');

    var test = "テスト文字";
    fs.writeFile('hoge.txt', test);
}

// test用
var inVal = {"name":"oota"};
console.log(main(inVal));