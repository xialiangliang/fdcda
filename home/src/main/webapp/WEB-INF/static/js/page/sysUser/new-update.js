$(function(){
    var newForm = jQuery("#newForm_sysUser");
    var updateForm = jQuery("#updateForm_sysUser");

    function tip(msg, reload) {
        var action_tip = "<div class='pop-con-tip'>" + msg + "</div>";
        var tip_frame = $("#tipDialogFrame");
        tip_frame.html(action_tip);
        tip_frame.dialog({
            resizable: true,
            width: 300,
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
        beforeSerialize: function () {
            var publicKey = "";
            $.ajax({
                url :'/sysUser/getPublicKey',
                data:{},
                async : false,
                success:function (data) {
                    publicKey = data.publicKey;
                }
            });
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            var encrypted = encrypt.encrypt($('#loginpwd').val());
            $('#loginpwd').val(encrypted);
        },
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
    });
    
    $(".btn-resetpwd").click(function () {
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysUser/resetPassword',
                        data: {'userId': $('#id').val()},
                        async: false,
                        success: function (data) {
                            if (data.success) {
                                dialog.dialog("close");
                                tip(data.message, true);
                            } else {
                                tip(data.message, false);
                            }
                        }
                    });
                    $(this).dialog("close");
                },
                '取消': function () {
                    $(this).dialog("close");
                }
            }
        });
    });
});