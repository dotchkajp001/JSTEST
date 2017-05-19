var request = require("request").defaults({encoding:null});
var fs = require('fs');
var base64 = require('urlsafe-base64');

var url = 'https://www.google.co.jp/images/nav_logo195.png';

// request(
//     {medhot: 'GET', url:url,encoding:null},
//     function(error,response,body){
//         fs.writeFile('a.png',body,'binary');
//     }
// );

// ﾌｧｲﾙ読み込み
fs.readFile("testAf.xlsx",'utf-8',function(err,data){
    console.log(data);

    // ﾃﾞｺｰﾄﾞ処理
    var ddata = new Buffer(data,'base64');

    // ﾌｧｲﾙ書き込み
    fs.writeFile("testAf_copy.xlsx",ddata,function(err,data){
        console.log(err);
    });
})



//fs.readFile('a.png',function(err,data){
function test(){
// ﾌｧｲﾙをbase64形式で、読み込んで書き込み
fs.readFile("test.xlsx",'base64',function(err,data){
    fs.writeFile("testAf.xlsx",data);

    console.log(new Buffer(data).toString());
    //fs.writeFile("testAf2_AF.xlsx",new Buffer(data,'base64').toString());

    //
    console.log(data);

    var url = "https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/My%20Action%20Sequence.json";
    //var url = "https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/getToken.json";
    //var url = "https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/TestAction.json";
    var obj1 = {"userId":"aaa482516d5e41c1b3e067e964a1fdc5",
    "userPassword":"XBq-}#8}}afn!NE1",
    "projectId":"1b0a57ae5cb94ae8be83337afbbfa375",
    "container":"test",
    "fName":"test01.txt",
    //"fText":"テスト"};
    //"fName":"test.xlsx",
    "fText": data.toString()};
    //var obj1 = {"test":"test2"};
    request({
        method:'POST',
        url:url,
        headers:{"accept":"application/json"},
        json:obj1
    },function(err,res,body){
        //console.log(err);
        //console.log(res);
        console.log(res);
    });

    // request({
    //     mehod:'GET',
    //     url:url
    // },function(err,res,body){
    //     // console.log(res);
    //     console.log(body);
    // });
});
}