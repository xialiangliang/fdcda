$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysUser/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                title: '修改',
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysUser/new', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                title: '新建',
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });
});