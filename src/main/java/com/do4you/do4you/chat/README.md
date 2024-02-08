# 채팅 시스템

### 1. WebSocket 통신 기능 구현 (프로토타입)
<details>
<summary>구현 내용</summary>

1:1 채팅방만을 구현하기 때문에 WebScoket 만을 사용해 세션을 직접 관리하도록 개발 진행
참고: https://docs.spring.io/spring-framework/reference/web/websocket/server.html

### 1-1. WebSocket 의존성 추가
- build.gradle
```groovy
implementation 'org.springframework.boot:spring-boot-starter-websocket'
```
<br />

### 1-2. WebSocketHandler 구현
간단하게 구현해보기 위해 스프링 문서와 같이 `TextWebSocketHandler`를 상속해서 필요한 메서드만 재정의하였다.   
WebSocketSession의 id를 Key로 Map에 담아두고 서버에서 클라이언트로 메세지를 보낼 때 맵에 있는 모든 세션에 데이터를 전송하도록 처리한다.

- **ChatWebSocketHandler**

```java
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 관리할 세션 목록
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    /** 
     * 웹소켓 커넥션이 정상적으로 맺어진 경우 호출
     * 해당 세션 정보를 Map에 추가하고, 접속중인 세션에 새로운 연결을 알려주는 메세지를 전송한다. 
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("웹소켓 연결: " + session.getUri());

        // 세션 목록에 추가
        sessions.put(session.getId(), session);

        // 저장된 모든 세션에 사용자 연결 알림 전송 (본인 제외)
        sessions.values().forEach(s-> {
            if(!s.getId().equals(session.getId())) {
                try {
                    s.sendMessage(new TextMessage("사용자가 연결되었습니다."));
                } catch (IOException e) {
                    System.out.println("오류 발생 !! " + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 연결된 세션으로 데이터를 수신한 경우 호출
     * 관리중인 모든 세션에 데이터를 전송한다. (채팅방 구분 없음)
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("데이터 통신: " + session.getId() + " = " + message.toString());
        
        // 저장된 모든 세션에 데이터 전송 (본인 제외)
        sessions.values().forEach(s-> {
            if(!s.getId().equals(session.getId())) {
                try {
                    s.sendMessage(new TextMessage(message.getPayload()));
                } catch (IOException e) {
                    System.out.println("오류 발생 !! " + e.getLocalizedMessage());
                }
            }
        });
    }
    
    /**
     * 웹소켓 연결이 닫혔을 때 호출
     * 세션 목록에서 삭제한다.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("웹소켓 종료: " + session.getUri() + ", " + status);

        // 연결 종료 시 세션 삭제
        sessions.remove(session.getId());
    }
}

```
<br />

### 1-3. WebSocketConfiguration 작성
```WebSocketConfigurer ``` 를 구현해서 1-2 에서 만들어둔 ```WebSocketHandler```를 사용하기 위한 설정을 해준다.  
/chat 으로 들어온 요청은 ```WebSocketHandler```를 사용해서 처리한다.

```java
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final ChatWebSocketHandler handler;

    @Autowired
    public WebSocketConfiguration(ChatWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/chat");
    }
}
```
<br />

### 1-4. 테스트
postman 을 사용해 간단한 테스트를 해볼 수 있다.   
(New 버튼 > WebSocket 선택)
- 사용자1
  ![img.png](../../../../../resources/document/chat/websocket-test-user1.png)

- 사용자2
  ![websocket-test-user2.png](..%2F..%2F..%2F..%2F..%2Fresources%2Fdocument%2Fchat%2Fwebsocket-test-user2.png)

</details>

