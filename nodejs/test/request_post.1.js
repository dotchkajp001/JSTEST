var request = require("request");

var promise = new Promise(function(resolve,reject){
    console.log('promise start');
    try{
        resolve('success');
    }catch(err){
        reject(err);
    }
});

promise.then(function(result){
    console.log(result);
});
return promise;