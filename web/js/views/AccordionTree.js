define(function () {

    function f(node){
        var value=node.value;
        var htmlfile, jsfile;
        if(node.location){
            var widget=node.location.replace(/\./g,'/');
            htmlfile='text!views/'+widget+'.htm';
            jsfile='views/'+widget;
        }
        var title=node.text;
        require(['commonfuncs/TreeClickEvent'],function(TreeClickEvent){
            if(node.type=='1'){ //组件
                TreeClickEvent.ShowContent({
                    htmfile:htmlfile,
                    jsfile:jsfile,
                    title:title,
                    type:null
                });
            }else if(node.type=='0'){//url
                TreeClickEvent.ShowIframe(value,jsfile,title);
            }
        });
    }
    var a = {
        render: function (panel) {
            $(panel).children('.easyui-tree').tree({
                lines:true,
                onClick: function (node) {
                    if (!node['leaf'])return;  //如果是不是叶子而是目录文件则返回
                    var title = $((node.target)).text();
                    var tabs = $('#tabs');
                    var isexist = tabs.tabs('exists', title);
                    if (isexist) {
                        tabs.tabs('select', title);
                    } else {
                        //alert(node.text)
                        f(node)
                    }
                },
                onBeforeLoad: function (node, param) {
                    var p = $(this).parent().parent('[functionid]'); //如果这个tree节点正好在抽屉下面(紧接着的),那么就增加查询参数
                    if (p && !p.attr('isaccessed')) {
                        param.node = p.attr('functionid');
                        p.attr('isaccessed', true);
                    }

                },
                formatter: function (node) {
                    return Number(node['leafcount']) > 0 ? node.text + '(' + node['leafcount'] + ')' : node.text;
                },
                url: 'lr.do?model=manager.Function&eventName=queryFunctionTree'
            });
        }
    }
    return a;
})