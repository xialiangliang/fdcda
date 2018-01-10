$(function(){
    var newForm = jQuery("#newForm_customerInfo");
    var updateForm = jQuery("#updateForm_customerInfo");

    newForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                gtip(data.message, true, null);
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    updateForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                gtip(data.message, true, null);
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
});