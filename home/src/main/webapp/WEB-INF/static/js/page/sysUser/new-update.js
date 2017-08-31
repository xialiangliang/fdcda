$(function () {
    var newForm = jQuery("#newForm_sysUser");
    var updateForm = jQuery("#updateForm_sysUser");
    
    function tip(msg) {
        var action_tip = "<div class='pop-con-tip'>" + msg + "</div>";
        var tip_frame = $("#tipDialogFrame");
        tip_frame.html(action_tip);
        tip_frame.dialog({
            resizable: true,
            width: 600,
            modal: true,
            buttons: {
            }
        });
        setTimeout(function () {
            tip_frame.dialog("close");
        }, 1000);
    }

    var dialog = $("#newUpdateDialogFrame");

    newForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                dialog.dialog("close");
                tip(data.message);
            } else {
                tip(data.message);
            }
        }
    });
    updateForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                dialog.dialog("close");
                tip(data.message);
            } else {
                tip(data.message);
            }
        }
    });
});