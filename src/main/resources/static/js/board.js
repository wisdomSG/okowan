document.addEventListener("DOMContentLoaded", function () {
    let boardId = 1; // BoardList 에서 보내줘야 하는 값, 임시로 1로 테스트
    const token = Cookies.get("Authorization");

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
})

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