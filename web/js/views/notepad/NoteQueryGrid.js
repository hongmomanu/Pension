define(function(){
    var viewContent=function(row){
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
    var editContent=function(row){
        alert('去编辑')
    }
    var a={
        render:function(local){
           $('.easyui-datagrid-noauto').datagrid({
               onLoadSuccess:function(data){
                   var viewbtns=local.find('[action=view]');
                   var editbtns=local.find('[action=edit]');
                   var btns_arr=[viewbtns,editbtns];
                   var rows=data.rows;
                   for(var i=0;i<rows.length;i++){
                      for(var j=0;j<btns_arr.length;j++){
                          (function(index){
                              $(btns_arr[j][i]).click(function(){
                                  var record=rows[index];
                                  if($(this).attr("action")=='view'){
                                      viewContent(record)
                                  }else{
                                      editContent(record)
                                  }
                              });
                          })(i);
                      }
                   }
               }
           })
        }

    }

    return a;
})