$(function(){
    var newForm = jQuery("#newForm_sysRole");
    var updateForm = jQuery("#updateForm_sysRole");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    var dialog = $("#newUpdateDialogFrame");


    var tree = [];
    function parseResource(data, tree) {
        for (i in data) {
            var url = data[i].url;
            if (url.length > 50) {
                url = url.substring(0, 50) + '...';
            }
            var map = {
                text: ''
                + data[i].name
                + '<span style="width:20%;float:right;">' + data[i].memo + '</span>'
                + '<span style="width:5%;float:right;">' + data[i].sort + '</span>'
                + '<span style="width:40%;float:right;">' + url + '</span>'
                + '<span style="width:5%;float:right;">' + data[i].id + '</span>',
                state:{
                    expanded:false,
                    checked:data[i].auth === 1
                },
                id:data[i].id
            };
            if (data[i].subResource) {
                map.nodes = [];
                parseResource(data[i].subResource, map.nodes);
            }
            tree.push(map);
        }
    }
    
    var tree_updated_data = {};
    
    $.get('/sysRole/getResourceWithAuth', {'id':$('#id').val()}, function (data) {
        if (data.success) {
            parseResource([
                {
                    name: '名称',
                    url: '资源路径',
                    id: 'ID',
                    sort: '排序',
                    memo: '备注'
                }
            ], tree);
            parseResource(data.res, tree);

            $('#treeview').treeview({
                data: tree,
                selectedColor:"black",
                selectedBackColor:"white",
                showCheckbox: true,
                onNodeChecked: function(event, node) {
                    tree_updated_data[node.id] = '1';
                },
                onNodeUnchecked: function(event, node) {
                    tree_updated_data[node.id] = '0';
                }
            });
        }
    });
    
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