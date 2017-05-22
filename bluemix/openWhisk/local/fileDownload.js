var fs = require('fs');
var request = require('request');

// ﾀﾞｳﾝﾛｰﾄﾞするﾌｧｲﾙ名
var fileName = "test.xlsx";

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
    if(err){
        console.log(err);
    }
    // base64ﾃﾞｺｰﾄﾞ
    var text = new Buffer(body.text,'base64');
    // ﾌｧｲﾙの保存
    fs.writeFile('download/' + fileName,text);
    console.log('filedownload success');
});