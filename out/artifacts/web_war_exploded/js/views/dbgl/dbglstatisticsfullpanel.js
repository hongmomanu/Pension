/**
 * Created by jack on 13-12-31.
 */
define(function () {

    function render(parameters) {
        var businesstype=$('#tabs').tabs('getSelected').panel('options').businesstype;
        var type="full";
        require(['commonfuncs/StatisticsGridCommon','jqueryplugin/jquery-formatDateTime'],function(StatisticsGridCommon){
            StatisticsGridCommon.initbusinessgrid(type,businesstype);

        });

    }

    return {
        render: render

    };
});