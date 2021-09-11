function post(questionId,content,type){
    // var questionId = $("#question_id").val();
    // var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":type
        }),
        success: function (response){
            if(response.code == 200){
                // $("#comment_section").hide();
                window.location.reload();
            }else{
                if (response.code == 2003){
                    var isAccepted = confirm(response.message());
                    if(isAccepted){
                        // window.open("https://github.com/login/oauth/authorize/?client_id=d6c5e256b9fbe5713894&amp;redirect_uri=http://localhost:8887/callback&amp;scope=user&amp;state=1");
                        window.open("https://gitee.com/oauth/authorize?client_id=c5ef666200c1cef313b1c96138715ed9091fb69bf37996a5abbd6eec90b7332c&redirect_uri=http://localhost:8887/callback&response_type=code");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}

function commentpost(e){
    // var commentId = $("").val();
    // var content = $("#")
    var id = e.getAttribute("data-id");
    var content = $("#input-"+id).val();
    post(id,content,2);
}

function questionpost(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    post(questionId,content,1);
}

function collapseComments(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    comments.toggleClass("in");
    if (comments.hasClass("in")){
        $.getJSON( "/comment/"+id, function( data ) {
            var subCommentContainer = $("#comment-" + id);
            if (subCommentContainer.children().length == 1) {
                var items = [];
                $.each(data.data, function (index, comment) {
                    // var
                    var media = $("<div/>",{
                        "class":"media"
                    })
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left",
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src": comment.user.avatarUrl,
                    }))
                    var mediaBody = $("<div/>",{
                        "class":"media-body comments"
                    }).append(($("<h5/>",{
                        "class":"media-heading"
                    })).append($("<span/>",{
                        html:comment.user.name
                    })));
                    var c = $("<div/>", {
                        // "class":"comments",
                        html: comment.content,
                    });
                    var menu = $("<div/>",{
                        "class":"menu",
                    }).append($("<span/>",{
                        "class":"glyphicon glyphicon-thumbs-up icon"
                    }))
                    menu.append($("<span/>",{
                        "class":"pull-right",
                        html: moment(comment.gmtCreate).subtract(10, 'days').calendar()
                    }))
                    mediaBody.append(c);
                    mediaBody.append(menu);
                    media.append(mediaLeftElement);
                    media.append(mediaBody);
                    subCommentContainer.prepend(media);
                });
            }
            console.log(data)
        });
    }else{}
}
function showSelectTag(){
    $("#select-tag").show();
}
function setTag(e){
    var data = e.getAttribute("data-tag");
    var str = $("#tag").val();
    if(str.indexOf(data) == -1) {
        if (str) {
            $("#tag").val(str + ',' + data);
        } else {
            $("#tag").val(data)
        }
    }
}