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
            ,url:'/blackList/system/listJson?nameStr=' + $('#nameStr').val() + '&phoneStr=' + $('#phoneStr').val()
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'name', title: '用户名', width:160}
                ,{field:'phone', title: '手机', width:160}
                ,{field:'gender', title: '性别', width:80, templet: '#genderId'}
                // ,{field:'nationalityStr', title: '国籍', align: 'center', width:120}
                ,{field:'provinceStr', title: '籍贯', width:120, templet: '#addrId'}
                ,{field:'companyName', title: '单位', width:220}
                ,{field:'modifyTimeStr', title: '拉黑时间', width:220}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'10%'}
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
    
    var dialog = $("#newUpdateDialogFrame");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }
    
    $(".j_update-btn").click(function () {
        $.get('/blackList/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            layer.open({
                type: 1,
                title: "修改",
                content: object.responseText
            });
        });
    });

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'删除',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/customerInfo/delete',
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
        }).html("确认删除？");
    });
});