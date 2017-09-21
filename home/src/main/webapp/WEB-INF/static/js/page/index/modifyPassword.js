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

    form_modifyfPassword.ajaxForm({
        beforeSerialize:function() {
            var token = $('#token').val();
            var salt = $();
            var phone = $('#phone').val();
            $.ajax({
                url :'/login/getSalt',
                data:{'phone': phone},
                async : false,
                success:function (data) {
                    salt = data.salt;
                }
            });
            var password = sha512(sha512($('#password').val() + salt) + token);
            $('#password').val(password);
            $('#phone').attr('readonly', '');
            $('#password').attr('readonly', '');
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