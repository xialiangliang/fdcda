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
            ,url:'/sysUser/listJson?keyword=' + $('#keyword').val()
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'id', title: 'ID', width:160}
                ,{field:'loginname', title: '登录名', width:160}
                ,{field:'username', title: '姓名', width:160}
                ,{field:'phone', title: '手机号', width:160}
                ,{field:'telephone', title: '固定电话', width:160}
                ,{field:'email', title: '邮件地址', width:160}
                ,{field:'address', title: '地址', width:160}
                ,{field:'valid', title: '状态', width:160, templet: '#validId'}
                ,{field:'createTimeStr', title: '创建时间', width:160}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'15%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                window.location.href = "/sysUser/find?id=" + data.id;
            }
        });


    });
    
    
    
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        // $.get('/sysUser/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
        //     layer.open({
        //         type: 1,
        //         title: "修改",
        //         content: object.responseText
        //     });
        // });
        window.location.href = '/sysUser/find?id=' + $(this).attr("data-id");
    });

    $(".j_new-btn").click(function () {
        // $.get('/sysUser/new', {}, function (data, textStatus, object) {
        //     layer.open({
        //         type: 1,
        //         title: "新建",
        //         content: object.responseText
        //     });
        // });
        window.location.href = '/sysUser/new';
    });
});