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
            fetchWorkerList();
            setCardData(response);
            categoryId = response.categoryId;


        })
        .fail(function (response, status, xhr) {
            alert("카드 정보 불러오기 실패");
            console.log(response);
        })


    // 작업자 조회
    // 작업자 선택 `<select>` 요소
    const workerChoiceSelect = document.getElementById('workerChoice');
    // 작업자 목록을 가져오는 함수
    function fetchWorkerList() {
        const boardId = 1; // BoardId 값을 어떻게 가져올지에 따라서 수정

        $.ajax({
            type: 'GET',
            url: `/okw/boards/worker/${boardId}`,
            headers: {
                'Authorization': token
            }
        })
            .done(function(data) {
                workerChoiceSelect.innerHTML ='';

                // 서버에서 가져온 작업자 목록을 `<option>` 요소로 변환하여 추가
                data.forEach(worker => {
                    console.log(worker);
                    const option = document.createElement('option');
                    option.value = worker.userId;
                    option.textContent = worker.nickname;

                    console.log(option);
                    workerChoiceSelect.appendChild(option);
                    console.log("작업자 리스트 생성 완료");
                });
            })

            .fail(function(error) {
                console.error('Error fetching worker list:', error);
            });
    }

})

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
    console.log(deadline.getFullYear());
    monthSelect.value = (deadline.getMonth() + 1).toString().padStart(2, '0');
    daySelect.value = deadline.getDate().toString().padStart(2, '0');
    hourSelect.value = deadline.getHours().toString().padStart(2, '0');

}




let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
let lastPart = urlParts[urlParts.length - 1];
function doneButton() {
    let cardTitle = $("#cardTitle").val();
    let description = $("#description").val();
    // let color = document.getElementById("cardChoice").value;
    // let category = document.getElementById("categoryChoice").value;

    $.ajax({
        type: "PUT",
        url:"/okw/cards/" + lastPart,
        headers: {'Authorization': token},
        contentType: "application/json",
        data: JSON.stringify({
            categoryId:categoryId,
            title: cardTitle,
            description: description
            // color: color,
            // categoryId: category
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