document.addEventListener("DOMContentLoaded", function () {
    const host = "http://" + window.location.host;

    // 로그인-회원가입 전환
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    signUpButton.addEventListener('click', () => {
        container.classList.add("right-panel-active");
    });

    signInButton.addEventListener('click', () => {
        container.classList.remove("right-panel-active");
    });

    // 로그인, 회원가입
    function signup() {
        let username = $('#signup-email').val();
        let password = $('#signup-password').val();
        let nickname = $('#signup-nickname').val();

        $.ajax({
            type: "POST",
            url: "/okw/users/signup",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                username: username,
                password: password,
                nickname: nickname
            })
        })
            .done(function () {
                alert('회원가입 성공');
                window.location.reload();
            })
            .fail(function (response) {
                alert('회원가입 오류: ' + response.responseJSON.msg);
            })
    }

    function login() {
        let username = $('#login-email').val();
        let password = $('#login-password').val();

        $.ajax({
            type: "POST",
            url: "/okw/users/login",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                username: username,
                password: password
            })
        })
            .done(function (response, status, xhr) {
                const token = xhr.getResponseHeader('Authorization');

                // 쿠키 만료 시간(초 단위) 설정
                let maxAgeInSeconds = 60 * 60; // 1시간

                // 현재 시간에 만료 시간(초 단위)을 더한 값을 구함
                let expirationDate = new Date();
                expirationDate.setTime(expirationDate.getTime() + (maxAgeInSeconds * 1000));

                Cookies.set('Authorization', token, {path: '/', expires: expirationDate});
                alert('로그인 성공');

                window.location.href = host + '/okw/view/users/profile';
            })
            .fail(function (response) {
                alert('로그인 오류: ' + response.responseJSON.msg);
            })
    }

    const realSignupButton = document.getElementById('signup-button');
    const realLoginButton = document.getElementById('login-button');

    realSignupButton.addEventListener('click', () => {
        signup();
    });

    realLoginButton.addEventListener('click', () => {
        login();
    });
});

