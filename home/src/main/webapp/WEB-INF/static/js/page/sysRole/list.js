$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysRole/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                area:'800px',
                type: 1,
                content: object.responseText
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysRole/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });
    
    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id");
        layer.confirm('确认删除？', function(index){
            $.ajax({
                url: '/sysRole/delete',
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
            layer.close(index);
        });
    });
});