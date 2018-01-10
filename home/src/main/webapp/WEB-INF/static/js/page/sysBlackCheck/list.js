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
            ,url:'/sysBlackCheck/listJson'
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'userName', title: '提交人', width:160}
                ,{field:'createDateStr', title: '提交时间', width:180}
                ,{field:'customerName', title: '采购商姓名', width:160, templet: '#genderId'}
                // ,{field:'nationalityStr', title: '国籍', align: 'center', width:120}
                ,{field:'customerPhone', title: '采购商手机号', width:180, templet: '#addrId'}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'20%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                //layer.msg('ID：'+ data.id + ' 的查看操作');
                window.location.href = "/sysBlackCheck/find?id=" + data.id;
            }
        });


    });
    
    $(".j_update-btn").click(function () {
        window.location.href = "/customerInfo/find?id=" + $(this).attr("data-id");
        // $.get('/customerInfo/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
        //     layer.open({
        //         type: 1,
        //         title: "修改",
        //         content: object.responseText
        //     });
        // });
    });

    // $(".j_new-btn").click(function () {
    //     window.location.href = "/customerInfo/new";
    //     // $.get('/customerInfo/new', {}, function (data, textStatus, object) {
    //     //     layer.open({
    //     //         type: 1,
    //     //         title: "新建",
    //     //         content: object.responseText
    //     //     });
    //     // });
    // });

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