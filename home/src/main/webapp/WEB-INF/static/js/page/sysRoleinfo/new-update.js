$(function(){
    var newForm = jQuery("#newForm_sysRoleInfo");
    var updateForm = jQuery("#updateForm_sysRoleInfo");

    function tip(msg, reload) {
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
            if (reload) {
                window.location.reload();
            }
        }, 1000);
    }

    var dialog = $("#newUpdateDialogFrame");

    newForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                dialog.dialog("close");
                tip(data.message, true);
            } else {
                tip(data.message, false);
            }
        }
    });
    updateForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                dialog.dialog("close");
                tip(data.message, true);
            } else {
                tip(data.message, false);
            }
        }
    });
    $(".btn-close").click(function () {
        dialog.dialog("close");
    })
});