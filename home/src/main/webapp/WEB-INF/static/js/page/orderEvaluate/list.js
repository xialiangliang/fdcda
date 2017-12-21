$(function(){
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
    
    $(".j_update-btn").click(function () {
        $.get('/orderEvaluate/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                title: '修改',
                modal: true,
                buttons: {
                }
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/orderEvaluate/new', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                title: '新建',
                modal: true,
                buttons: {
                }
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
                        url: '/orderEvaluate/delete',
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