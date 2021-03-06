$(function(){
    layui.use(['layer', 'form'], function () {
        var layer = layui.layer, form = layui.form;

        /*树形表格 start*/
        var setting = {
            view: {
                addDiyDom: addDiyDom,
                showLine: true, //是否显示节点之间的连线
                showIcon: false, //是否显示节点的图标
                selectedMulti: false
            },
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                }
            },
            edit: {
                enable: false
            }
        };
        // ctrl  +shift + f9
        var $dataTree = $("#dataTree");
        function addDiyDom(treeId, treeNode) {
            var spaceWidth = 15;
            var liObj = $("#" + treeNode.tId),
                switchObj = $("#" + treeNode.tId + "_switch"),
                checkObj = $("#" + treeNode.tId + "_check"),
                aObj = $("#" + treeNode.tId + "_a"),
                icoObj = $("#" + treeNode.tId + "_ico"),
                spanObj = $("#" + treeNode.tId + "_span");

            aObj.attr('title', '');
            aObj.append('<div class="diy switch" style="width:25%"></div>');
            var div = $(liObj).find('div').eq(0);
            //从默认的位置移除
            switchObj.remove();
            checkObj.remove();
            spanObj.remove();
            icoObj.remove();
            //在指定的div中添加

            div.append(switchObj);
            // div.append(checkObj);
            div.append(spanObj);
            //隐藏了层次的span
            var spaceStr = "<span style='height:1px;display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
            switchObj.before(spaceStr);
            var editStr = '';
            //宽度需要和表头保持一致
            editStr += '<div class="diy" style="width:15%;">' + treeNode.id + '</div>';
            editStr += '<div class="diy" style="width:20%">' + treeNode.dir + '</div>';
            editStr += '<div class="diy" style="width:10%;">' + treeNode.sort + '</div>';
            // editStr += '<div class="diy" style="width:15%;">' + treeNode.channel_comment + '</div>';
            editStr += '<div class="diy" style="width:15%; text-align: center">' + formatHandle(treeNode) + '</div>';
            aObj.append(editStr);
        }

        //初始化列表
        function queryHandler(zTreeNodes){
            //初始化树
            $.fn.zTree.init($dataTree, setting, zTreeNodes);
            //添加表头
            var li_head = '<li class="head">\n' +
                '    <a>\n' +
                '        <div class="diy" style="width:25%; ">名称</div>\n' +
                '        <div class="diy" style="width:15%;">ID</div>\n' +
                '        <div class="diy" style="width:20%">资源路径</div>\n' +
                '        <div class="diy" style="width:10%;">排序</div>\n' +
                // '        <div class="diy" style="width:15%; ">备注</div>\n' +
                '        <div class="diy" style="width:15%; text-align: center;">操作</div>\n' +
                '    </a>\n' +
                '</li>';
            var rows = $dataTree.find('li');
            if (rows.length > 0) {
                rows.eq(0).before(li_head)
            } else {
                $dataTree.append(li_head);
                $dataTree.append('<li ><div style="text-align: center;line-height: 30px;" >无符合条件数据</div></li>')
            }
        }

        function formatHandle(treeNode) {
            var html = '';
            //html += '<button class="layui-btn layui-btn-primary layui-btn-xs v-middle dt-view" data-id="'+treeNode.id+'" >查看</button>';
            html += '<button class="layui-btn layui-btn-normal layui-btn-xs v-middle dt-edit" data-id="'+treeNode.id+'">修改</button>';
            html += '<button class="layui-btn layui-btn-danger layui-btn-xs v-middle dt-del" data-id="'+treeNode.id+'">删除</button>';
            return html;
        }

        $.get('/sysResource/listJson', function (res) {
            queryHandler(res);
            var $dataTreeLi = $dataTree.find('li');
            $dataTreeLi.each(function () {
                var $thisLi = $(this);
                //删除操作
                $thisLi.delegate('.dt-del', 'click',  function (e) {
                    e = event || window.event;
                    var _this = $(this);
                    console.log(_this.data('id'));

                    layer.confirm('确认删除？', function(index){
                        $.ajax({
                            url: '/sysResource/delete',
                            data: {'id': _this.data('id')},
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
                    e.stopPropagation ? e.stopPropagation() : e.cancelBubble=true;
                });

                //修改操作
                $thisLi.delegate('.dt-edit', 'click',  function (e) {
                    e = event || window.event;
                    var _this = $(this);
                    // console.log(_this.data('id'));

                    // location.href = './edit.php?id='+_this.data('id');

                    $.get('/sysResource/find', {'id':_this.data('id')}, function (data, textStatus, object) {
                        layer.open({
                            area: '500px',
                            type: 1,
                            content: object.responseText,
                            success: function (index) {
                                // alert(1);
                                form.render();
                            }
                        });
                    });
                    e.stopPropagation ? e.stopPropagation() : e.cancelBubble=true;
                });
            });
        });

        /*树形表格 End*/


        $(".j_new-btn").click(function () {
            $.get('/sysResource/new', {}, function (data, textStatus, object) {
                layer.open({
                    area: '500px',
                    type: 1,
                    content: object.responseText,
                    success: function (index) {
                        // alert(1);
                        form.render();
                    }
                });
            });
        });

    });
});