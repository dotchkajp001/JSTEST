var fs = require('fs');
var request = require('request');
//var request = require("request").defaults({encoding:null});

console.log('fileDownload start');

// var fileName = "test.bmp";
// var localFileName = "testDownload.bmp";
var fileName = "確認資料.xlsx";
var localFileName = "確認資料_down.xlsx";
var fileName = "bb.xlsx";
var localFileName = "bb_down.xlsx";
var fileName = "ああ.xlsx";
var localFileName = "ああ_down.xlsx";

// ファイルをbase64形式にｴﾝｺｰﾄﾞして、読み込み
var param = {"userId":"aaa482516d5e41c1b3e067e964a1fdc5",
"userPassword":"XBq-}#8}}afn!NE1",
"projectId":"1b0a57ae5cb94ae8be83337afbbfa375",
"container":"test",
"fName":fileName
};

// openWhiskに送信
request.post({
    url:'https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/My%20Action%20Sequence%20Download.json',
    headers:{'accept':'application/json'},
    json:param
}, function(err, res, body){
    console.log(body);

    // base64ﾃﾞｺｰﾄﾞ
    var text = new Buffer(body.text,'base64');
    // ﾌｧｲﾙの保存
    fs.writeFile(localFileName,text);
});


