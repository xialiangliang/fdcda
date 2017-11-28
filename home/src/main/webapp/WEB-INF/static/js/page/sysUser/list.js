$(function(){
    var dialog = $("#newUpdateDialogFrame");

    $(".j_update-btn").click(function () {
        $.get('/sysUser/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                title: "修改",
                content: object.responseText
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysUser/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                title: "新建",
                content: object.responseText
            });
        });
    });
});