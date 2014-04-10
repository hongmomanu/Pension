define(function(){
    var a=function(local,option){
        require(['views/pension/EvaluateLrInfo'],function(js){
            js.render(local,option)
        })
    }

    return {
        render:a
    }
})