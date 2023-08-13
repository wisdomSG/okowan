document.addEventListener("DOMContentLoaded", function () {
    function clickLogo() {
        window.location.href = "/okw/view/boards/board";
    }

    const logo = document.getElementById("logo");
    if (logo) {
        logo.addEventListener("click", clickLogo);
    }
})

function logout() {
    const token = Cookies.get('Authorization');

    $.ajax({
        type: "POST",
        url: "/okw/users/logout",
        headers: {"Authorization": token}
    })
        .done(function () {
            window.location.href = "/okw/view/users/login-signup";
        })
        .fail(function (response) {
            alert("로그아웃 오류: " + response.responseJSON.msg);
            window.location.href = "/okw/view/users/login-signup";
        })
}