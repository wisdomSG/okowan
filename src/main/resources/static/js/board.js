document.addEventListener("DOMContentLoaded", function () {
    const token = Cookies.get('Authorization');
    const host = "http://" + window.location.host;
    const showMemberButton = document.querySelector(".showMember-menu-btn");
    const offcanvasLabel = document.querySelector("#showMemberoffcanvasScrollingLabel");
    const boardId = 1 //어떤 board가 눌렸는지 해당 boardId 업데이트
    const boardTitle = document.querySelector('.board-title').textContent;  //Board Title
    const userBoardRole = ['OWNER', 'EDITER','VIEWER'];

    // 보드의 내용(카테고리, 카드) 불러오는 함수
    $.ajax({
        type: "GET",
        url: "/okw/boards/contents/" + boardId,
        headers: {"Authorization": token},
    })
        .done(function (response) {
            document.getElementsByClassName("lists-container").innerHTML = "";
            loadBoardContent(response);
        })
        .fail(function (response, status, xhr) {
            console.log(response.responseJSON);
            alert("보드 내용 불러오기 실패: " + response.responseJSON.msg);
            window.location.href = "/okw/view/users/login-signup";
        })

    // 보드 전체 리스트 불러오는 함수
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

    showMemberButton.addEventListener("click", function () {
        offcanvasLabel.textContent = boardTitle + '에 초대된 맴버';

        showBoardMember(boardId);
    });

    function showBoardMember(boardId) {
        $.ajax({
            type: 'GET',
            url: `/okw/boards/member/${boardId}`,
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
                                <select id="form-select-${user['userId']}" class="form-select" onchange="updateMember(${user['userId']},\'${user['username']}\',${boardId})" aria-label="Default select example" style="border:none">
                                  <option selected style="background-color: #838c91">@${user['role']}</option>
                                  <option value="1">@${userBoardRole[0]}</option>
                                  <option value="2">@${userBoardRole[1]}</option>
                                  <option value="3">@${userBoardRole[2]}</option>
                                </select>
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

    document.querySelector('.add-list-btn').addEventListener('click',addCategory(boardId));

    function addCategory(boardId) {
        $.ajax({
            type:'POST',
            url:`/okw/categories/${boardId}`,

        })

        let html = document.querySelector('.lists-container').innerHTML;

        html = `
            <div class="list">
                <h3 class="list-title">Useful Websites for Learning</h3>
                <button class="add-card-btn btn">Add a card</button>
        </div>
        ` + html;

    }
})

function inviteMember(username, boardId) {
    const host = "http://" + window.location.host;
    const token = Cookies.get('Authorization');
    let User = username;
    let BoardId = boardId;

    let data = {
        BoardId: BoardId,
        username: User,
        role: "VIEWER"
    }
    $.ajax({
        type: 'POST',
        url: `/okw/boards/${boardId}/invite`,
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

function updateMember(userId, username, boardId) {
    const token = Cookies.get('Authorization');
    var onSelect = document.getElementById('form-select-' + userId);

    console.log(onSelect.options[onSelect.selectedIndex].value)
    var selectValue = onSelect.options[onSelect.selectedIndex].text.substring(1,);
    console.log(selectValue);

    let data = {
        "BoardId" : boardId,
        "username" : username,
        "role" : selectValue
    }

    $.ajax({
        type:'PUT',
        url:`/okw/boards/${boardId}/invite/update`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: {'Authorization': token}
    })
        .done(function (response, status, xhr) {
            alert("역할 업데이트 성공");
        })
        .fail(function (response) {
            console.log(response);
        })
}

function setHtml(boardTitle, boardId) {
    let html = `
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

function loadBoardContent(boardJson) {
    let boardId = boardJson.boardId;
    let boardTitle = boardJson.title;
    let boardTitleButton = document.getElementById("board-title-button");
    boardTitleButton.setAttribute("board-id", boardId);

    document.getElementById("board-title").textContent = boardTitle;

    let boardContentHtml = ``;

    // 보드에 속한 카테고리 순회
    let categoryList = boardJson.categoryDetailResponseDtoList;

    categoryList.forEach((category) => {
        let categoryId = category.categoryId;
        let categoryTitle = category.title;

        let categoryContentHeader = `
            <div class="list" id=${categoryId}>
                <h3 class="list-title">${categoryTitle}</h3>
                <ul class="list-items">
            `;

        // 카테고리에 속한 카드 순회
        let cardList = category.cardSimpleResponseDtoList;

        cardList.forEach((card) => {
            let cardId = card.cardId;
            let cardTitle = card.title;
            let cardColor = card.color;
            let cardWorker = card.workerName;
            let cardDeadline = card.deadline;

            let cardContent = `
                    <li class="card__item" id="${cardId}">
                        <span class="card__tag card__tag--browser" style="background-color: ${cardColor}">${cardWorker}</span>
                        <h6 class="card__title">${cardTitle}</h6>
                        <span class="card__tag card__tag--date">${cardDeadline}</span>
                    </li>
            `;
            categoryContentHeader += cardContent;
        })

        let categoryContentFooter = `                        
                </ul>
                <button class="add-card-btn btn">Add a card</button>
            </div>
            `;
        boardContentHtml += categoryContentHeader + categoryContentFooter;
    })
    let boardContentFooter = `
        <button class="add-list-btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#AddListCallapse" aria-expanded="false" aria-controls="collapseExample">
            Add a column
        </button>
        <div class="collapse" id="AddListCallapse">
          <div class="card card-body">
            <input type="text" placeholder="Enter Category Title..."/>
            <button class="btn btn-primary">Add Category</button>
          </div>
        </div>
    `;
    boardContentHtml += boardContentFooter;

    let boardContent = document.getElementById("board-content");
    boardContent.innerHTML = boardContentHtml;
}