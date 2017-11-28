$(function(){
    var dialog = $("#newUpdateDialogFrameDev");

    $(".j_update-dev-btn").click(function () {
        $.get('/sysOutlets/sysDevice/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });

    $(".j_new-dev-btn").click(function () {
        $.get('/sysOutlets/sysDevice/new', {'outletsId':$(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }
    $(".j_delete-dev-btn").click(function () {
        var id = $(this).attr("data-id");
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysOutlets/sysDevice/delete',
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
        });
    });
});