const chatConst = {
    chatRoomIdAttrName: "data-room-id"
}

async function fetchData(url) {
    try {
        const response = await fetch(url);
        const data = await response.json(); // 서버로부터 받은 데이터를 JSON 형태로 변환
        console.log(url + ': ', data);  // 데이터 출력
        return data;    // data => Promise.resolve(data)
    } catch (error) {
        console.error("Error fetching data: ", error); // 오류 발생 시 메시지 출력
    }
}

const Chat = {
    makeChatRoomElements: function (chatRooms) {
        const root = document.querySelector('#chat-list .list-group');

        console.log('chatRooms: ', chatRooms);
        for (let chatRoom of chatRooms) {
            const roomObj = JSON.parse(JSON.stringify(chatRoom));

            // element 복사
            const roomNode = document.getElementById("chat-list-item-template").cloneNode(true);
            roomNode.removeAttribute('id');
            roomNode.classList.remove('hidden');
            roomNode.setAttribute(chatConst.chatRoomIdAttrName, roomObj.chatRoomId);
            roomNode.querySelector(".chatroom-name").innerText = roomObj.partner.nickname;
            roomNode.querySelector(".chatroom-message").innerText = roomObj.lastMessage || '마지막메세지입니다.';

            if(roomObj.unreadMessageCount >= 0) {
                let countElement = roomNode.querySelector(".chatroom-count");
                countElement.setAttribute('disabled', false);
                countElement.innerText = roomObj.unreadMessageCount || '0';
            }

            roomNode.addEventListener('click', function(){
                console.log('##### clickRoomNode')
                this.initChatWindow(roomObj);
            }.bind(this));

            root.appendChild(roomNode);
        }
    },
    getUserId: function () {
        // TODO : 로그인 후 변경
        let userId = localStorage.getItem("userId");
        console.log('userId: ' + userId)

        if (!userId) {
            userId = prompt("사용자ID를 입력하세요.");
            localStorage.setItem("userId", userId);
        }

        return userId;
    },
    initChatRooms: function() {
        console.log('initChatRooms')

        const userId = this.getUserId();
        fetchData("/chatRoom/list?userId=" + userId)
            .then(function(data){
                this.makeChatRoomElements(data)
            }.bind(this));
    },
    initChatWindow: function(roomId) {
        /**  webSocket setting */
        // connect websocket
        ChatSocket.connect(roomId);
        // disconnect websocket
        window.addEventListener('beforeunload', function(event) {
            ChatSocket.disconnect();
        });

        /** room 데이터로 채팅방 초기화 */
        fetchData("/chatRoom/" + roomId + "?userId=" + this.getUserId())
            .then(function(roomObj){
                // 채팅방 내부 보이기
                const chatArea = document.getElementById('chat-area');
                chatArea.classList.remove('hidden');

                // 채팅방 제목에 닉네임 설정
                const header = chatArea.querySelector('#chat-window .fixed-title');
                header.innerText = roomObj.partner.nickname;

                // 전송 버튼에 이벤트 추가 (기등록된 click 이벤트 리스너들은 삭제)
                const sendBtn = chatArea.querySelector(".chat-input .send-button");
                const sendBtnClicks = sendBtn.querySelectorAll('[onClick]');
                sendBtnClicks.forEach(listener=> {
                    sendBtn.removeEventListener('click', listener);
                });
                sendBtn.addEventListener('click', function(){
                    this.sendMessage(roomObj);
                }.bind(this));

                // input에 이벤트 추가
                const input = chatArea.querySelector(".chat-input input");
                const inputEnter = input.querySelectorAll('[onkeydown]');
                inputEnter.forEach(listener=> {
                    sendBtn.removeEventListener('keydown', listener);
                });
                input.addEventListener('keydown', function(event){
                    if(event.keyCode == 13) {// 13: Enter Key
                        this.sendMessage(roomObj);
                    }
                }.bind(this));

                // 대화 내용 불러오기
                const params = new URLSearchParams();
                params.append("roomId", roomObj.chatRoomId);
                params.append('userId', this.getUserId());

                fetchData("/chat/message/recent/" + roomObj.chatRoomId)
                    .then(function(data){
                        // 채팅 메세지 elements 추가
                        this.makeChatMessageElements(data);

                        // 스크롤 가장 아래로 내리기
                }.bind(this));

                // 스크롤 이벤트 추가
                // window.addEventListener("scroll", function(){
                //     if (window.y === 0) {
                //         const newData = fetchDummyData();
                //         prependDataToPage(newData);
                //
                //         // 새로운 콘텐츠를 추가한 후에도 같은 위치에 유지되도록 스크롤 조정
                //         chatArea.scrollTop = chatArea.scrollHeight;
                //     }
                // }.bind(this))
                // 스크롤이 최대 길이보다 길 경우 가장 마지막 데이터를 기준으로 데이터 요청
                // 데이터를 다시 chat-messages div의 맨앞에 추가


            }.bind(this));
    },
    makeChatMessageElements: function(data){
        const userId = this.getUserId();
        const messageRoot = document.getElementById("chat-messages");
        messageRoot.innerHTML = '';

        console.log('init recent messages: ', data);
        for (let message of data) {
            const messageEl = document.createElement('div');
            const p = document.createElement('p');
            p.innerText = message.content;

            if(message.userId === userId)
                messageEl.setAttribute('class', 'chat-message me');
            else
                messageEl.setAttribute('class', 'chat-message partner');

            messageEl.appendChild(p);

            // messageRoot.appendChild(messageEl);

            messageRoot.prepend(messageEl);
        }
    },
    sendMessage: function(roomObj) {
        const input = document.querySelector(".chat-input input");

        console.log('sendMessage: ' + input.value);
        ChatSocket.sendMessage(roomObj, input.value);

        input.value = '';
    },
    appendChatMessage(message){
        const messageRoot = document.getElementById("chat-messages");
        const messageEl = document.createElement('div');
        const p = document.createElement('p');
        p.innerText = message.content;

        if(message.userId === this.getUserId())
            messageEl.setAttribute('class', 'chat-message me');
        else
            messageEl.setAttribute('class', 'chat-message partner');

        messageEl.appendChild(p);
        messageRoot.appendChild(messageEl);
    }
}






