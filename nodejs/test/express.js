var express = require("express");

console.log("express");

var app = express();

app.get('/',function(req,res){
    res.send('test page2');
});

app.post('/',function(req,res){
    res.send('post');
});

app.listen(3000);