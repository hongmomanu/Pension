define(function(){
    var a={
        render:function(){
           $('.easyui-datagrid-noauto').datagrid({
               onClickRow:function(index,row){
                   if(row.notecid){
                       $.webox({
                           height:450,
                           width:800,
                           top:110,
                           bgvisibel:true,
                           title:'<<span style="color: green;">'+row.text+'</span>>',
                           iframe:'lr.do?model=notepad.NoteEnter&eventName=clobExport&notecid='+row.notecid

                       });
                   }else{
                       alert('无效')
                   }
               }
           })
        }

    }

    return a;
})