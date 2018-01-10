layui.use(['layer'], function () {
    var layer = layui.layer;
    if(is.ie(8)  || is.ie(7)){
        layer.alert('浏览器版本过低，为保证您正常的体验，请使用IE9+、chrome、360安全浏览器', {icon:5});
    }

    //退出
    var $signOut = $('.site_signOut');
    $signOut.on('click', function () {
        layer.confirm('退出登录？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            //执行退出操作
            /*$.ajax({
                type: 'POST',
                url: '',
                dataType: 'json',
                success: function (res) {
                    location.href = './login.html';
                }
            })*/

            location.href = '/login/logout'

        });
    })
});

// 提示 参数：msg消息 reload是否重新加载当前页面 relocation重定向页面
function gtip(msg, reload, relocation) {
    layer.msg(msg,{time:1500},function () {
        if (reload) {
            window.location.reload();
        } else if (relocation) {
            window.location.href = relocation;
        }
    });
    layer.index = layer.index - 1;
}

