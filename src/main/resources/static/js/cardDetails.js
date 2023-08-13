document.addEventListener("DOMContentLoaded", function () {

// 수정 버튼 클릭시 필드 값 변경 & 버튼 전환
    const updateButtons = document.querySelectorAll('.update-btn');
    updateButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const p = this.closest('p');
            const input = p.querySelector('input');
            const doneButton = p.querySelector('.done-btn');

            this.style.display = 'none';
            doneButton.style.display = 'block';

            input.disabled = false;
        });
    });

    const descriptionButtons = document.querySelectorAll('.des-btn');
    descriptionButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const p = this.closest('p');
            const textarea = p.querySelector('textarea');
            const doneButton = p.querySelector('.done-btn');

            this.style.display = 'none';
            doneButton.style.display = 'block';

            textarea.disabled = false;
        });
    });

    const categoryButtons = document.querySelectorAll('.update-category-btn');
    categoryButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const p = this.closest('p');
            const select = p.querySelector('select');
            const doneButton = p.querySelector('.done-btn');

            this.style.display = 'none';
            doneButton.style.display = 'block';

            select.disabled = false;
        });
    });

    const workerButtons = document.querySelectorAll('.update-worker-btn');
    workerButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const p = this.closest('p');
            const select = p.querySelector('select');
            const doneButton = p.querySelector('.done-worker-btn');

            this.style.display = 'none';
            doneButton.style.display = 'block';

            select.disabled = false;
        });
    });

    const colorButtons = document.querySelectorAll('.update-color-btn');
    colorButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const p = this.closest('p');
            const select = p.querySelector('select');
            const doneButton = p.querySelector('.done-btn');

            this.style.display = 'none';
            doneButton.style.display = 'block';

            select.disabled = false;
        });
    });

    function commentBtnUpdate() {
        const commentButtons = document.querySelectorAll('.comment-update-btn');
        commentButtons.forEach(function (button) {
            button.addEventListener('click', function () {
                const p = this.closest('p');
                const input = p.querySelector('input');
                const doneButton = p.querySelector('.done-comment-btn');

                this.style.display = 'none';
                doneButton.style.display = 'block';

                input.disabled = false;
            });
        });
    }


    // const deadlineButtons = document.querySelectorAll('.update-deadline-btn');
    // deadlineButtons.forEach(function (button) {
    //     button.addEventListener('click', function () {
    //         const p = this.closest('p');
    //         const select = p.querySelector('select');
    //         const doneButton = p.querySelector('.done-deadline-btn');
    //
    //         this.style.display = 'none';
    //         doneButton.style.display = 'block';
    //
    //         select.disabled = false;
    //     });
    // });

    const monthSelect = document.getElementById('month');
    const daySelect = document.getElementById('day');
    const yearSelect = document.getElementById('year');
    const currentYear = new Date().getFullYear();
    const endYear = 2030;
    const hourSelect = document.getElementById('hour');
    const minuteSelect = document.getElementById('minute');


    // 월 옵션 생성
    for (let month = 1; month <= 12; month++) {
        const option = document.createElement('option');
        const formattedMonth = month.toString().padStart(2, '0'); // 2자리 숫자로 변환
        option.value = formattedMonth;
        option.textContent = formattedMonth;
        monthSelect.appendChild(option);
    }

    // 일 옵션 생성
    for (let day = 1; day <= 31; day++) {
        const option = document.createElement('option');
        const formattedMonth = day.toString().padStart(2, '0'); // 2자리 숫자로 변환
        option.value = formattedMonth;
        option.textContent = formattedMonth;
        daySelect.appendChild(option);
    }

    // 년 옵션 생성
    for (let year = currentYear; year <= endYear; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }


    // 시간 옵션 생성
    for (let hour = 0; hour <= 23; hour++) {
        const option = document.createElement('option');
        option.value = hour.toString().padStart(2, '0');
        option.textContent = hour.toString().padStart(2, '0'); // 시간을 2자리 숫자로 표시
        hourSelect.appendChild(option);
    }


    const token = Cookies.get('Authorization');

    let currentURL = window.location.href;

    // URL을 "/"로 분할하여 배열로 저장합니다.
    let urlParts = currentURL.split("/");

    // 배열에서 마지막 요소를 가져옵니다.
    let lastPart = urlParts[urlParts.length - 1];

    $.ajax({
        type: "GET",
        url: "/okw/cards/" + lastPart,
        headers: {"Authorization": token}
    })
        .done(function (response) {
            alert("카드 정보 불러오기 성공");
            console.log(response);
            fetchWorkerList(response);
            setCardData(response);
            categoryId = response.categoryId;
            commentBtnUpdate();

        })
        .fail(function (response, status, xhr) {
            alert("카드 정보 불러오기 실패");
            console.log(response);
        })


    // 작업자 조회
    // 작업자 선택 `<select>` 요소
    const workerChoiceSelect = document.getElementById('workerChoice');

    // 작업자 목록을 가져오는 함수
    function fetchWorkerList(response) {
        let boardId = response.boardId;

        $.ajax({
            type: 'GET',
            url: `/okw/boards/worker/${boardId}`,
            headers: {
                'Authorization': token
            }
        })
            .done(function (data) {
                workerChoiceSelect.innerHTML = '';

                // 서버에서 가져온 작업자 목록을 `<option>` 요소로 변환하여 추가
                data.forEach(worker => {
                    console.log(worker);
                    const option = document.createElement('option');
                    option.value = worker.userId;
                    option.textContent = worker.nickname;

                    workerChoiceSelect.appendChild(option);
                });
            })

            .fail(function (error) {
                console.error('Error fetching worker list:', error);
            });
    }

    $(document).on('click', '.file-delete-btn', function () {
        const fileContent = $(this).closest('p');
        const aTag = fileContent.find('.file-body');
        const fileId = aTag.data('file-id'); // 이 부분을 수정하세요

        $.ajax({
            type: 'DELETE',
            url: "/okw/cards/"+lastPart +"/files/" + fileId,
            headers: {
                'Authorization': token
            }
        })
            .done(function (data) {
                alert('파일 삭제 완료');
                window.location.reload();
                // 업로드 이후에 필요한 UI 업데이트나 새로고침 처리 등을 진행할 수 있습니다.
            })
            .fail(function (error) {
                console.error('파일 삭제 오류:', error);
            });
    });


    // 댓글 수정 완료 버튼 클릭 이벤트
    $(document).on('click', '.done-comment-btn', function () {
        const commentContainer = $(this).closest('p');
        const input = commentContainer.find('.comment-body');
        const commentId = input.data('comment-id');
        const updatedCommentContent = input.val();


        const data = {
            content: updatedCommentContent
        };

        $.ajax({
            type: 'PUT',
            url: `/okw/comments/${commentId}`,
            headers: {'Authorization': token},
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                alert('댓글이 성공적으로 수정되었습니다.');
                window.location.reload();
            },
            error: function (error) {
                console.error('댓글 수정 오류: ', error);
            }
        });
    });

    $(document).on('click', '.comment-delete-btn', function () {
        const commentContainer = this.closest('p');
        const commentId = commentContainer.querySelector('.comment-body').dataset.commentId;

        $.ajax({
            type: 'DELETE',
            url: `/okw/comments/${commentId}`,
            headers: {'Authorization': token},
            success: function (response) {
                alert('댓글이 성공적으로 삭제되었습니다.');
                window.location.reload();
            },
            error: function (error) {
                console.error('댓글 삭제 오류: ', error);
            }
        });
    });
});


