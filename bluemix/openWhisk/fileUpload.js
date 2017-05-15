// bluemix用のﾒｲﾝ処理
function main(params){
    var request = require("request");
    var docNm   = params.docNm;
    var docText = params.docText;

    // cache['test] = 
    // ｵﾌﾞｼﾞｪｸﾄｽﾄﾚｰｼﾞの接続情報
    var user_info = {"url":"https://dal.objectstorage.open.softlayer.com/v1/AUTH_3314ae8438ca43b293d428206b6deee8",
                    "token":"gAAAAABZFRzHwIFgWyjBgNyw1c3y9q5o7WWjnDLBMMqZW-D3GiwyrrgUl8Kigja8Cj1l730m6VmRfNhcojLdSWlmgFIS0rgEKYprv3NNR_yGb9iEf4zPfGwPQwhZ1bm1IRh-jkKiwuQHswbMQh7autnnq4bthJwyTDeQ66aMbtIAGxBZB7GIz6w"};

    var req_options = {
            url: user_info['url'] + "/test/" + docNm,
            headers: {'accept': 'application/json',
                        'X-Auth-Token': user_info['token']},
            timeout: 100000,
            body: docText,
            method: 'PUT'
        };

    var promise = new Promise(function(resolve, reject){
        request.put({
            url: user_info['url'] + "/test/" + docNm,
            headers: {'accept': 'application/json',
                        'X-Auth-Token': user_info['token']},
            body: docText
        }, function (err, res, body){
            if(err){
                console.log('error:', err, body);
                reject(err);
            }else{
                console.log(body);
                resolve({"result":"success"});
            }
        })
    })

    return promise;
}

// テスト用
/** **/
console.log('ローカル実行');
var params = {"docNm":"local2.txt",
                "docText":"テストデータ2"};
main(params);
/** **/