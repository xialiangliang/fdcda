$(function(){
    var newForm = jQuery("#newForm_orderEvaluate");
    var updateForm = jQuery("#updateForm_orderEvaluate");

    var dialog = $("#newUpdateDialogFrame");

    newForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                dialog.dialog("close");
                gtip(data.message, true, null);
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    updateForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                dialog.dialog("close");
                gtip(data.message, true, null);
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    $(".btn-close").click(function () {
        dialog.dialog("close");
    });
});