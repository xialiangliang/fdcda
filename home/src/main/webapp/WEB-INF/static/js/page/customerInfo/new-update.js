$(function(){
    var newForm = jQuery("#newForm_customerInfo");
    var updateForm = jQuery("#updateForm_customerInfo");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    var dialog = $("#newUpdateDialogFrame");

    newForm.ajaxForm({
        beforeSerialize: function () {
            if ($('#file').val() === '') {
                tip("请选择图片", false);
            }
        },
        success: function (data) {
            if (data.success) {
                // layer.close(layer.index);
                tip(data.message, false);
                window.location.href = "/customerInfo";
            } else {
                tip(data.message, false);
            }
        }
    });
    updateForm.ajaxForm({
        beforeSerialize: function () {
            if ($('#file').val() === '') {
                tip("请选择图片", false);
            }
        },
        success: function (data) {
            if (data.success) {
                // layer.close(layer.index);
                tip(data.message, false);
                window.location.href = "/customerInfo";
            } else {
                tip(data.message, false);
            }
        }
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
});