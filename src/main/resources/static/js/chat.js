const chatConst = {
    chatRoomIdAttrName: "data-room-id"
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

        fetch("/chatRoom/" + userId)
        .then(response => {
            console.log('response: ', response);
            return response.json()
        })
        .then(data => {
            console.log('data: ', data);
            this.makeChatRoomElements(data);
        })
        .catch(error => {
            console.log('error: ', error);
        });
    },
    initChatWindow: function(roomObj) {
        console.log('initChatWindow: ', roomObj)

        // connect websocket
        ChatSocket.connect(roomObj);

        // 페이지를 벗어날 때 disconnect 호출
        window.addEventListener('beforeunload', function(event) {
            ChatSocket.disconnect(roomObj);
        });

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
        })
        sendBtn.addEventListener('click', function(){
            this.sendMessage(roomObj);
        }.bind(this));

        // 대화 내용 불러오기
        const params = new URLSearchParams();
        params.append("roomId", roomObj.chatRoomId);
        params.append('userId', this.getUserId());

        fetch("/chat/message")
            .then(response => {
                console.log('response: ', response);
                return response.json()
            })
            .then(data => {
                console.log('data: ', data);
                this.makeChatMessageElements(data);
            })
            .catch(error => {
                console.log('error: ', error);
            });
    },
    makeChatMessageElements: function(data){
        const userId = this.getUserId();
        const root = document.getElementById('chat-window');
        const chatMessageRoot = root.getElementById("chat-messages");
        root.removeAttribute('hidden');
        chatMessageRoot.innerHTML = '';

        console.log('chatMessages: ', data);
        for (let message of data) {
            console.log(message);
            const messageEl = document.createElement('div');

            if(message.userId === userId)
                messageEl.classList.add('chat-message-me');
            else
                messageEl.classList.add('chat-message-partner');

            chatMessageRoot.appendChild(messageEl);
        }
    },
    sendMessage: function(roomObj) {
        const message = document.querySelector(".chat-input textarea").value;

        console.log('sendMessage: ' + message);
        ChatSocket.sendMessage(roomObj, message);
    }
}



