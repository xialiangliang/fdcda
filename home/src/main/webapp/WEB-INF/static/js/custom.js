layui.use(['layer'], function () {
    var layer = layui.layer;
    if(is.ie(8)  || is.ie(7)){
        layer.alert('浏览器版本过低，为保证您正常的体验，请使用IE9+、chrome、360安全浏览器', {icon:5});
    }
});