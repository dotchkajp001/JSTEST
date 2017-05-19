// bluemix用のﾒｲﾝ処理
function main(params){
    var request = require("request");
    
    var promise = new Promise(function(resolve, reject){
        // ﾌｧｲﾙｱｯﾌﾟﾛｰﾄﾞ処理(ﾃｽﾄ用ｺﾝﾃﾅｰのtest)
        request.put({
            url: params.url + "/" + params.container + "/" + params.fName,
            headers: {'accept': 'application/json',
                        'X-Auth-Token': params.token},
            body: params.fText
        }, function (err, res, body){
            if(err){
                reject(err);
            }else{
                if(body == ""){
                    body = "success";
                }
                resolve({"result":body});
            }
        })
    })

    return promise;
}