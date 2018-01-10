$(function(){
    var newForm = jQuery("#newForm_sysOutlets");
    var updateForm = jQuery("#updateForm_sysOutlets");

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
                // 弹出提示
                gtip(data.message, false, null);
            }
        }
    });
    
    // 关闭对话框
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
});