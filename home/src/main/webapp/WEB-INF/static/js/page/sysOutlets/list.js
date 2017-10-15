$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysOutlets/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysOutlets/new', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });

    $(".j_device-btn").click(function () {
        $.get('/sysOutlets/sysDevice/list', {'outletsId': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                title:"设备列表",
                modal: true,
                buttons: {
                }
            });
        });
    });
});