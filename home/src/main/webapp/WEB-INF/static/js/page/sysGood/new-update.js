$(function(){
    var newForm = jQuery("#newForm_sysGood");
    var updateForm = jQuery("#updateForm_sysGood");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    var dialog = $("#newUpdateDialogFrame");

    newForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                tip(data.message, true);
            } else {
                tip(data.message, false);
            }
        }
    });
    updateForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                tip(data.message, true);
            } else {
                tip(data.message, false);
            }
        }
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
});