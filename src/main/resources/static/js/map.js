function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라가 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}
    document.getElementById("postForm").addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 동작 방지 (폼 제출)
        const formData = new FormData(this); // 폼 데이터 가져오기

        // [Soiiii] 사용자가 작성한 full location 저장
        const location = document.getElementById("sample6_address").value + " " + document.getElementById("sample6_detailAddress").value;
        // [Soiiii] formData에 key, value 로 저장
        formData.append("location", location);

        // 폼 데이터를 JSON으로 변환
        const jobData = {};
        formData.forEach((value, key) => {
            jobData[key] = value;
        });

        console.log('jobData:', jobData);

        // 서버에 POST 요청 보내기
        fetch('/jobWrite', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jobData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            alert('게시글이 성공적으로 등록되었습니다.');
            window.location.href = '/jobList.html';
        })
        .catch(error => {
            console.error('Error:', error);
            // 오류 발생 시 사용자에게 알림 처리
            alert('게시글을 작성하는 도중 오류가 발생했습니다.');
        });
    });

    document.getElementById("postForm").addEventListener("findLocation", function(event) {
});
