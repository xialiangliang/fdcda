$(function(){
    var dialog = $("#newUpdateDialogFrame");
    
    function tip(msg, reload) {
        layer.msg(msg,{time:1000},function () {
            if (reload) {
                window.location.reload();
            }
        });
    }
    $(".j_update-btn").click(function () {
        $.get('/sysGood/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                content: object.responseText
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysGood/new', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                title: "修改密码",
                content: object.responseText
            });
        });
    });
    
    $(".j_category-btn").click(function () {
        $.get('/sysGood/category', {}, function (data, textStatus, object) {
            layer.open({
                type: 1,
                title: "分类",
                content: object.responseText
            });
        });
    });

    $(".j_on-btn").click(function () {
        var id = $(this).attr("data-id");
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'上架',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysGood/on',
                        data: {'id': id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
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
        }).html("确认上架？");
    });

    $(".j_off-btn").click(function () {
        var id = $(this).attr("data-id");
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'下架',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysGood/off',
                        data: {'id': id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
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
        }).html("确认下架？");
    });

    $(".j_delete-btn").click(function () {
        var id = $(this).attr("data-id")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            modal: true,
            title:'删除',
            buttons: {
                '确定': function () {
                    $.ajax({
                        url: '/sysGood/delete',
                        data: {'id': id},
                        async: false,
                        success: function (data) {
                            if (data.success) {
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
        }).html("确认删除？");
    });
});