var fs = require('fs');
var request = require("request");

// ｱｯﾌﾟﾛｰﾄﾞするﾌｧｲﾙの指定
var readFile = 'test.xlsx';

// ファイルをbase64形式にｴﾝｺｰﾄﾞして、読み込み
fs.readFile(readFile,'base64',function(err,text){
    var param = {"userId":"aaa482516d5e41c1b3e067e964a1fdc5",
    "userPassword":"XBq-}#8}}afn!NE1",
    "projectId":"1b0a57ae5cb94ae8be83337afbbfa375",
    "container":"test",
    "fName":readFile,
    "fText":text
    };

    // openWhiskに送信
    request.post({
        url:'https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/My%20Action%20Sequence.json',
        headers:{'accept':'application/json'},
        json:param
    }, function(err, res, body){
        if(err){
            console.log(err);
        }
        console.log(body);
    });
});