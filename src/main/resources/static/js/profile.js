document.addEventListener("DOMContentLoaded", function () {
    const host = "http://" + window.location.host;

    let userId = 1; // board 페이지에서 받아와야 하는 값
    const token = Cookies.get('Authorization');

    $.ajax({
        type: "GET",
        url: "/okw/users/profile/" + userId,
        headers: {'Authorization': token}
    })
        .done(function (response) {
            console.log(response);
            document.getElementById("nickname").value = response.nickname;
            document.getElementById("introduction").value = response.introduction;
            document.getElementById("address").value = response.address;
        })
        .fail(function () {
            window.location.href = "/okw/view/users/login-signup";
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

    // 완료 버튼 클릭시 필드 값 기준으로 수정 요청
    const doneButtons = document.querySelectorAll('.done-btn');
    doneButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            let nickname = document.getElementById("nickname").value;
            let introduction = document.getElementById("introduction").value;
            let address = document.getElementById("address").value;

            $.ajax({
                type: "PUT",
                url: "/okw/users/profile/" + userId,
                headers: {'Authorization': token},
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    nickname: nickname,
                    introduction: introduction,
                    address: address
                })
            })
                .done(function () {
                    alert("프로필 수정 완료");
                    window.location.reload();
                })
                .fail(function (response) {
                    alert("프로필 수정 오류: " + response.responseJSON.msg);
                })
        });
    });
})