const token = Cookies.get('Authorization');

// 카드 정보 불러오기
function setCardData(response) {
    let cardTitleInput = document.getElementById("cardTitle");
    let cardDescriptionTextarea = document.getElementById("description");

    cardTitleInput.value = response.title;
    cardDescriptionTextarea.value = response.description;

    console.log(cardDescriptionTextarea);

    //카테고리 선택(select) 엘리먼트 설정
    const categoryChoiceElement = document.getElementById("categoryChoice");
    // 기존 옵션 삭제
    categoryChoiceElement.innerHTML = '';

    // 카테고리 선택(select) 엘리먼트 설정
    response.categoryResponseDtoList.forEach(category => {
        const option = document.createElement('option');
        option.value = category.categoryId;
        option.textContent = category.title;

        const selectCategoryId = response.categoryId;

        if (selectCategoryId) {
            categoryChoiceElement.value = selectCategoryId;
        }

        categoryChoiceElement.appendChild(option);
    });

    // 색상 선택(select) 엘리먼트 설정
    const cardChoiceElement = document.getElementById("cardChoice");
    const cardColor = response.color;

    for (let i = 0; i < cardChoiceElement.options.length; i++) {
        if (cardChoiceElement.options[i].value === cardColor) {
            cardChoiceElement.selectedIndex = i; // 선택한 옵션으로 변경
            break;
        }
    }
    //
    // // 작업자 선택(select) 엘리먼트 설정
    // const workerChoiceElement = document.getElementById("workerChoice");
    // const workerId = response.workerId;
    //
    // for (let i = 0; i < workerChoiceElement.options.length; i++) {
    //     if (workerChoiceElement.options[i].value === workerId.toString()) {
    //         workerChoiceElement.selectedIndex = i; // 선택한 옵션으로 변경
    //         break;
    //     }
    // }

    // 데드라인 선택(select) 엘리먼트 설정
    const yearSelect = document.getElementById("year");
    const monthSelect = document.getElementById("month");
    const daySelect = document.getElementById("day");
    const hourSelect = document.getElementById("hour");

    const deadline = new Date(response.deadline);

    yearSelect.value = deadline.getFullYear();
    monthSelect.value = (deadline.getMonth() + 1).toString().padStart(2, '0');
    daySelect.value = deadline.getDate().toString().padStart(2, '0');
    hourSelect.value = deadline.getHours().toString().padStart(2, '0');

// fileList 배열에서 파일 이름과 URL 가져와서 출력과 링크 생성

    let fileList = "";
    let fileBox = document.getElementById("fileBox");

    response.fileList.forEach(file => {

        // URL을 '/'로 분할하여 각 부분을 배열로 가져옵니다.
        const urlParts = file.fileName.split('/');

        // 배열에서 마지막 요소를 추출합니다.
        const fileNameWithUUID = urlParts[urlParts.length - 1];

        const fileUrl = file.fileName; // 파일 URL을 저장하고 있는 속성을 사용해야 함

        const fileId = file.fileId;

        let fileContent = `
                <p class="line">
                    <a href="${fileUrl}" data-file-id="${fileId}" class="file-body">${fileNameWithUUID}</a>
                    <button class="file-delete-btn">X</button>
                </p>`;
        fileList += fileContent;
    });
    fileBox.innerHTML = fileList;

    let commentList = "";
    let commentBox = document.getElementById("commentList");

    response.commentResponseDtoList.forEach(comment => {

        const commentId = comment.commentId;
        const content = comment.content;
        const nickname = comment.nickname;

        let commentContent = `
                <p class="line">
                    <input type="text" value="${content}" class="comment-body" data-comment-id="${commentId}"disabled>
                    <span class="author">${nickname}</span>
                    <button class="btn comment-update-btn"><img class="plus" src="/css/search.png" alt="plus"></button>
                    <button class="btn done-comment-btn" style="display: none">done</button>
                    <button class="btn comment-delete-btn">X</button>
                </p>`;
        commentList += commentContent;

    });
    commentBox.innerHTML = commentList;
}

