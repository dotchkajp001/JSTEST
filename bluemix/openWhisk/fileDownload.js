// bluemix用のﾒｲﾝ処理
function main(params){
    var request = require("request");

    console.log(params.fName);
    var promise = new Promise(function(resolve, reject){
        request.get({
            url: params.url + "/" + params.container + "/" + encodeURIComponent(params.fName),
            headers: {'accept': 'application/json',
                        'X-Auth-Token': params.token}
        }, function (err, res, body){
            if(err){
                reject(err);
            }else{
                resolve({"text":body});
            }
        })
    })

    return promise;
}