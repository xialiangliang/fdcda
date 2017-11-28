$(function(){
    var dialog = $("#newUpdateDialogFrame");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }
    
    $(".j_update-btn").click(function () {
        $.get('/imageBaseSend/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                title: "修改",
                content: object.responseText
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/imageBaseSend/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                title: "新建",
                content: object.responseText
            });
        });
    });

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'删除',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/imageBaseSend/delete',
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
        }).html("确认删除？");
    });
});