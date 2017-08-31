$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysRole/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
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
        $.get('/sysRole/new', {}, function (data, textStatus, object) {
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
});