let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
let lastPart = urlParts[urlParts.length - 1];
function doneButton() {
    let cardTitle = $("#cardTitle").val();
    let description = $("#description").val();
    let color = document.getElementById("cardChoice").value;
    let categoryChoice = document.getElementById("categoryChoice").value;

    $.ajax({
        type: "PUT",
        url:"/okw/cards/" + lastPart,
        headers: {'Authorization': token},
        contentType: "application/json",
        data: JSON.stringify({
            categoryId: categoryChoice,
            title: cardTitle,
            description: description,
            color: color
        })
    })
        .done(function () {
            alert("수정 완료");
            window.location.reload();
        })
        .fail(function (response) {
            alert("수정 오류: " + response.responseJSON.msg);
        })
}


const doneButtons = document.querySelectorAll('.done-btn');
doneButtons.forEach(function (button) {
    button.addEventListener('click', doneButton);
});




// 선택한 데드라인을 서버에 업데이트하는 함수
function updateDeadline() {
    const selectedYear = document.getElementById('year').value;
    const selectedMonth = document.getElementById('month').value;
    const selectedDay = document.getElementById('day').value;
    const selectedHour = document.getElementById('hour').value;

    // 선택한 데드라인을 원하는 형식으로 조합 (예: "2023-08-11 15")
    const selectedDeadline = `${selectedYear}-${selectedMonth}-${selectedDay} ${selectedHour}`;

    $.ajax({
        type: 'PUT',
        url: '/okw/cards/deadLine/' + lastPart,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            deadlineStr: selectedDeadline
        }),
        headers: {
            'Authorization': token
        }
    })
        .done(function(data) {
            alert('데드라인이 업데이트되었습니다.');
            window.location.reload();
            // 업데이트 이후에 필요한 UI 업데이트나 새로고침 처리 등을 진행할 수 있습니다.
        })
        .fail(function(error) {
            console.error('데드라인 업데이트 오류:', error);
        });
}

