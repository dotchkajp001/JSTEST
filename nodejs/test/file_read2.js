var fs = require("fs");

console.log("file_read:start");

fs.readFile('./test.xlsx',function(err,text){
    console.log(text);

    var bfile = new Buffer(text,'base64');
    console.log(bfile.toString());

    if(err){
        console.log(err);
    }
    // console.log(new Buffer(text,'base64').toString());
    // fs.writeFile("testAf_Af.xlsx",new Buffer(text,'base64'),function(err){
    //     console.log(err);
    // });
    fs.writeFile("testAf_Af.xlsx",text,function(err){
        console.log(err);
    });
});