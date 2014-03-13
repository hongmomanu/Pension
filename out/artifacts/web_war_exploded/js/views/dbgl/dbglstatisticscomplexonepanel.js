/**
 * Created by jack on 13-01-19.
 */
define(function () {

    function render(parameters) {
        var businesstype=$('#tabs').tabs('getSelected').panel('options').businesstype;
        var type="complexone";
        require(['commonfuncs/StatisticsGridCommon','jqueryplugin/jquery-formatDateTime'],function(StatisticsGridCommon){
            StatisticsGridCommon.initbusinessgrid(type,businesstype);
        });
    }

    return {
        render: render

    };
});