$(function(){
    $(".j_update-btn").click(function () {
        $.get('/sysAreaInfo/find', {'id':$(this).attr("data-id")}, function(data, textStatus, object){
            $( object.responseText ).dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    })
    
    $(".j_new-btn").click(function () {
        $.get('/sysAreaInfo/new', {}, function(data, textStatus, object){
            $( object.responseText ).dialog({
                resizable: true,
                width: 600,
                modal: true,
                buttons: {
                }
            });
        });
    });
});