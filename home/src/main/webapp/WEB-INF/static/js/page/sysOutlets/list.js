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
            ,url:'/sysOutlets/listJson'
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'loginName', title: '用户登录名', width:160}
                ,{field:'name', title: '门店名', width:160}
                ,{field:'address', title: '地址', width:160}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'25%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                // window.location.href = "/sysOutlets/find?id=" + data.id;
                $.get('/sysOutlets/find', {'id':data.id}, function (data, textStatus, object) {
                    layer.open({
                        type: 1,
                        content: object.responseText
                    });
                });
            } else if(obj.event === 'detail'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                window.location.href = "/sysOutlets/sysDevice/list?outletsId=" + data.id;
            } else if(obj.event === 'del'){
                layer.confirm('确认删除？', function(index){
                    obj.del();
                    $.ajax({
                        url: '/sysOutlets/delete',
                        data: {'id': data.id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
                                gtip(data.message, false, null);
                            } else {
                                gtip(data.message, false, null);
                            }
                        }
                    });
                    layer.close(index);
                });
            }
        });


    });
    
    var dialog = $("#newUpdateDialogFrame");

    // $(".j_update-btn").click(function () {
    //     $.get('/sysOutlets/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
    //         layer.open({
    //             type: 1,
    //             content: object.responseText
    //         });
    //     });
    // });

    $(".j_new-btn").click(function () {
        $.get('/sysOutlets/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });

    // $(".j_device-btn").click(function () {
    //     $.get('/sysOutlets/sysDevice/list', {'outletsId': $(this).attr("data-id")}, function (data, textStatus, object) {
    //         layer.open({
    //             area: '500px',
    //             type: 1,
    //             content: object.responseText
    //         });
    //     });
    // });

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'删除',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysOutlets/delete',
                        data: {'id': id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
                                gtip(data.message, true, null);
                            } else {
                                gtip(data.message, false, null);
                            }
                        }
                    });
                    $(this).dialog("close");
                },
                '取消': function () {
                    $(this).dialog("close");
                }
            }
        }).html("确认删除？");
    });
});