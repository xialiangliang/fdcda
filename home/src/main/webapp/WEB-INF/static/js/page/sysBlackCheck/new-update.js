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

    newForm.ajaxForm({
        beforeSerialize: function () {
            if ($('#file').val() === '') {
                gtip("请选择图片", false, null);
            }
        },
        success: function (data) {
            if (data.success) {
                // layer.close(layer.index);
                gtip(data.message, false, "/customerInfo");
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    updateForm.ajaxForm({
        beforeSerialize: function () {
            if ($('#file').val() === '') {
                gtip("请选择图片", false, null);
            }
        },
        success: function (data) {
            if (data.success) {
                // layer.close(layer.index);
                gtip(data.message, false, "/customerInfo");
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    $(".btn-success").click(function () {
        layer.confirm('确认通过？', function (index) {
            $.ajax({
                url: '/sysBlackCheck/check',
                data: {'id': $('#id').val(), 'status':1},
                async: false,
                success: function (data) {
                    if (data.success) {
                        layer.msg(data.message,{time:1000},function () {
                            window.location.href = '/sysBlackCheck';
                        });
                    } else {
                        layer.msg(data.message,{time:1000},function () {
                        });
                    }
                }
            });
            layer.close(index);
        });
    });
    $(".btn-failed").click(function () {
        layer.confirm('确认驳回？', function (index) {
            $.ajax({
                url: '/sysBlackCheck/check',
                data: {'id': $('#id').val(), 'status':2},
                async: false,
                success: function (data) {
                    if (data.success) {
                        layer.msg(data.message,{time:1000},function () {
                            window.location.href = '/sysBlackCheck';
                        });
                    } else {
                        layer.msg(data.message,{time:1000},function () {
                        });
                    }
                }
            });
            layer.close(index);
        });
    });
});