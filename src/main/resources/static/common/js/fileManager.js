var commonLib = commonLib || {}; //뭐시여어어어어
/**
* 1. 파일 업로드
*
*/
commonLib.fileManager = {
    /**
    * 파일 업로드 처리
    *
    */
    upload(files) {
        try {
            if (!files || files.length == 0) {
                throw new Error("업로드할 파일을 선택하세요.");
            }

            //gid
            const gidEl = document.querySelector("[name='gid']");
            if (!gidEl || !gidEl.value.trim()) {
                throw new Error("gid가 누락되었습니다.");
            }
            //MDN사이트에서 ...폼데이터를..
            const gid = gidEl.value.trim();

            const formData = new FormData(); // 기본 Content-Type : multipart/form-data

            formData.append("gid",gid);

            for (const file of files) {
                formData.append("file", file);
            }

            const { ajaxLoad } = commonLib;
            ajaxLoad("POST", "/api/file", formData, "json")
                .then(res => { //요청 성공시
                    if(res && res.success) {
                        if (typeof parent.callbackFileupload == 'function') {
                        parent.callbackFileupload(res.data);
                        }

                    } else {
                        if(res) alert(res.message);
                    }

                })
                .catch(err => console.error(err));

        } catch (err) {
            alert(err.message);
            console.error(err);
        }
    }
};

//이벤트처리 (클릭하면 파일탐색기가 나오도록)
window.addEventListener("DOMContentLoaded", function() {
//window.onload 절대 쓰지마!!!! ??..
    const uploadFiles = document.getElementsByClassName("upload_files");
    const fileEl = document.createElement("input");
    fileEl.type="file";
    fileEl.multiple = true; //여러개 파일을 선택 가능하게

//파일 업로드 버튼 클릭 처리 -> 파일 탐색기 열기
    for(const el of uploadFiles) {
        el.addEventListener("click", function() {
            fileEl.click();
        });
    }

    //파일 선택시 이벤트 처리
    fileEl.addEventListener("change", function(e) {
         //console.dir(e.target.files);
         commonLib.fileManager.upload(e.target.files);
    });
});