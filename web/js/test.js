define(function(){
    var s="init";
    var getS=function(){
        return s;
    }
    var setS=function(){
        s="yes";
    }

    return {
        get:getS,set:setS
    }
})