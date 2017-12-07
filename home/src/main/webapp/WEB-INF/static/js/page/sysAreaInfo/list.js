$(function(){
    var dialog = $("#newUpdateDialogFrame");
    layui.use(['laydate', 'table'], function(){
        var laydate = layui.laydate, table = layui.table;

        //执行一个laydate实例
        laydate.render({
            elem: '#createTimeBeginStr' //指定元素
            ,value: new Date()
        });
        laydate.render({
            elem: '#createTimeEndStr' //指定元素
            ,value: new Date()
        });

        table.render({
            elem: '#test'
            ,url:'/sysAreaInfo/listJson'
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'code', title: '区域code', width:160}
                ,{field:'parentcode', title: '父区域code', width:160}
                ,{field:'areaname', title: '名称', width:80}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'15%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
            }
        });


    });
    $(".j_update-btn").click(function () {
        $.get('/sysAreaInfo/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysAreaInfo/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });
});