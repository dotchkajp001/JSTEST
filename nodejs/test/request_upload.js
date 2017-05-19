var request = require('request');
var fs = require('fs');

var url = 'https://www.google.co.jp/images/nav_logo195.png';

// ﾊﾞｲﾅﾘｰﾌｧｲﾙのﾀﾞｳﾝﾛｰﾄﾞ
request(
    {  
        medhot: 'GET',
        url: url,
        encoding: null
    },
    function(error,response,body){
        fs.writeFile('a.png',body,'binary');
    }
);

fs.readFile('a.png',function(err,data){
    console.log(data);

    request(
        
    )
});