// bluemix用のﾒｲﾝ処理
function main(params){
    var request = require("request");
    var docNm   = params.docNm;
    var docText = params.docText;

    // cache['test] = 
    // ｵﾌﾞｼﾞｪｸﾄｽﾄﾚｰｼﾞの接続情報
    var user_info = {"url":"https://dal.objectstorage.open.softlayer.com/v1/AUTH_3314ae8438ca43b293d428206b6deee8",
                    "token":"gAAAAABZFRzHwIFgWyjBgNyw1c3y9q5o7WWjnDLBMMqZW-D3GiwyrrgUl8Kigja8Cj1l730m6VmRfNhcojLdSWlmgFIS0rgEKYprv3NNR_yGb9iEf4zPfGwPQwhZ1bm1IRh-jkKiwuQHswbMQh7autnnq4bthJwyTDeQ66aMbtIAGxBZB7GIz6w"};

    auth = {"auth_uri":"https://identity.open.softlayer.com",
			"userid":"f087e0cb1e4a1af8ee135f6af68372ceeaee9819",
			"password":"KwIE?5yTn8B?5NAL"};

	auth["secret"] = "Basic KwIE?5yTn8B?5NAL"; 
		//Buffer(auth.userid + ":" + auth.password).toString("base64");

    var promise = new Promise(function(resolve, reject){
        var obj1 = {"auth":
                    { "identity": 
                            {"methods": [ "password" ], 
                                "password":
                                    { "user": 
                                        { "id": "c5135dfaf9a34a5c869b8d971e4408d3", 
                                           "password": "KwIE?5yTn8B?5NAL"
                                        } 
                                    } 
                            }, 
                            "scope": { "project": 
                                   {"id": "3314ae8438ca43b293d428206b6deee8"} 
                            }
                    }  
            };
        request.post({
            url: 'https://identity.open.softlayer.com/v3/auth/tokens',
            headers: {'accept': 'application/json'},
            json: obj1
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
console.log('ローカル実行');
var params = {"docNm":"local2.txt",
                "docText":"テストデータ2"};
main(params);