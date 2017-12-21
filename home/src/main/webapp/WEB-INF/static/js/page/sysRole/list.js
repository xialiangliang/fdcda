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
            ,url:'/sysRole/listJson'
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'id', title: '手机', width:160}
                ,{field:'name', title: '用户名', width:160}
                ,{field:'createTimeStr', title: '创建时间', width:220}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'15%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('确认删除？', function(index){
                    obj.del();
                    $.ajax({
                        url: '/sysRole/delete',
                        data: {'id': data.id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
                                tip(data.message, false);
                            } else {
                                tip(data.message, false);
                            }
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                window.location.href = "/sysRole/find?id=" + data.id;
            } 
        });


    });
    
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        // $.get('/sysRole/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
        //     layer.open({
        //         area:'800px',
        //         type: 1,
        //         content: object.responseText
        //     });
        // });
        window.location.href = '/sysRole/find?id=' + $(this).attr("data-id");
    });

    $(".j_new-btn").click(function () {
        $.get('/sysRole/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });
    
    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id");
        layer.confirm('确认删除？', function(index){
            $.ajax({
                url: '/sysRole/delete',
                data: {'id': id},
                async: false,
                success: function (data) {
                    if (data.success) {
                        tip(data.message, true);
                    } else {
                        tip(data.message, false);
                    }
                }
            });
            layer.close(index);
        });
    });
});