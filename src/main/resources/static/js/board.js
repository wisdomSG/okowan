// 보드 리스트
document.addEventListener("DOMContentLoaded", function () {
    const token = Cookies.get('Authorization');

    $.ajax({
        type: "GET",
        url: "/okw/boards",
        headers: {'Authorization': token},
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            // $('#post-cards').empty();
            for (let i = 0; i < response.length; i++) {
                let boardTitle = response[i]['title'];
                console.log(boardTitle);
                let boardId = response[i]['boardId'];
                setHtml(boardTitle, boardId);
            }
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    })
})

function setHtml(boardTitle, boardId) {
    let html=`
              <div>
                    <button type="button" class="btn btn-light" value="boardTitle" id="boardTitle">${boardTitle}</button>
                    <span hidden="hidden">${boardId}</span>
              </div>
        `;
    $('#boardList').append(html);
}


// 보드 추가 모달창
document.getElementById("openModalBtn").addEventListener("click", function () {
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
});

document.querySelector(".close").addEventListener("click", function () {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
});

// document.getElementById("cancelBtn").addEventListener("click", function() {
//     var modal = document.getElementById("myModal");
//     modal.style.display = "none";
// });

document.getElementById("confirmBtn").addEventListener("click", postBoard);

function postBoard() {
    const token = Cookies.get('Authorization'); // 쿠키에서 토큰 가져오기
    const title = document.querySelector("#title").value;
    const description = document.querySelector("#description").value;

    // 입력하는 데이터
    const data = {
        title: title,
        description: description
    }

    $.ajax({
        type: 'POST',
        url: '/okw/boards/post',
        headers: {'Authorization': token}, // 헤더에 토큰 주입
        contentType: "application/json", // 읽는 타입이 JSON이라는 뜻
        data: JSON.stringify(data), // JAVA -> JSON

        success: function (response) {
            alert("보드 추가 성공");
            // window.location.href = "/okw/view/boards/board";
        }, error: function (req, status, error) {
            alert("보드 추가 실패");
            console.log(req, status, error);
            // window.location.href = "/okw/view/boards/board";
        }
    })
}
// 보드 추가 모달창 끝