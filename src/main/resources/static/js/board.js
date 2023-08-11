// 보드 리스트
document.addEventListener("DOMContentLoaded", function () {
    const token = Cookies.get('Authorization');

    $.ajax({
        type: "GET",
        url: "/okw/boards",
        headers: {'Authorization': token},
        contentType: 'application/json',
        success: function (response) {
            // $('#post-cards').empty();
            for (let i = 0; i < response.length; i++) {
                let boardTitle = response[i]['title'];
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
        url: '/okw/boards',
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

document.addEventListener("DOMContentLoaded", function () {
    const host = "http://" + window.location.host;

    const showMemberButton = document.querySelector(".showMember-menu-btn");
    const offcanvasLabel = document.querySelector("#showMemberoffcanvasScrollingLabel");

    const boardId = 1 //어떤 board가 눌렸는지 해당 boardId 업데이트

    const token = Cookies.get('Authorization');

    const boardTitle = document.querySelector('#board-title').textContent;  //Board Title

    showMemberButton.addEventListener("click", function () {
        offcanvasLabel.textContent = boardTitle + '에 초대된 맴버';

        showBoardMember(boardId);
    });

    function showBoardMember(boardId) {
        $.ajax({
            type:'GET',
            url:`/okw/boards/member/${boardId}`,
            headers: {'Authorization': token}
        })
            .done(function (response, status, xhr) {
                $('#member-list').empty();
                const users = response;
                let html = ``;
                users.forEach((user) => {
                    html += `
                    <li class="member-list-item">
                            <div>
                                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/488320/profile/profile-80.jpg" class="member-list-item-image">
                            </div>
                            <div class="member-list-item-content">
                                <h4>${user['nickname']}</h4>
                                <p>@${user['role']}</p>
                            </div>
                        </li>
                    `
                })
                $('#member-list').append(html);
            })
            .fail(function (response) {
                alert(response.responseJSON.msg);
            })
    }

    document.querySelector('#button-addon2').addEventListener('click',() => {
        let search = $('#searchMember').val();

        console.log(search);

        $.ajax({
            type:'GET',
            url:'/okw/users/'
        })

    })

    function searchMember() {

    }
});