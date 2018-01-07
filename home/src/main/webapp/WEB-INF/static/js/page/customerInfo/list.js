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
            ,url:'/customerInfo/listJson?nameStr=' + $('#nameStr').val() + '&phoneStr=' + $('#phoneStr').val()
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'name', title: '用户名', width:160}
                ,{field:'phone', title: '手机', width:160}
                ,{field:'gender', title: '性别', width:80, templet: '#genderId'}
                // ,{field:'nationalityStr', title: '国籍', align: 'center', width:120}
                ,{field:'province', title: '籍贯', width:120, templet: '#addrId'}
                ,{field:'companyName', title: '单位', width:220}
                ,{field:'createTimeStr', title: '创建时间', width:220}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'15%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                //layer.msg('ID：'+ data.id + ' 的查看操作');
                window.location.href = "/customerInfo/detail?id=" + data.id;
            } else if(obj.event === 'del'){
                layer.confirm('确认删除？', function(index){
                    obj.del();
                    $.ajax({
                        url: '/customerInfo/delete',
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
            } else if (obj.event === 'black'){
                layer.confirm('确认添加到黑名单？', { title:'确认添加到黑名单？',
                    content: '<textarea placeholder="拉黑原因" rows="3" cols="50" class="layui-textarea"></textarea>'
                }, function(index){
                    $.ajax({
                        url: '/customerInfo/addToBlackList',
                        data: {'id': data.id, 'reason':layero.find('textarea')[0].value},
                        async: false,
                        success: function (data) {
                            if (data.success) {
                                gtip(data.message, true, null);
                            } else {
                                gtip(data.message, false, null);
                            }
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                window.location.href = "/customerInfo/find?id=" + data.id;
            } else if(obj.event === 'addVip'){
                layer.confirm('确认添加到VIP？', function(index){
                    $.ajax({
                        url: '/customerInfo/addVip',
                        data: {'id': data.id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
                                gtip(data.message, true, null);
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

    $(".j_new-btn").click(function () {
        window.location.href = "/customerInfo/new";
    });

    $(".j_batchnew-btn").click(function () {
        window.location.href = "/customerInfo/batchnew";
    });

    // $(".j_delete-btn").click(function () {
    //     var id = $(this).attr("data-id")
    //     $( "#dialog-confirm" ).dialog({
    //         resizable: false,
    //         modal: true,
    //         title:'删除',
    //         buttons: {
    //             '确定': function () {
    //                 $.ajax({
    //                     url: '/customerInfo/delete',
    //                     data: {'id': id},
    //                     async: false,
    //                     success: function (data) {
    //                         if (data.success) {
    //                             tip(data.message, true);
    //                         } else {
    //                             tip(data.message, false);
    //                         }
    //                     }
    //                 });
    //                 $(this).dialog("close");
    //             },
    //             '取消': function () {
    //                 $(this).dialog("close");
    //             }
    //         }
    //     }).html("确认删除？");
    // });
    //
    // $(".j_black-btn").click(function () {
    //     var id = $(this).attr("data-id")
    //     $( "#dialog-confirm" ).dialog({
    //         resizable: false,
    //         modal: true,
    //         title:'提示',
    //         buttons: {
    //             '确定': function () {
    //                 $.ajax({
    //                     url: '/customerInfo/addToBlackList',
    //                     data: {'id': id},
    //                     async: false,
    //                     success: function (data) {
    //                         if (data.success) {
    //                             tip(data.message, true);
    //                         } else {
    //                             tip(data.message, false);
    //                         }
    //                     }
    //                 });
    //                 $(this).dialog("close");
    //             },
    //             '取消': function () {
    //                 $(this).dialog("close");
    //             }
    //         }
    //     }).html("确认添加到黑名单？");
    // });
});