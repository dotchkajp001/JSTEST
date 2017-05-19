var fs = require('fs');
// var request = require('request');
var request_read = require("request");
var request = require("request");
var base64 = require('urlsafe-base64');

console.log('fileUpload start');

//var readFile = 'bluemix調査資料0502.xlsx';
//var readFile = 'test.bmp';
//var readFile = "確認資料.xlsx";
var readFile = 'bb.xlsx';
var readFile = 'ああ.xlsx';

// ファイルをbase64形式にｴﾝｺｰﾄﾞして、読み込み
//fs.readFile('bluemix調査資料0502.xlsx','base64',function(err,text){
    fs.readFile(readFile,'base64',function(err,text){

    //var dtext = base64.encode(text);

    var param = {"userId":"aaa482516d5e41c1b3e067e964a1fdc5",
    "userPassword":"XBq-}#8}}afn!NE1",
    "projectId":"1b0a57ae5cb94ae8be83337afbbfa375",
    "container":"test",
    "fName":readFile,
    "fText":text
    //"fText":dtext
    };

    // openWhiskに送信
    request.post({
        url:'https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/My%20Action%20Sequence.json',
        headers:{'accept':'application/json'},
        json:param
    }, function(err, res, body){
        console.log(body);
    });
});