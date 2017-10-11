$(function(){
    var form_modifyfPassword = jQuery("#form_modifyfPassword");

    function tip(msg, reload) {
        var action_tip = "<div class='pop-con-tip'>" + msg + "</div>";
        var tip_frame = $("#tipDialog");
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

    var dialog = $("#modifyPasswordDialog");

    var publicKey = '';

    var encrypt = new JSEncrypt();
    form_modifyfPassword.ajaxForm({
        beforeSerialize:function() {
            $.ajax({
                url :'/sysUser/getPublicKey',
                data:{},
                async : false,
                success:function (data) {
                    publicKey = data.publicKey;
                }
            });
        },
        data: {
            'originPwd':function() {
                var token = $('#token').val();
                var phone = $('#phone').val();
                var salt = '';
                $.ajax({
                    url :'/login/getSalt',
                    data:{'phone': phone},
                    async : false,
                    success:function (data) {
                        salt = data.salt;
                    }
                });
                // 校验原密码
                var password = sha512(sha512($('#originPwd').val() + salt) + token);
                return password;
            },
            'newPwd': function () {
                encrypt.setPublicKey(publicKey);
                var encrypted = encrypt.encrypt($('#newPwd').val());
                return encrypted;
            },
            'confirmNewPwd': function () {
                encrypt.setPublicKey(publicKey);
                var encrypted2 = encrypt.encrypt($('#confirmNewPwd').val());
                return encrypted2;
            }
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
    
    $(".btn-close").click(function () {
        dialog.dialog("close");
    });
});