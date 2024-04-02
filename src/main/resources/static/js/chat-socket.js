let stompClient = null;

const ChatSocket = {
    connect: function(roomId) {
        let socket = new SockJS('/chat/register');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // subscribe: 브로커가 보내는 메세지를 수신할 경로
            stompClient.subscribe('/chat/room/' + roomId, function (response) {
                console.log(response);
                Chat.appendChatMessage(JSON.parse(response.body));
            }.bind(this));
        });
    },
    disconnect: function() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    },
    sendMessage: function(roomObj, message) {
        stompClient.send("/chat/client/message/" + roomObj.chatRoomId, {}, JSON.stringify({...roomObj, 'content': message}));
    }
}