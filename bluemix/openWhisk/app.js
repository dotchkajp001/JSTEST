// bluemix用のﾒｲﾝ処理

function main(params){
    var request = require("request");

    // cache['test] = 
    // ｵﾌﾞｼﾞｪｸﾄｽﾄﾚｰｼﾞの接続情報
    var user_info = {"url":"https://dal.objectstorage.open.softlayer.com/v1/AUTH_3314ae8438ca43b293d428206b6deee8",
                    "token":"gAAAAABZE7sarPpTH9haq_uG2C4LLEeRQEzud-totK1XSKCXSXBs3teVHTc7wHW_kViQP0Whdy1fol8sTn1ajL2wZcUBBYgxjeZIo6zu6WuMLw83qLLM8NmqKRBTIpjFhbSz-zsoBduYwgd39wEBF4bPzeu0V3Ae_2lgQndZhsC-IKC01Ymip2A"};

    //var user_info = cache[req.params.userid];

    var res_handler = function(error, response, body) {
        console.log(body);
        //res.render('results', {'body': JSON.parse(body) });
        return {payload: JSON.parse(body)};
    };

    var req_options = {
            url: user_info['url'] + "/",
            headers: {'accept': 'application/json',
                        'X-Auth-Token': user_info['token']},
            timeout: 100000,
            method: 'GET'
        };

    var response = request(req_options, res_handler);
}

// テスト用
console.log('ローカル実行');
main();