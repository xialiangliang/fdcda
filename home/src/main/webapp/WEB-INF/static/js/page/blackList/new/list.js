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
            ,url:'/blackList/newBlack/list/listJson?nameStr=' + $('#nameStr').val() + '&phoneStr=' + $('#phoneStr').val() + '&cardStr=' + $('#cardStr').val()
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
                window.location.href = "/customerInfo/find?id=" + data.id;
            } else if (obj.event === 'black'){
                var str = '';
                if (data.isVip === 1) {
                    str = data.name + '已经添加到VIP，确认添加到黑名单？';
                }
                layer.confirm('确认添加到黑名单？', { title:'确认添加到黑名单？',
                    content: '<p>' + str + '</p></p><textarea placeholder="拉黑原因" rows="3" cols="50" class="layui-textarea"></textarea>'
                }, function(index, layero){
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
            } else if (obj.event === 'applyblack'){
                if (data.isVip === 1) {
                    layer.confirm('已经添加到VIP，确认添加到黑名单？', function (index) {
                        layer.close(index);
                        window.location.href = "/blackList/newBlack/applyUserBlacklist/page?id=" + data.id;
                    });
                } else {
                    window.location.href = "/blackList/newBlack/applyUserBlacklist/page?id=" + data.id;
                }
            }
        });


    });
});