/**
 * 글 삭제 요청을 보낸다.
 *
 * @return
 */
function deleteFunc() {
    alert("삭제하고자하는 글 idx : " + $("#idx").val());
    $.ajax({
        url: "/board/delete/" + $("#idx").val(),
        type: "DELETE",
        // dataType: 'json',
        // contentType: 'application/json',
        // data: JSON.stringify({
        //       "idx" : $("#idx").val()
        //     , "title" : $("#title").val()
        //     , "content" : $("#content").val()
        //     , "writer" : $("#writer").val()
        // }),
        success: function(result) {
            console.log(result)
            alert("글쓰기 삭제 완료 !\nidx : " + result.idx + "\nTitle :" + result.title + "\nWriter : " + result.writer);
            window.location.href = '/board/list';
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
        }
    });
}

/**
 * 업로드한 파일 데이터를 가져온다.
 *
 * @return
 */
function getFileData(boardIdx) {
    console.log("조회할 파일 데이터의 idx : " + boardIdx);

    $.getJSON("/board/getAttachList", { boardIdx : boardIdx }, function(obj) {
        console.log(obj);
        var str = "";

        $(obj).each(function(i, attach) {
            if (attach.fileType) { // 이미지 파일일 경우
                var encodeFileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
                var fileCallPath = attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName;

                str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid +
                    "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
                str += "<img src='/board/display?filename=" + encodeFileCallPath + "'></div>";
                str += "</li>";
            } else {
                var encodeFileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
                var fileCallPath = attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName;

                str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid +
                    "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
                str += "<img src='/images/attach.png'></div>";
                str += "</li>";
            }
        });

        $(".uploadResult ul").html(str);
    });
}

/**
 * <div class='uploadResult' ...>에 클릭 이벤트를 등록한다.
 *
 * @return
 */
function registClickEventToImage() {
    $(".uploadResult").on("click", "li", function(e) {
        console.log("Download File");

        var liObj = $(this);
        var encodePath = encodeURIComponent(liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename"));
        var path = liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename");
        alert("다운로드 하기 위해 넘기는 파라미터 값 : " + path);
        console.log("다운로드 하기 위해 넘기는 파라미터 값 : " + path);

        if (liObj.data("type")) { // 이미지 파일
            self.location = "/board/download?filename=" + encodePath;
        } else { // 일반 파일
            self.location = "/board/download?filename=" + encodePath;
        }
    });
}