$(function(){

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
            ,url:'/sysPlatform/listJson?userId=' + $('#userId').val()
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'userId', title: '用户id', width:80}
                ,{field:'name', title: '用户姓名', width:160}
                ,{field:'loginName', title: '登录账号', width:150}
                ,{field:'ip', title: 'ip地址', width:180}
                ,{field:'device', title: '登录设备', width:80}
                ,{field:'createTimeStr', title: '登录时间', width:220}
                // ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'10%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                //layer.msg('ID：'+ data.id + ' 的查看操作');
                window.location.href = "/blackList/system/find?id=" + data.id;
            }
        });


    });
    
    //
    // var dialog = $("#newUpdateDialogFrame");
    //
    // $(".j_update-btn").click(function () {
    //     $.get('/sysLoginlog/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
    //         layer.open({
    //             type: 1,
    //             content: object.responseText
    //         });
    //     });
    // });
    //
    // $(".j_new-btn").click(function () {
    //     $.get('/sysLoginlog/new', {}, function (data, textStatus, object) {
    //         layer.open({
    //             type: 1,
    //             content: object.responseText
    //         });
    //     });
    // });
});