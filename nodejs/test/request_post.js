var request = require("request");

var promise = new Promise(function(resolve,reject){
    console.log('promise start');
    try{
        obj1 = {"name":"太田"};

        request.post({
            url:'https://openwhisk.ng.bluemix.net/api/v1/web/highpot51_dev/default/Hello%20World.json',
            headers:{'accept':'application/json'},
            json:obj1
        }, function(err, res, body){
            if(err){
                console.log(err);
            }
            //console.log(err);
            //console.log(res);
            console.log(body);
        })
        resolve('success');
    }catch(err){
        reject(err);
    }
});

promise.then(function(result){
    console.log(result);
});

return promise;