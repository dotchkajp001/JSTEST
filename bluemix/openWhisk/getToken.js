// bluemix用のﾒｲﾝ処理
function main(params){
    var request = require("request");

    var promise = new Promise(function(resolve, reject){
        // ｵﾌﾞｼﾞｪｸﾄｽﾄﾚｰｼﾞの接続情報
        var obj1 =  {"auth":
                        { "identity": 
                            {"methods": [ "password" ], 
                                "password":
                                    { "user": 
                                        { "id": params.userId, 
                                           "password": params.userPassword
                                        } 
                                    } 
                            }, 
                            "scope": { "project": 
                                   {"id": params.projectId} 
                            }
                        }
                    };

        // tokenの取得
        request.post({
            url: 'https://identity.open.softlayer.com/v3/auth/tokens',
            headers: {'accept': 'application/json'},
            json: obj1
        }, function (err, res, body){
            if(err){
                console.log('error:', err, body);
                reject(err);
            }else{
                // bodyからｵﾌﾞｼﾞｪｸﾄｽﾄﾚｰｼﾞのurl情報を検索
                var obj = body.token['catalog'].filter(function(item, index){
                    if(item.type == 'object-store') return true;
                });
                var obj2 = obj[0]['endpoints'].filter(function(item, index){
                    if(item.region_id == 'dallas' && item.interface == 'public') return true;
                });
                
                // 返却値
                resolve({"url":obj2[0].url,
                    "token":res.headers['x-subject-token'],
                    "container":params.container,
                    "fName":params.fName,
                    "fText":params.fText
                });
            }
        })
    })

    return promise;
}