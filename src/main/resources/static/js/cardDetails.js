document.addEventListener("DOMContentLoaded", function () {
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
            setCardData(response);
        })
        .fail(function (response, status, xhr) {
            alert("카드 정보 불러오기 실패");
            console.log(response);
        })

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

    const deadlineButtons = document.querySelectorAll('.update-deadline-btn');
    deadlineButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const p = this.closest('p');
            const select = p.querySelector('select');
            const doneButton = p.querySelector('.done-deadline-btn');

            this.style.display = 'none';
            doneButton.style.display = 'block';

            select.disabled = false;
        });
    });

    const monthSelect = document.getElementById('month');
    const daySelect = document.getElementById('day');
    const yearSelect = document.getElementById('year');
    const currentYear = new Date().getFullYear();
    const endYear = 2030;
    const hourSelect = document.getElementById('hour');
    const minuteSelect = document.getElementById('minute');


    // 월별 일수를 저장한 객체
    const daysInMonth = {
        1: 31, 2: 28, 3: 31, 4: 30, 5: 31, 6: 30,
        7: 31, 8: 31, 9: 30, 10: 31, 11: 30, 12: 31
    };

    // 월 옵션 생성
    for (let month = 1; month <= 12; month++) {
        const option = document.createElement('option');
        option.value = month;
        option.textContent = month;
        monthSelect.appendChild(option);
    }

    // 년 옵션 생성
    for (let year = currentYear; year <= endYear; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }

    // 월 선택에 따른 일 옵션 업데이트
    monthSelect.addEventListener('change', function () {
        daySelect.innerHTML = ''; // 기존 옵션 삭제

        const selectedMonth = parseInt(this.value);
        const days = daysInMonth[selectedMonth];

        if (selectedMonth !== 0) {
            for (let day = 1; day <= days; day++) {
                const option = document.createElement('option');
                option.value = day;
                option.textContent = day;
                daySelect.appendChild(option);
            }
        }
    });

    // 시간 옵션 생성
    for (let hour = 0; hour <= 23; hour++) {
        const option = document.createElement('option');
        option.value = hour;
        option.textContent = hour.toString().padStart(2, '0'); // 시간을 2자리 숫자로 표시
        hourSelect.appendChild(option);
    }


    // 백엔드 연결 (U,D)


})

// 카드 정보 불러오기
function setCardData(response) {
    document.getElementById("cardTitle").value=response.title;
    document.getElementById("cardDescription").textContent=response.description;
}