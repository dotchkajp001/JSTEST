// node.jsのバージョン
// "use strict";

class Test{
    constructor (var1){
        this.var1 = var1;
    }
    out(){
        console.log(this.var1);
    }
}

var trg1 = new Test("classテスト");
trg1.out();