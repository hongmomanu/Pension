define(function(){
    var a={
       render:function(local){
           var editor = KindEditor.create('textarea[name="noteclob"]', {
               resizeType : 1,
               allowPreviewEmoticons : false,
               allowImageUpload : false,
               items2 : [
                   'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                   'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                   'insertunorderedlist', '|', 'emoticons', 'image', 'link']
           });
           local.find('div[opt=formcontentpanel]').panel({
               onResize:function(width, height){
                   $(this).height($(this).height()-30);
                   local.find('div[opt=form_btns]').height(30);
               }
           });

           local.find('[action=save]').bind('click',function(){
               editor.sync();
               local.find('form').form('submit',{
                   url:'lr.do?model=notepad.NoteEnter&eventName=save',
                   success: function(res){
                       cj.ifSuccQest(res,"已操作成功是否关闭此页",function(){
                           require(['commonfuncs/TreeClickEvent'],function(js){
                               js.closeCurrentTab();
                           })
                       })
                   }
               })
           })
           local.find('[action=upload]').bind('click',function(){
               $.webox({
                   height:350,
                   width:500,
                   top:130,
                   bgvisibel:true,
                   title:'<<span style="color: green;">上传</span>>',
                   iframe:'lr.do?model=test&page=upload'
               });
           })
           local.find('[action=upload2]').bind('click',function(){
               /*require(['text!views/notepad/Upload.htm'],function(uploadhtm){
                   $.webox({
                       height:350,
                       width:500,
                       top:130,
                       bgvisibel:true,
                       title:'<<span style="color: green;">上传</span>>',
                       html:uploadhtm
                   });
               })*/
               require(['commonfuncs/Upload'],function(up){
                   up.show(
                       function(data){
                           alert(JSON.stringify(data))
                       }
                   )
               })

           })
       }
    }


    return a;
})