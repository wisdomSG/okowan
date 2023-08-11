document.addEventListener("DOMContentLoaded", function () {
    const token = Cookies.get('Authorization');
    const host = "http://" + window.location.host;
    const showMemberButton = document.querySelector(".showMember-menu-btn");
    const offcanvasLabel = document.querySelector("#showMemberoffcanvasScrollingLabel");
    const boardId = 1 //어떤 board가 눌렸는지 해당 boardId 업데이트
    const boardTitle = document.querySelector('.board-title').textContent;  //Board Title

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

    /* -------- */

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
                            <div class="member-list-item-content w-100">
                                <h4>${user['username']}</h4>
                                <h4>${user['nickname']}</h4>
                                <p>@${user['role']}</p>
                            </div>
                            <div>
                                <button class="upd-member-setting-btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample-${user['UserId']}" aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-plus" aria-hidden="true"></i>
                                </button>
                            </div>
                    </li>
                    <div class="collapse" id="collapseExample-${user['UserId']}">
                            <div class="card card-body">
                                Some placeholder content for the collapse component. This panel is hidden by default but revealed when the user activates the relevant trigger.
                            </div>
                    </div>
                    `
                })
                $('#member-list').append(html);
            })
            .fail(function (response) {
                alert(response.responseJSON.msg);
            })
    }

    document.querySelector('.searchMemberButton').addEventListener('click',() => {
        let search = $('.searchTerm').val();

        $.ajax({
            type:'GET',
            url:'/okw/users/search',
            data: {
                keyword : search
            },
            headers: {'Authorization': token}
        })
            .done(function (response, status, xhr) {
                let users = response;
                if(users.length == 0) {
                    alert("검색결과가 없습니다.");
                    return;
                }

                $('#invite-member-list').empty();
                let html = ``;
                users.forEach((user) => {
                    html += `
                        <li class="member-list-item">
                            <div>
                                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/488320/profile/profile-80.jpg" class="member-list-item-image">
                            </div>
                            <div class="member-list-item-content w-100">
                                <h4>${user['username']}</h4>
                                <p>${user['nickname']}</p>
                                <p>${user['introduce']}</p>
                            </div>
                            <button class="invite-member-setting-btn btn-primary" onclick="inviteMember(\'${user['username']}\',${boardId})" type="button">
                                    <i class="fas fa-plus" aria-hidden="true"></i>
                            </button>
                        </li>   
                    `;
                })
                $('#invite-member-list').append(html);
            })
            .fail(function (response, status, xhr) {
                console.log(response);
            })

    })
})

function inviteMember(username, boardId) {
    console.log(username, boardId);
    const host = "http://" + window.location.host;
    const token = Cookies.get('Authorization');
    let User = username;
    let BoardId = boardId;

    let data = {
        BoardId : BoardId,
        username : User,
        role : "VIEWER"
    }
    $.ajax({
        type:'POST',
        url:`/okw/boards/${boardId}/invite`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            alert("맴버 초대 성공");
            window.location.href = host;
        })
        .fail(function (response) {
            console.log(response);
        });
}

function setHtml(boardTitle, boardId) {
    let html=`
              <div>
                    <button type="button" class="btn btn-light" value="boardTitle" id="boardTitle">${boardTitle}</button>
                    <span hidden="hidden">${boardId}</span>
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
            // window.location.href = "/okw/view/boards/board";
        }, error: function (req, status, error) {
            alert("보드 추가 실패");
            console.log(req, status, error);
            // window.location.href = "/okw/view/boards/board";
        }
    })
}