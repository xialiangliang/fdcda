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
            ,url:'/order/listJson'
            ,height:500
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , page:true
            ,cols: [[
                {field:'userRowId', title: '经营户id', width:160}
                ,{field:'customerRowId', title: '采购商id', width:160}
                ,{field:'orderContent', title: '交易内容', width:160}
                ,{field:'orderAmt', title: '交易金额', width:160}
                ,{field:'payType', title: '付款方式', width:160, width:160, templet: '#payType'}
                ,{field:'goodDateStr', title: '交货日期', width:160}
                ,{field:'toaddress', title: '收货地址', width:160}
                ,{field:'remark', title: '备注', width:160}
                ,{field:'createDateStr', title: '创建日期', width:160}
                ,{fixed:'right',  align:'center', toolbar: '#barDemo', title:'操作', width:'15%'}
            ]]
        });

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'evaluate'){
                window.location.href = "/evaluate/detail?id=" + data.id;
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