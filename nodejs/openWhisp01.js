var fs = require('fs');

// bluemixから実行されるﾌﾟﾛｸﾞﾗﾑ
function main(params){
    console.log("hello" + params.name);
    // fileMake();
    fileRead();
    return {"result1":"hello " + params.name};
}

// ファイル作成
function fileMake(str){
    // fs.writeFile('書き込みたいファイル名','書き込む文字','エンコード','イベントハンドラ');
    fs.writeFile('hogeOut.txt', str);
}

// ファイル読み込み
function fileRead(){
    // fs.readFile('読み込むファイル名','エンコード','function(err,data){});
    // 同期読み込みはfs.readFileSync("");
    fs.readFile('hoge.txt',function(err,data){
        if(err){
            console.log(err);
            return;
        }
        var str = data.toString();
        console.log(str);
        
        fileMake(str);
    });
}

// test用
var inVal = {"name":"oota"};
console.log(main(inVal));