document.addEventListener("DOMContentLoaded", function () {
    const host = "http://" + window.location.host;

    const showMemberButton = document.querySelector(".showMember-menu-btn");
    const offcanvasLabel = document.querySelector("#showMemberoffcanvasScrollingLabel");

    const boardId = 1 //어떤 board가 눌렸는지 해당 boardId 업데이트

    const token = Cookies.get('Authorization');

    const boardTitle = document.querySelector('#board-title').textContent;  //Board Title

    showMemberButton.addEventListener("click", function () {
        offcanvasLabel.textContent = boardTitle + '에 초대된 맴버';

        showBoardMember(boardId);
    });

    function showBoardMember(boardId) {
        $.ajax({
            type:'GET',
            url:`/okw/boards/member/${boardId}`,
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

    document.querySelector('#button-addon2').addEventListener('click',() => {
        let search = $('#searchMember').val();

        console.log(search);

        $.ajax({
            type:'GET',
            url:'/okw/users/'
        })

    })

    function searchMember() {

    }
});