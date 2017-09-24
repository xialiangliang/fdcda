$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysGood/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
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
        $.get('/sysGood/new', {}, function (data, textStatus, object) {
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
    
    $(".j_category-btn").click(function () {
        $.get('/sysGood/category', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                height:600,
                modal: true,
                title:'分类',
                buttons: {
                }
            });
        });
    });
});