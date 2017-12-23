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
            ,url:'/sysOutlets/sysDevice/listJson?outletsId=' + $('#outletsId').val()
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'seqno', title: '设备序列号', width:200}
                ,{field:'outletsName', title: '门店名', width:160}
                ,{field:'createTimeStr', title: '创建时间', width:200}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'25%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                // window.location.href = "/sysOutlets/find?id=" + data.id;
                $.get('/sysOutlets/sysDevice/find', {'id':data.id}, function (data, textStatus, object) {
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
                        url: '/sysOutlets/sysDevice/delete',
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
            }
        });


    });
    
    var dialog = $("#newUpdateDialogFrameDev");

    $(".j_update-dev-btn").click(function () {
        $.get('/sysOutlets/sysDevice/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });

    $(".j_new-dev-btn").click(function () {
        $.get('/sysOutlets/sysDevice/new', {'outletsId':$(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }
    $(".j_delete-dev-btn").click(function () {
        var id = $(this).attr("data-id");
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysOutlets/sysDevice/delete',
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
                    $(this).dialog("close");
                },
                '取消': function () {
                    $(this).dialog("close");
                }
            }
        });
    });
});