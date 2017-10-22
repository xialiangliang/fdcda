$(function(){
    var dialog = $("#newUpdateDialogFrameDev");

    $(".j_update-dev-btn").click(function () {
        $.get('/sysOutlets/sysDevice/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
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

    $(".j_new-dev-btn").click(function () {
        $.get('/sysOutlets/sysDevice/new', {'outletsId':$(this).attr("data-id")}, function (data, textStatus, object) {
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
    $(".btn-close").click(function () {
        $("#newUpdateDialogFrame").dialog("close");
    });
    function tip(msg, reload) {
        var action_tip = "<div class='pop-con-tip'>" + msg + "</div>";
        var tip_frame = $("#tipDialogFrameDev");
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