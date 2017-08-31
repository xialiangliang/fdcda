var top;
$(function () {

    $(".j_update-btn").click(function () {
        $.get('/sysUser/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            $("#newUpdateDialogFrame").html(object.responseText);
            $("#newUpdateDialogFrame").dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    })

    $(".j_new-btn").click(function () {
        $.get('/sysUser/new', {}, function (data, textStatus, object) {
            $(object.responseText).dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                    "取消": function () {
                        $(this).dialog('close');
                    }
                }
            });
        });
    });
});