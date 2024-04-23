// JavaScript

// 교환물품 리스트
var options = ["식품", "패션", "뷰티", "자동차", "출산/육아", "도서/문구", "식물", "펫", "기타"];
var selectElement = document.getElementById("reward_type");
options.forEach(function(option) {
    var optionElement = document.createElement("option");
    optionElement.value = option;
    optionElement.textContent = option;
    selectElement.appendChild(optionElement);
});

function editContent() {
    let id =  document.getElementById("id").value;

    let data = {
        id: document.getElementById("id").value,
        title:document.getElementById("title").value,
        content:document.getElementById("content").value,
        reward_type:document.getElementById("reward_type").value,
        reward_content:document.getElementById("reward_content").value,
        location:document.getElementById("location").value,
        geoLocationX: document.getElementById("geoLocationX").value,
        geoLocationY: document.getElementById("geoLocationY").value,
    };

    fetch('/jobDetail/' + data.id + '/update', {
        method: 'POST', // 또는 다른 HTTP 메소드 사용
        headers: {
            'Content-Type': 'application/json; charset=utf-8;',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
        // JSON 형식의 데이터를 JavaScript 객체로 변환
        const parsedData = JSON.parse(data);

        const id = parsedData.id;
        window.location.href = '/jobList/' + id;
    })

    .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
    });
}

function deleteContent() {
    let id =  document.getElementById("id").value;

    fetch('/jobDetail/' + id + '/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json; charset=utf-8;',
        },
//        body: JSON.stringify({ id: id })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        alert("글이 삭제되었습니다.");
        window.location.href = '/jobList.html';
    })

    .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
    });

}