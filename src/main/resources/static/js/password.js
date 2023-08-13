document.addEventListener("DOMContentLoaded", function () {
    const host = "http://" + window.location.host;

    const token = Cookies.get('Authorization');

    function updatePassword() {
        let currentPassword = document.getElementById("current-password").value;
        let newPassword = document.getElementById("new-password").value;
        let confirmNewPassword = document.getElementById("confirm-new-password").value;

        $.ajax({
            type: "PUT",
            url: "/okw/users/password",
            headers: {"Authorization": token},
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                currentPassword: currentPassword,
                newPassword: newPassword,
                confirmNewPassword: confirmNewPassword
            })
        })
            .done(function () {
                alert("비밀번호 수정 완료");
                logout();
            })
            .fail(function (response) {
                alert("비밀번호 수정 오류: " + response.responseJSON.msg);
            })
    }

    const updatePasswordButton = document.getElementById("update-password-button");
    updatePasswordButton.addEventListener('click', updatePassword);
})