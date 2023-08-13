let BoardId = 0 //어떤 board가 눌렸는지 해당 boardId 업데이트
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
            //$('#post-cards').empty();
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
                    getBoardContent(boardItemId);
                })
            });

        },
        error: function (error, status, xhr) {
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

    document.querySelector(".showMember-menu-btn").addEventListener("click", () => {
        showBoardMember(BoardId, token);
    });

    document.querySelector('.searchTerm').addEventListener('keyup', () => {
        searchingMember(BoardId)
    });

    document.querySelector('.searchMemberButton').addEventListener('click',() => {
        searchMember(BoardId)
    });

    document.querySelector('#board-content').parentElement.addEventListener('click', (event) => {
        // 클릭된 요소 확인
        const clickedElement = event.target;

        if (clickedElement.classList.contains('list-title')) {
            event.stopPropagation(); // 이벤트 버블링 중단

            const listElement = clickedElement.closest('.list'); // 가장 가까운 .list 요소 찾기
            const categoryId = listElement.getAttribute('id');

            // 현재 텍스트 내용 가져오기
            const currentText = clickedElement.textContent;
            let newText = '';

            // input 요소 생성 및 값 설정
            const inputElement = document.createElement('input');
            inputElement.value = currentText;
            inputElement.classList.add('form-control');

            // h3 요소를 input 요소로 교체
            clickedElement.replaceWith(inputElement);

            // input 요소에 포커스 설정
            inputElement.focus();

            inputElement.addEventListener('keyup', (event) => {
                if (event.key === 'Enter') {
                    // 변경된 값 가져오기
                    const newValue = inputElement.value;

                    // h3 요소 생성 및 값 설정
                    const newH3Element = document.createElement('h3');
                    newH3Element.textContent = newValue;
                    newH3Element.classList.add('list-title');
                    newH3Element.id = 'list-title';

                    // input 요소를 h3 요소로 교체
                    inputElement.replaceWith(newH3Element);
                    newText = newValue;

                    updateCategory(categoryId,BoardId,newText,token);
                }
            });
        }
    });

    document.querySelector('#showAlert').addEventListener('click',() => {
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

function showBoardMember(boardId, token) {
    const userBoardRole = ['OWNER', 'EDITOR','VIEWER'];
    document.querySelector("#showMemberoffcanvasScrollingLabel").textContent =
        document.querySelector('.board-title').textContent + '에 초대된 맴버';

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
                                <select id="form-select-${user['userId']}" class="form-select" onchange="updateMember(${user['userId']},\'${user['username']}\',${boardId}, \'${token}\')" aria-label="Default select example" style="border:none">
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
            alert("맴버 조회 실패")
        })
}

function searchingMember(boardId) {
    const token = Cookies.get('Authorization');
    let search = $('.searchTerm').val().trim();

    if(search === "") {
        return;
    }

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
                            <button class="invite-member-setting-btn btn-primary" onclick="inviteMember(\'${user['username']}\',${boardId},\'${token}\')" type="button">
                                    <i class="fas fa-plus" aria-hidden="true"></i>
                            </button>
                        </li>   
                    `;
            })
            $('#invite-member-list').append(html);
        })
        .fail(function (response, status, xhr) {
        })
}

function searchMember(boardId) {
    const token = Cookies.get('Authorization');
    let search = $('.searchTerm').val().trim();

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
            alert("맴버 검색 실패");
        })
}

function inviteMember(username, boardId, token) {
    const host = "http://" + window.location.host;
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
        })
        .fail(function (response) {
            alert("맴버 초대 실패: " + response.responseJSON.msg);
        });
}

function updateMember(userId,boardId, username, token) {
    var onSelect = document.getElementById('form-select-' + userId);
    var selectValue = onSelect.options[onSelect.selectedIndex].text.substring(1,);

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
            //alert("역할 업데이트 실패: " + response.responseJSON.msg);
            alert("역할 업데이트 실패: ");
        })
}

function addCategory(boardId, token) {
    const host = "http://" + window.location.host;
    const BoardId = boardId;
    const title = document.getElementById('add-category-input').value.trim();

    let data = {
        "title" : title
    }

    $.ajax({
        type:'POST',
        url:`/okw/categories/${BoardId}`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: {'Authorization': token}
    })
        .done(function (response, status, xhr) {
            alert("카테고리 등록 성공");
            getBoardContent(boardId);
        })
        .fail(function (response, status, xhr) {
            alert("카테고리 등록 실패: " + response.responseJSON.msg);
        })
}

function updateCategory(categoryId, boardId, newText, token) {
    let data = {
        'title' : newText
    }

    $.ajax({
        type: 'PUT',
        url: `/okw/categories/${categoryId}`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            //getBoardContent(boardId);
        })
        .fail(function (response) {
            alert("카테고리 수정 실패: " + response.responseJSON.msg);
        })
}

function deleteCategory(categoryId, boardId, token) {
    $.ajax({
        type:'DELETE',
        url:`/okw/categories/${categoryId}`,
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            alert("카테고리 삭제 성공")
            getBoardContent(boardId);
        })
        .fail(function (response) {
            alert("카테고리 삭제 실패: " + response.responseJSON.msg);
        })

}

function moveCategory(categoryId, boardId, move, token) {
    $.ajax({
        type:'POST',
        url:`/okw/categories/${categoryId}/move` + `?boardId=${boardId}&move=${move}`,
        contentType: 'application/json',
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            getBoardContent(boardId);
        })
        .fail(function (response) {
            alert("카테고리 순서 이동 실패: " + response.responseJSON.msg);
        })
}

function showAlert(boardId) {
    const token = Cookies.get('Authorization');

    $.ajax({
        type:'GET',
        url:'/okw/alerts',
        headers: {"Authorization": token}
    })
        .done(function (response, status, xhr) {
            let alerts = response;
            let html = ``;

            $('.alert-list').empty();

            alerts.forEach((alert => {
                html += `
                    <li class="alert-list-item" id="${alert['alertId']}">                        
                        <div class="alert-list-item-content">
                            <h4>Board: ${alert['board_title']}</h4>
                            <h5>Category: ${alert['category_title']}</h5>
                            <p>제목: ${alert['card_title']}</p>
                            <p>내용: ${alert['card_description']}</p>
                            <p>${alert['alert_at']}</p>
                        </div>
                        <div>
                            <button type="button" class="btn-close float-right" onclick="deleteAlert(${alert['alertId']},${boardId},\'${token}\')"aria-label="Close"></button>
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

function deleteAlert(alertId, boardId, token) {
    $.ajax({
        type:'DELETE',
        url:`/okw/alerts/${alertId}`,
        headers: {"Authorization" : token}
    })
        .done(function (response, status, xhr) {
            alert("알림 삭제 성공")
            getBoardContent(boardId);
        })
        .fail(function (response) {
            alert("알림 삭제 실패: " + response.responseJSON.msg)
        })
}

// cardDetails 가기
function registerCardItemClickEvent() {
    $(".card__item").click(function () {
            const token = Cookies.get('Authorization');
            const cardId = this.id // 클릭된 요소의 id 값 가져오기
            console.log(cardId);
            // 카드 정보를 가져오기 위한 AJAX 요청
            $.ajax({
                url: `/okw/cards/${cardId}`,  // 카드 정보를 가져올 API 엔드포인트
                method: 'GET',
                headers: {"Authorization": token},
                dataType: 'json',
                success: function (cardDetails) {
                    window.location.href = `/okw/view/cards/${cardId}`;
                },
                error: function (xhr, status, error) {
                    console.error('카드 정보 가져오기 에러:', error);
                }
            });
        }
    )
}

// 보드의 내용(카테고리, 카드) 불러오는 함수
function getBoardContent(boardId) {
    let token = Cookies.get('Authorization');

    $.ajax({
        type: "GET",
        url: "/okw/boards/contents/" + boardId,
        headers: {"Authorization": token},
    })
        .done(function (response) {
            document.getElementsByClassName("lists-container").innerHTML = "";
            loadBoardContent(response);
            registerCardItemClickEvent();
            AddCard();
        })
        .fail(function (response, status, xhr) {
            console.log(response.responseJSON);
            let errorMessage = response.responseJSON.msg;
            alert("보드 내용 불러오기 실패: " + errorMessage);
            if (errorMessage === "만료된 토큰입니다.") {
                window.location.href = "/okw/view/users/login-signup";
            }
        })
}

function setHtml(boardTitle, boardId) {
    let html = `
              <div>
                    <button type="button" class="btn btn-light board-list-item" value="${boardId}">${boardTitle}</button>
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

// 보드의 카테고리, 카드를 불러오는 함수
function loadBoardContent(boardJson) {
    const token = Cookies.get('Authorization');
    let boardId = BoardId = boardJson.boardId;
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
                <div class="list" id=${categoryId} draggable="true">
                    <div class="list-header">
                        <h3 class="list-title" id="list-title">${categoryTitle}</h3>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle dropdown-toggle-no-arrow" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-ellipsis-h menu-btn-icon" aria-hidden="true" style="font-size: 20px; color: #FFFFFF"></i>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" onclick="moveCategory(${categoryId},${boardId},'up',\'${token}\')">Move to Up</a></li>
                                <li><a class="dropdown-item" onclick="moveCategory(${categoryId},${boardId},'down',\'${token}\')">Move to Down</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" onclick="deleteCategory(${categoryId},${boardId},\'${token}\')">Delete</a></li>
                            </ul>
                        </div>
                    </div>
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
                    <div class="add-card-container">
                        <button class="add-card-btn btn" id="category${categoryId}">Add a card</button>
                        <!-- 폼 추가 -->
                        <form class="cardForm" style="display: none;">
                            <div>
                                <input class="cardTitleInput" type="text" name="cardTitleInput" placeholder="Enter card title" required>
                            </div>
                            <button type="submit" class="createCardButton">Create Card</button>
                            <button type="button" class="deleteCardButton">X</button>
                        </form>
                    </div>
            </div>
            `;
        boardContentHtml += categoryContentHeader + categoryContentFooter;
    })
    let boardContentFooter = `
        <div class="container">
            <button class="add-list-btn btn" type="button" data-bs-toggle="collapse" data-bs-target="#addListCollapse" aria-expanded="false" aria-controls="addListCollapse">
                Add a Category
            </button>
            <div class="collapse" id="addListCollapse" style="background-color: #4d4c99">
                <input id="add-category-input" class="form-control" type="text" placeholder="Enter Category Title..."/>
                <button class="add-category-btn btn" onclick="addCategory(${boardId}, \'${token}\')">Add Category</button>
            </div>
        </div>

    `;
    boardContentHtml += boardContentFooter;

    let boardContent = document.getElementById("board-content");
    boardContent.innerHTML = boardContentHtml;
}




function AddCard() {
    // Add event listeners for each "Add a card" button
    const addCardButtons = document.querySelectorAll('.add-card-btn');

    addCardButtons.forEach(addCardButton => {
        const cardForm = addCardButton.closest('.add-card-container').querySelector('.cardForm');
        const cardDelete = cardForm.querySelector('#deleteCardButton');

        addCardButton.addEventListener('click', () => {
            addCardButton.style.display = 'none';
            cardForm.style.display = 'block';
        });

        cardForm.addEventListener('submit', (event) => {
            event.preventDefault();
            const cardTitleInput = cardForm.querySelector('.cardTitleInput');
            const cardTitle = cardTitleInput.value;
            console.log('New card title:', cardTitle);
            addCardButton.style.display = 'block';
            cardForm.style.display = 'none';
            cardForm.reset();
        });

        cardDelete.addEventListener('click', (event) => {
            addCardButton.style.display = 'block';
            cardForm.style.display = 'none';
            cardForm.reset();
        });
    });
}
