define(function(){
    //打印
    function printformFn(form){
        var el = form;

        var win = window.open('', '', '');
        if (win==null){
            alert("Pop-up is blocked!");
            return;
        }


        win.document.write('<!DOCTYPE html>');
        win.document.write('<html><head>');
        win.document.write('<title>' + document.title + '</title>');


        win.document.write('<link rel="stylesheet" type="text/css" id="swicth-style" href="'+extLocation+
            'themes/default/easyui.css"><\/>');
        win.document.write('<link rel="stylesheet" type="text/css" id="swicth-style" href="css/printform.css"><\/>');
        win.document.write('</head><body style="height: 500px">');
        win.document.write('<div style="width: 1024px">');
        win.document.write(el.html());
        win.document.write('</div>');
        win.document.write('</body></html>');
        win.document.close();


        win.focus();
        win.print();
        //win.close();

    }

    function render(lookupname,folder,LoadingMask,res){

        $('#businessformprint td[name]').each(function(i){
            var td=$(this);
            td.text(res.form[td.attr('name')])
        })
        if(res){
            var params = {
                businessid:res.form.id
            };
            var successFunc = function(data){
                $('#familymembersgrid').datagrid('loadData',data);

            };

            $.ajax({
                type: "post",        //使用get方法访问后台
                dataType: "json",       //返回json格式的数据
                url: "ajax/getfamilymembersbybid.jsp",   //要访问的后台地址
                data: params,         //要发送的数据
                complete :function(){},      //AJAX请求完成时
                success: successFunc
            });
        }

        var btns=$('.aprovlfuncs_btns');
        btns.each(function(i){
            $(this).linkbutton();
            $(this).on('click',function(){
                printformFn($('#businessformprint'))
            })
        })
    }


    return {
        render:render
    };
});