// "done-deadline-btn" 버튼에 대한 이벤트 리스너
const doneDeadlineButton = document.querySelector('.update-deadline-btn');
doneDeadlineButton.addEventListener('click', updateDeadline);



// 선택한 작업자를 서버에 업데이트하는 함수
function updateSelectedWorker() {
    const workerChoiceSelect = document.getElementById('workerChoice');
    const selectedWorkerId = workerChoiceSelect.value; // 선택한 작업자의 ID 가져오기
    console.log(selectedWorkerId);

    $.ajax({
        type: 'PATCH',
        url: '/okw/cards/worker',
        data: {
            cardId: lastPart,
            worker: selectedWorkerId
        },
        headers: {
            'Authorization': token
        }
    })
        .done(function(data) {
            alert('작업자가 업데이트되었습니다.');
            window.location.reload();
            // 업데이트 이후에 필요한 UI 업데이트나 새로고침 처리 등을 진행할 수 있습니다.
        })
        .fail(function(error) {
            console.error('작업자 업데이트 오류:', error);
        });
}

// "done-worker-btn" 버튼에 대한 이벤트 리스너
const doneWorkerButton = document.querySelector('.done-worker-btn');
doneWorkerButton.addEventListener('click', updateSelectedWorker);


// "done-file-btn" 버튼에 대한 이벤트 리스너
const doneFileButton = document.querySelector('.done-file-btn');
doneFileButton.addEventListener('click', uploadFiles);

// 파일을 서버에 업로드하는 함수
function uploadFiles() {
    const filesInput = document.getElementById('formFileMultiple');
    const selectedFiles = filesInput.files;

    const formData = new FormData();
    for (let i = 0; i < selectedFiles.length; i++) {
        formData.append('fileName', selectedFiles[i]);
    }

    $.ajax({
        type: 'PUT',
        url: '/okw/cards/files/' + lastPart,
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            'Authorization': token
        }
    })
        .done(function(data) {
            console.log(data);
            alert('파일 업로드 완료');
            window.location.reload();
            // 업로드 이후에 필요한 UI 업데이트나 새로고침 처리 등을 진행할 수 있습니다.
        })
        .fail(function(error) {
            console.error('파일 업로드 오류:', error);
        });
}



// 댓글 생성
function createComment() {
    // 댓글 필드에서 입력값을 가져옵니다.
    let commentContent = $("#comment").val();

    // 서버로 전송할 데이터 객체를 생성합니다.
    let data = {
        content: commentContent
    };

    // 서버로 AJAX POST 요청을 보냅니다.
    $.ajax({
        type: "POST",
        url:`/okw/comments/`+ lastPart, // 적절한 엔드포인트 URL로 대체합니다.
        headers: {'Authorization': token},
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            // 성공 시 처리 (성공 메시지 표시 또는 다른 동작 수행 가능)
            console.log("댓글이 성공적으로 추가되었습니다.");
            window.location.reload();
        },
        error: function (error) {
            // 오류 시 처리 (오류 메시지 표시 또는 다른 동작 수행 가능)
            console.error("댓글 추가 오류: ", error);
        }
    });
}

const commentCreateBtn = document.querySelector('.comment-create-btn');
commentCreateBtn.addEventListener('click', createComment);





