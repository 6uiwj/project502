var commonLib = commonLib || {};

/**
* ajax 요청, 응답 편의 함수
*
* @param method : 요청 방식 - GET, POST, PUT, PATCH, DELETE ...
* @param url : 요청 URL
* @param params : 요청 데이터(POST, PUT, PATCH ... )
* @param responseType : json : javascript 객체로 변환
*/
commonLib.ajaxLoad = function(method, url, params, responseType) {
    method = method || "GET" ;
    params = params || null; //요청데이터가 있으면 그대로 쓰고 없으면 null값으로 대체

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise((resolve, reject) => { //resolve:성공시 데이터 넘겨준다. //reject: 실패시 데이터 넘겨준다.
    const xhr = new XMLHttpRequest();


    xhr.open(method, url);
    xhr.setRequestHeader(tokenHeader, token);


    xhr.send(params); //요청 바디에 실릴 데이터 키=값&키=값& ... FormData 객체 (POST, PATCH, PUT)

    xhr.onreadystatechange = function() {
            if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE ) {
                 //응답형식이 json이면 자바스크립트 객체로 아닐 때는 문자열형태로 내보낸다.
                const resData = (responseType && responseType.toLowerCase() == 'json') ? JSON.parse(xhr.responseText) : xhr.responseText;

                resolve(resData); //성공시 응답 데이터
            }
        };

       xhr.onabort = function(err) {
            reject(err); //중단시
       };

       xhr.onerror = function(err)  {
            reject(err); ///요청 또는 응답시 오류 발생
       };
    });
};

/**
* 위지윅 에디터 로드
*
*/
commonLib.loadEditor = function(id, height) {
    if (!id) {
        return;
    }

    height = height || 450;

    // ClassicEditor
    return ClassicEditor.create(document.getElementById(id), {
        height
    });
}