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
            ,url:'/blackList/user/listJson?nameStr=' + $('#nameStr').val() + '&phoneStr=' + $('#phoneStr').val() + '&cardStr=' + $('#cardStr').val()
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
                ,{field:'blackTimeStr', title: '拉黑时间', width:220}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'25%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                //layer.msg('ID：'+ data.id + ' 的查看操作');
                window.location.href = "/blackList/user/find?id=" + data.id;
            } else if(obj.event === 'rmblack'){
                layer.confirm('确认删除？', function(index){
                    $.ajax({
                        url: '/blackList/user/remove',
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
            } else if (obj.event === 'sysblack'){
                window.location.href = "/blackList/user/applySystemBlacklist/page?id=" + data.id;
                // layer.confirm('确认添加到系统黑名单？', function(index){
                //     $.ajax({
                //         url: '/blackList/user/applySystemBlacklist',
                //         data: {'id': data.id},
                //         async: false,
                //         success: function (data) {
                //             if (data.success) {
                //                 tip(data.message, true);
                //             } else {
                //                 tip(data.message, false);
                //             }
                //         }
                //     });
                //     layer.close(index);
                // });
            } else if(obj.event === 'edit'){
                // layer.alert('编辑行：<br>'+ JSON.stringify(data))
                window.location.href = "/customerInfo/find?id=" + data.id;
            }
        });


    });

    $(".j_new-btn").click(function () {
        window.location.href = "/blackList/user/new";
    });

    $(".j_rmblack-btn").click(function () {
        var id = $(this).attr("data-id")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'删除',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/blackList/user/remove',
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
        }).html("确认移除？");
    });

    $(".j_applysysblack-btn").click(function () {
        var id = $(this).attr("data-id")
        window.location.href = "/blackList/user/applySystemBlacklist/page?id=" + id;
        // $( "#dialog-confirm" ).dialog({
        //     resizable: false,
        //     modal: true,
        //     title:'删除',
        //     buttons: {
        //         '确定': function () {
        //             $.ajax({
        //                 url: '/blackList/user/applySystemBlacklist',
        //                 data: {'id': id},
        //                 async: false,
        //                 success: function (data) {
        //                     if (data.success) {
        //                         tip(data.message, true);
        //                     } else {
        //                         tip(data.message, false);
        //                     }
        //                 }
        //             });
        //             $(this).dialog("close");
        //         },
        //         '取消': function () {
        //             $(this).dialog("close");
        //         }
        //     }
        // }).html("申请系统黑名单？");
    });
});