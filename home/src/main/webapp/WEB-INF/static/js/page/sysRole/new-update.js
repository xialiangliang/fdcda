$(function(){

    var tree_updated_data = {};
    layui.use(['layer'], function () {
        var layer = layui.layer;

        /*树形表格 start*/
        var setting = {
            view: {
                addDiyDom: addDiyDom,
                showLine: true, //是否显示节点之间的连线
                showIcon: false, //是否显示节点的图标
                selectedMulti: false
            },
            check: {
                enable: true,
                autoCheckTrigger: true
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
            },
            callback: {
                onCheck: zTreeOnCheck
            }
        };
        function zTreeOnCheck(event, treeId, treeNode) {
            if (treeNode.checked) {
                tree_updated_data[treeNode.id] = '1';
            } else {
                tree_updated_data[treeNode.id] = '0';
            }
        };
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
            div.append(checkObj);
            div.append(spanObj);
            //隐藏了层次的span
            var spaceStr = "<span style='height:1px;display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
            switchObj.before(spaceStr);
            var editStr = '';
            //宽度需要和表头保持一致
            editStr += '<div class="diy" style="width:15%;">' + treeNode.id + '</div>';
            editStr += '<div class="diy" style="width:50%">' + treeNode.dir + '</div>';
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
                '        <div class="diy" style="width:50%">资源路径</div>\n' +
                '        <div class="diy" style="width:10%;">排序</div>\n' +
                // '        <div class="diy" style="width:15%; ">备注</div>\n' +
                // '        <div class="diy" style="width:15%; text-align: center;">操作</div>\n' +
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
            // html += '<button class="layui-btn layui-btn-normal layui-btn-xs v-middle dt-edit" data-id="'+treeNode.id+'">修改</button>';
            // html += '<button class="layui-btn layui-btn-danger layui-btn-xs v-middle dt-del" data-id="'+treeNode.id+'">删除</button>';
            return html;
        }

        $.get('/sysRole/getResourceWithAuth?id='+$('#id').val(), function (res) {
            queryHandler(res);
            var $dataTreeLi = $dataTree.find('li');
            $dataTreeLi.each(function () {
                var $thisLi = $(this);
                //删除操作
                $thisLi.delegate('.dt-del', 'click',  function (e) {
                    e = event || window.event;
                    var _this = $(this);
                    console.log(_this.data('id'));

                    layer.open({
                        title:'提示',
                        btn: ['确定', '取消'],
                        content: '你确定要删除ID为'+_this.data('id')+'的栏目么？',
                        yes: function(index){
                            //do something
                            $thisLi.remove();

                            /*$.post('', function (res) {
                                $thisLi.remove();
                            });*/
                            layer.close(index); //如果设定了yes回调，需进行手工关闭
                        }
                    });
                    e.stopPropagation ? e.stopPropagation() : e.cancelBubble=true;
                });

                //修改操作
                $thisLi.delegate('.dt-edit', 'click',  function (e) {
                    e = event || window.event;
                    var _this = $(this);
                    console.log(_this.data('id'));

                    location.href = './edit.php?id='+_this.data('id');
                    e.stopPropagation ? e.stopPropagation() : e.cancelBubble=true;
                });
            });
        });

        /*树形表格 End*/

    });
    
    
    var newForm = jQuery("#newForm_sysRole");
    var updateForm = jQuery("#updateForm_sysRole");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    // var dialog = $("#newUpdateDialogFrame");
    //
    //
    // var tree = [];
    // function parseResource(data, tree) {
    //     for (i in data) {
    //         var url = data[i].url;
    //         if (url.length > 50) {
    //             url = url.substring(0, 50) + '...';
    //         }
    //         var map = {
    //             text: ''
    //             + data[i].name
    //             + '<span style="width:20%;float:right;">' + data[i].memo + '</span>'
    //             + '<span style="width:5%;float:right;">' + data[i].sort + '</span>'
    //             + '<span style="width:40%;float:right;">' + url + '</span>'
    //             + '<span style="width:5%;float:right;">' + data[i].id + '</span>',
    //             state:{
    //                 expanded:false,
    //                 checked:data[i].auth === 1
    //             },
    //             id:data[i].id
    //         };
    //         if (data[i].subResource) {
    //             map.nodes = [];
    //             parseResource(data[i].subResource, map.nodes);
    //         }
    //         tree.push(map);
    //     }
    // }
    //
    // var tree_updated_data = {};
    //
    // $.get('/sysRole/getResourceWithAuth', {'id':$('#id').val()}, function (data) {
    //     if (data.success) {
    //         parseResource([
    //             {
    //                 name: '名称',
    //                 url: '资源路径',
    //                 id: 'ID',
    //                 sort: '排序',
    //                 memo: '备注'
    //             }
    //         ], tree);
    //         parseResource(data.res, tree);
    //
    //         $('#treeview').treeview({
    //             data: tree,
    //             selectedColor:"black",
    //             selectedBackColor:"white",
    //             showCheckbox: true,
    //             onNodeChecked: function(event, node) {
    //                 tree_updated_data[node.id] = '1';
    //             },
    //             onNodeUnchecked: function(event, node) {
    //                 tree_updated_data[node.id] = '0';
    //             }
    //         });
    //     }
    // });
    
    newForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                tip(data.message, true);
            } else {
                tip(data.message, false);
            }
        }
    });
    updateForm.ajaxForm({
        beforeSerialize: function () {
            $('#updatedData').val(JSON.stringify(tree_updated_data));
        },
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                tip(data.message, true);
            } else {
                tip(data.message, false);
            }
        }
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    })
});