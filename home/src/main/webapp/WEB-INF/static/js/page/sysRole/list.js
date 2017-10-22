$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysRole/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 800,
                modal: true,
                buttons: {
                }
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysRole/new', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 400,
                modal: true,
                buttons: {
                }
            });
        });
    });
    
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

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id");
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            buttons: {
                '确定': function () {
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
                    $(this).dialog("close");
                },
                '取消': function () {
                    $(this).dialog("close");
                }
            }
        });
    });
});