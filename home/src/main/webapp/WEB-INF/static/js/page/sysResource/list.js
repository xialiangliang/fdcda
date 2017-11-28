$(function(){
    var dialog = $("#newUpdateDialogFrame");
    var tree = [];
    
    function parseResource(data, tree) {
        for (i in data) {
            var oper_item;
            if(data[i].oper) {
                oper_item = data[i].oper;
            } else {
                oper_item = '<a href="#" class="j_update-btn" onclick="update_resource(' + data[i].id + ')">修改</a>';
            }
            var url = data[i].url;
            if (url.length > 50) {
                url = url.substring(0, 50) + '...';
            }
            var map = {
                text: ''
                // + '<span class="icon check-icon glyphicon glyphicon-unchecked"></span>'
                + data[i].name
                + '<span style="width:10%;float:right;">' + oper_item + '</span>'
                + '<span style="width:20%;float:right;">' + data[i].memo + '</span>'
                + '<span style="width:5%;float:right;">' + data[i].sort + '</span>'
                + '<span style="width:40%;float:right;">' + url + '</span>'
                + '<span style="width:5%;float:right;">' + data[i].id + '</span>',
                state:{
                    expanded:false
                }
            };
            if (data[i].subResource) {
                map.nodes = [];
                parseResource(data[i].subResource, map.nodes);
            }
            tree.push(map);
        }
    }
    
    $.get('/sysResource/listMap', {}, function (data, textStatus, object) {
        parseResource([
            {
                name:'名称',
                url:'资源路径',
                id:'ID',
                sort:'排序',
                memo:'备注',
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

    $(".j_new-btn").click(function () {
        $.get('/sysResource/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });


    update_resource = function(id) {
        var dialog = $("#newUpdateDialogFrame");
        $.get('/sysResource/find', {'id': id}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    };
});