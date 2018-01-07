$(function(){
    var newForm = jQuery("#newForm_customerInfo");
    var updateForm = jQuery("#updateForm_customerInfo");
    var newBlackForm = jQuery("#newForm_newBlack");

    function tip(msg, reload, relocation) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            } else if (relocation) {
                window.location.href = relocation;
            }
        });
    }


    layui.use(['laydate', 'form'], function(){
        var laydate = layui.laydate, form = layui.form;
        //执行一个laydate实例
        laydate.render({
            elem: '#delivery_date' //指定元素
            ,value: new Date()
        });

        // 初始化Web Uploader
        var uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: false,
            // swf文件路径
            swf:  '/js/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: 'http://webuploader.duapp.com/server/fileupload.php',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: '#filePicker',
                multiple: false
            },
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        // 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {
            var $li = $(
                '<div id="' + file.id + '" >' +
                '<img>' +
                // '<div class="info">' + file.name + '</div>' +
                '</div>'
                ),
                $img = $li.find('img');

            // $list为容器jQuery实例
            $('#fileList').html( $li );

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            var thumbnailWidth = 155, thumbnailHeight=208;
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }
                $img.attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });

        newForm.ajaxForm({
            beforeSerialize: function () {
            },
            success: function (data) {
                if (data.success) {
                    // layer.close(layer.index);
                    gtip(data.message, false, '/customerInfo');
                } else {
                    gtip(data.message, false, null);
                }
            }
        });
        updateForm.ajaxForm({
            beforeSerialize: function () {
                if ($('file').val() === '') {
                    // tip("请选择图片", false);
                }
            },
            success: function (data) {
                if (data.success) {
                    // layer.close(layer.index);
                    gtip(data.message, false, '/customerInfo');
                } else {
                    gtip(data.message, false, null);
                }
            }
        });
    });
    newBlackForm.ajaxForm({
        success: function (data) {
            if (data.success) {
                // layer.close(layer.index);
                gtip(data.message, false, "/blackList/user");
            } else {
                gtip(data.message, false, null);
            }
        }
    });
    $(".btn-close").click(function () {
        layer.close(layer.index);
    });
});