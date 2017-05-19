var fs = require("fs");

console.log("file_read:start");

fs.readFile('./test.xlsx',function(err,text){
    if(err){
        console.log(err);
    }
    console.log(text);
});