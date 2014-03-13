/**
 * Created by jack on 13-12-31.
 */
define(function () {

    function render(parameters) {

       /* var columns=[familyheaders.dbgl];
        var businesstype=$('#tabs').tabs('getSelected').panel('options').businesstype;
        var type=businesstype;
        require(['commonfuncs/BusinessGridCommon','jqueryplugin/jquery-formatDateTime'],function(BusinessGridCommon){
            BusinessGridCommon.initbusinessgrid(type,businesstype,columns);
        });*/
        require(['views/dbgl/dbglgrantmoneypanel'],function(js){
            js.render();
        });

    }

    return {
        render: render

    };
});