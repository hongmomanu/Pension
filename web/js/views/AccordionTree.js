define(function () {

    //text!LrBasicInfo
    //views/weipan/View
    function f(node){
        var jsfile='views/pension/LrBasicInfo';
        var value=node.value;
        var htmlfile='text!views/pension/'+value+'.htm';
        var title=node.text;
        var folder="pension";//以后配置
        require(['commonfuncs/TreeClickEvent'],function(TreeClickEvent){
            if(node.type=='1'){ //组件
                TreeClickEvent.ShowContent(htmlfile,jsfile,title,value,folder,null,'0000','无类型');
            }else if(node.type=='0'){//url
                TreeClickEvent.ShowIframe(value,jsfile,title);
            }
        });
    }
    var a = {
        render: function (panel) {
            $(panel).children('.easyui-tree').tree({

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