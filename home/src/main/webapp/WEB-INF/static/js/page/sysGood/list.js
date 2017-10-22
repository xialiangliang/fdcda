$(function(){
    var dialog = $("#newUpdateDialogFrame");
    
    function tip(msg, reload) {
        var action_tip = "<div class='pop-con-tip'>" + msg + "</div>";
        var tip_frame = $("#tipDialogFrame");
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
    $(".j_update-btn").click(function () {
        $.get('/sysGood/find', {'id': $(this).attr("data-id")}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });

    $(".j_new-btn").click(function () {
        $.get('/sysGood/new', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });
    
    $(".j_category-btn").click(function () {
        $.get('/sysGood/category', {}, function (data, textStatus, object) {
            dialog.html(object.responseText);
            dialog.dialog({
                resizable: true,
                width: 600,
                height:600,
                modal: true,
                title:'分类',
                buttons: {
                }
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