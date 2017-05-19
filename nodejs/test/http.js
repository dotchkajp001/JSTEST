var http = require("http");
var fs = require("fs");
//console.log(http);

var server = http.createServer();

server.on('request', function(req,res){
    res.writeHead(200, {'Content-Type':'text/plain'});
    res.write('test page');

    fs.writeFile("httpWrite.txt","書き込み");
    res.end();
});

server.listen(3000,'localhost');