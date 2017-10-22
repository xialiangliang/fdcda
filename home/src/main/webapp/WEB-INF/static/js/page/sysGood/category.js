$(function(){

    var dialog = $("#newUpdateSubDialogFrame");
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

    var tree = [];
    function parseResource(data, tree) {
        for (i in data) {
            var oper_item;
            if(data[i].oper) {
                oper_item = '<a href="#" class="btn j_new-btn col-sm-6"  style="color: #333;padding:1px;width:20px;margin-left:3px;border:1px solid #333;" onclick="newCategory(0)">+</a>';
            } else {
                oper_item = '<a href="#" class="btn j_new-btn col-sm-6"  style="color: #333;padding:1px;width:20px;margin-left:3px;border:1px solid #333;" onclick="newCategory(' + data[i].id + ')">+</a>';
                oper_item = oper_item + '<a href="#" class="btn j_new-btn col-sm-6"  style="color: #333;padding:1px;width:20px;margin-left:3px;border:1px solid #333;" onclick="delCategory(' + data[i].id + ')">-</a>';
                oper_item = oper_item + '<a href="#" class="btn j_update-btn col-sm-6"  style="color: #333;padding:1px;width:20px;margin-left:3px;border:1px solid #333;" onclick="updateCategory(' + data[i].id + ')">...</a>';
            }
            if (data[i].subCategory) {
                data[i].name = data[i].name + '(' + data[i].subCategory.length + ')';
            }
            var map = {
                text: ''
                // + '<span class="icon check-icon glyphicon glyphicon-unchecked"></span>'
                + data[i].name
                + '<span style="width:20%;float:right;">' + oper_item + '</span>',
                state:{
                    expanded:false
                }
            };
            if (data[i].subCategory) {
                map.nodes = [];
                parseResource(data[i].subCategory, map.nodes);
            }
            tree.push(map);
        }
    }
    
    $.get('/sysGood/category/listMap', {}, function (data, textStatus, object) {
        parseResource([
            {
                name:'名称',
                oper:'操作'
            }
        ], tree);
        parseResource(data.result, tree);

        $('#treeview').treeview({
            data:tree,
            selectedColor:"black",
            selectedBackColor:"white"
        });
    });

    updateCategory = function(id) {
        $.get('/sysGood/category/find', {'id': id}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 400,
                modal: true,
                title:'修改',
                buttons: {
                }
            });
        });
    };

    newCategory = function(id) {
        $.get('/sysGood/category/new', {'parentId': id}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 400,
                modal: true,
                title:'新增',
                buttons: {
                }
            });
        });
    };

    delCategory = function(id) {        
        $( "#dialog-confirm-cat" ).dialog({
            resizable: false,
            modal: true,
            title:'删除',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysGood/category/delete',
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
        }).html('确认删除吗？');
        
        
    };

    $(".btn-close").click(function () {
        dialog.dialog("close");
    });
});