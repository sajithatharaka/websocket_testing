package websocket;

import java.net.URISyntaxException;
import java.util.Map;

public class WebClient {
    private Client webSocket;

    private WebClient() {
    }

    public static WebClient getInstance() {
        return new WebClient();
    }

    public SocketServiceData connectAndListen(SocketServiceData socketContext) {
        boolean isSent=false;
        try {
            webSocket = new Client(socketContext);
            if (!socketContext.requestHeaders.isEmpty()) {
                final Map<String, String> requestHeaderParams = socketContext.requestHeaders;
                requestHeaderParams.forEach((key, value) -> {
                    webSocket.addHeader(key, value);
                });
            }
            webSocket.connectBlocking();
            while (!webSocket.isClosed()){
                if(webSocket.connectionAliveTime()>=socketContext.timeOut){
                    webSocket.close(1006,"Time Out");
                }
                if(!isSent){
                    webSocket.onMessage(socketContext.actualMessage);
                    isSent=true;
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return socketContext;
    }
}
