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
            ,url:'/evaluate/listJson'
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'orderRowId', title: '订单id', width:160}
                ,{field:'content', title: '评价内容', width:160}
                ,{field:'evaType', title: '评价类型：0差评1中评2好评', width:160}
                ,{field:'imagesUrl', title: '附件，多个图片url以英文逗号,隔开', width:160}
                ,{field:'createDateStr', title: '评价日期', width:160}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'15%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                window.location.href = "/evaluate/find?id=" + data.id;
            } else if(obj.event === 'del'){
                layer.confirm('确认删除？', function(index){
                    obj.del();
                    $.ajax({
                        url: '/evaluate/delete',
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
    
    var dialog = $("#newUpdateDialogFrame");

    function tip(msg, reload) {
        var action_tip = "<div class='pop-con-tip'>" + msg + "</div>";
        var tip_frame = $("#tipDialogFrame");
        tip_frame.html(action_tip);
        tip_frame.dialog({
            resizable: true,
            width: 300,
            modal: true,
            buttons: {
            }
        });
        setTimeout(function () {
            tip_frame.dialog("close");
            if (reload) {
                window.location.reload();
            }
        }, 1000);
    }
    
    $(".j_new-btn").click(function () {
        window.location.href = '/evaluate/new';
        // $.get('/evaluate/new', {}, function (data, textStatus, object) {
        //     layer.open({
        //         type: 1,
        //         content: object.responseText
        //     });
        // });
    });
    
});