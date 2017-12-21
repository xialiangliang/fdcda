$(function(){
    var form_modifyfPassword = jQuery("#form_modifyfPassword");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
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
                layer.close(layer.index);
                tip(data.message, false);
            } else {
                tip(data.message, false);
            }
        }
    });
    
    /*$(".btn-close").click(function () {
        // var index = parent.layer.getFrameIndex(window.name);
        // parent.layer.close(index);
        layer.close(layer.index);
        console.log('111');
        // ctrl + shift + f9
    });*/
});