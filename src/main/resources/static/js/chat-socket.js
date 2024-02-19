let stompClient = null;

const ChatSocket = {
    connect: function(roomObj) {
        let socket = new SockJS('/chat/register');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // subscribe: 브로커가 보내는 메세지를 수신할 경로
            stompClient.subscribe('/chat/room/' + roomObj.chatRoomId, function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    },
    disconnect: function() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    },
    sendMessage: function(roomId, message) {
        stompClient.send("/chat/message/" + roomId, {}, JSON.stringify({'message': message}));
    },
    showGreeting: function(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }
}