$(function(){
    var newForm = jQuery("#newForm_sysUser");
    var updateForm = jQuery("#updateForm_sysUser");

    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }

    var dialog = $("#newUpdateDialogFrame");

    newForm.ajaxForm({
        beforeSerialize:function () {
            
        },
        data: {
            'loginpwd': function () {
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
                return encrypted;
            }
        },
        success: function (data) {
            if (data.success) {
                layer.close(layer.index);
                var htmlStr = '手机号：' + data.phone + '<br>'
                    + '登录名：' + data.loginname + '<br>'
                    + '密码：' + data.password;
                layer.open({
                    type: 1,
                    title: "新用户",
                    content: htmlStr,
                    btn: ['确定'],
                    yes: function(index, layero){
                        layer.close(layer.index);
                    }
                });
            } else {
                tip(data.message, false);
                $('#loginpwd').val('');
            }
        }
    });
    updateForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                // layer.close(layer.index);
                tip(data.message, false);
                window.location.href = '/sysUser';
            } else {    
                tip(data.message, false);
            }
        }
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
    
    $(".btn-resetpwd").click(function () {
        layer.confirm('确认重置密码？', function(index){
            $.ajax({
                url: '/sysUser/resetPassword',
                data: {'userId': $('#id').val()},
                async: false,
                success: function (data) {
                    if (data.success) {
                        layer.close(layer.index);
                        var htmlStr = '手机号：' + data.phone + '<br>'
                            + '登录名：' + data.loginname + '<br>'
                            + '密码：' + data.password;
                        layer.open({
                            type: 1,
                            title: "新密码",
                            content: htmlStr
                        });
                    } else {
                        tip(data.message, false);
                    }
                }
            });
            layer.close(index);
        });
        // $( "#dialog-confirm" ).dialog({
        //     resizable: false,
        //     modal: true,
        //     buttons: {
        //         '确定': function () {
        //             $.ajax({
        //                 url: '/sysUser/resetPassword',
        //                 data: {'userId': $('#id').val()},
        //                 async: false,
        //                 success: function (data) {
        //                     if (data.success) {
        //                         layer.close(layer.index);
        //                         var htmlStr = '手机号：' + data.phone + '<br>'
        //                             + '登录名：' + data.loginname + '<br>'
        //                             + '密码：' + data.password;
        //                         layer.open({
        //                             type: 1,
        //                             title: "新密码",
        //                             content: htmlStr
        //                         });
        //                     } else {
        //                         tip(data.message, false);
        //                     }
        //                 }
        //             });
        //             $(this).dialog("close");
        //         },
        //         '取消': function () {
        //             $(this).dialog("close");
        //         }
        //     }
        // });
    });
});