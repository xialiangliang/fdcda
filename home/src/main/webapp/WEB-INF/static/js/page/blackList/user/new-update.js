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
    $(".btn-apply").click(function () {
        layer.msg("申请成功，等待审核",{time:1000},function () {
            history.back(-1);
        });
    });

    $('#uploadfile').fileinput({
        language: 'zh',
        uploadUrl: '#', //上传地址
        showPreview : true, //预览
        maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        maxFileCount: 2, //表示允许同时上传的最大文件个数
        //dropZoneTitle: '拖拽文件到这里 &hellip;',
        allowedFileExtensions : ['jpg','png']
    });
    
});