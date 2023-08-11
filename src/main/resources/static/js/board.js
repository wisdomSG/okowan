document.addEventListener("DOMContentLoaded", function () {
    const host = "http://" + window.location.host;

    const boardId = 1 //어떤 board가 눌렸는지 해당 boardId 업데이트

    const token = Cookies.get('Authorization');

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


    const showMemberButton = document.querySelector(".showMember-menu-btn");
    const offcanvasLabel = document.querySelector("#showMemberoffcanvasScrollingLabel");

    const boardTitle = document.querySelector('#board-title').textContent;  //Board Title

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

    document.querySelector('#button-addon2').addEventListener('click', () => {
        let search = $('#searchMember').val();

        console.log(search);

        $.ajax({
            type: 'GET',
            url: '/okw/users/'
        })

    })

    function searchMember() {

    }
});

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
        <button class="add-list-btn btn">Add a column</button>
    `;
    boardContentHtml += boardContentFooter;

    let boardContent = document.getElementById("board-content");
    boardContent.innerHTML = boardContentHtml;
}