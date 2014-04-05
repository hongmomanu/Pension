define(function(){
    var a={
       render:function(){
           var editor = KindEditor.create('textarea[name="noteclob"]', {
               resizeType : 1,
               allowPreviewEmoticons : false,
               allowImageUpload : false,
               items2 : [
                   'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                   'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                   'insertunorderedlist', '|', 'emoticons', 'image', 'link']
           });
           $('#formcontentpanel').panel({
               onResize:function(width, height){

                   $('#formcontentpanel').height($('#formcontentpanel').height()-30);
                   $('#form_btns').height(30);
               }
           });

           $('#save').bind('click',function(){
               editor.sync();
               $('#mainform').form('submit',{
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
       }
    }


    return a;
})