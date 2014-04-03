/*查询行政区划名称*/
define(function(){
    var a={
        initDivisionWidget:function(selection,func){
            $(selection).combogrid({
                panelWidth:350,
                panelHeight:400,
                url: 'lr.do?model=manager.Division&eventName=query',
                mode:'remote',
                fit:true,
                pagination:true,
                pageSize:15,
                pageList: [15, 30,50],
                border:false,
                fitColumns:true,
                onBeforeLoad: function(param){
                    if(param.q!=null){
                        if(cj.getByteLen(param.q)<4){
                            return false;
                        }
                        param.q=param.q;
                    }else{
                        return false;
                    }
                },
                onClickRow:function(index,row){
                    func(index,row)
                },
                columns:[[
                    {field:'dvcode',title:'代码',width:60},
                    {field:'dvname',title:'地名',width:50},
                    {field:'parentname',title:'上级地名',width:50}
                ]]
            });
        }
    }
    return a;
})