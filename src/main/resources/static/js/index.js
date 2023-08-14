let part = window.location.href.split("/");
let BoardId = part[part.length - 1];
let images = {}

document.addEventListener("DOMContentLoaded", function () {
    const token = Cookies.get('Authorization');
    const host = "http://" + window.location.host;

    // 보드 전체 리스트 불러오는 함수
    $.ajax({
        type: "GET",
        url: "/okw/boards",
        headers: {'Authorization': token},
        contentType: 'application/json',
        success: function (response) {
            $('#boardList').empty();
            for (let i = 0; i < response.length; i++) {
                let boardTitle = response[i]['title'];
                let boardId = response[i]['boardId'];
                setHtml(boardTitle, boardId);
            }

            // 보드 리스트에 클릭 시 정보 로딩 함수 매핑
            const boardListItem = document.querySelectorAll(".board-list-item");
            boardListItem.forEach(function (boardItem) {
                let boardItemId = boardItem.value;
                boardItem.addEventListener("click", function () {
                    window.location.href = "/okw/view/boards/new-board/" + boardItemId;
                    // getBoardContent(boardItemId);
                })
            });
        },
        error: function (error) {
            console.error(error);
            let errorMessage = error.responseJSON.msg;
            alert(errorMessage);
            if (errorMessage === "토큰이 유효하지 않습니다.") {
                window.location.href = "/okw/view/users/login-signup";
            }
        }
    })

    // 보드 추가 모달창
    document.getElementById("openModalBtn").addEventListener("click", function () {
        var modal = document.getElementById("myModal");
        modal.style.display = "block";
    });

    document.querySelector(".close").addEventListener("click", function () {
        var modal = document.getElementById("myModal");
        modal.style.display = "none";
    });

    document.getElementById("confirmBtn").addEventListener("click", postBoard);

    document.querySelector('#showAlert').addEventListener('click', () => {
        showAlert(BoardId);
    });

    // User Menu 버튼 매핑
    const profileButton = document.getElementById("user-menu-profile");
    profileButton.addEventListener("click", function () {
        window.location.href = "/okw/view/users/profile";
    });
    const passwordButton = document.getElementById("user-menu-password");
    passwordButton.addEventListener("click", function () {
        window.location.href = "/okw/view/users/password";
    });
    const logoutButton = document.getElementById("user-menu-logout");
    logoutButton.addEventListener("click", logout);
});

function showAlert(boardId) {
    const token = Cookies.get('Authorization');

    $.ajax({
        type: 'GET',
        url: '/okw/alerts',
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            let alerts = response;
            let html = ``;

            $('.alert-list').empty();

            alerts.forEach((alert => {
                html += `
                    <li class="alert-list-item" id="alert-${alert['alertId']}">                        
                        <div class="alert-list-item-content">
                            <h4>Board: ${alert['board_title']}</h4>
                            <h5>Category: ${alert['category_title']}</h5>
                            <p>제목: ${alert['card_title']}</p>
                            <p>내용: ${alert['card_description']}</p>
                            <p>${alert['alert_at']}</p>
                        </div>
                        <div>
                            <button type="button" class="btn-close float-right" onclick="deleteAlert(${alert['alertId']},\'${token}\')"aria-label="Close"></button>
                        </div>
                    </li>
                `;
            }));

            $('.alert-list').append(html);
        })
        .fail(function (response) {
            alert("알람 조회 실패: " + response.responseJSON.msg);
        })
}

function deleteAlert(alertId, token) {
    $.ajax({
        type: 'DELETE',
        url: `/okw/alerts/${alertId}`,
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            alert("알림 삭제 성공")
            removeAlertElement(alertId);
        })
        .fail(function (response) {
            alert("알림 삭제 실패: " + response.responseJSON.msg)
        })
}

function removeAlertElement(alertId) {
    const alertList = document.querySelector('#alert-list');
    if (alertList) {
        const liElement = alertList.querySelector(`li[id="alert-${alertId}"]`);

        if (liElement) {
            liElement.remove();
        }
    }
}

function setHtml(boardTitle, boardId) {
    let html = `
              <div>
                    <button type="button" class="boardListTitle btn btn-light board-list-item" value="${boardId}">${boardTitle}</button>
              </div>
        `;
    $('#boardList').append(html);
}

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
            window.location.href = "/okw/view/boards";
        }, error: function (error) {
            alert("보드 추가 실패");
            console.log(error);
        }
    })
}