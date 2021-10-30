package websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class Client extends WebSocketClient {
    SocketServiceData dataContext;
    Date openedTime,closedTime;


    public Client(URI serverUri) {
        super(serverUri);
    }

    public Client(SocketServiceData dataContext) throws URISyntaxException {
        super(new URI(dataContext.URI));
        this.dataContext=dataContext;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        openedTime=new Date();
        System.out.println("Opened Connection "+dataContext.URI);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received "+message);
        dataContext.messageList.add(message);
        if(dataContext.expectedMessage.equals(message)){
            closeConnection(1000,"Received expected message");
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        int timeElapsed = connectionAliveTime();
        System.out.println("Connection time (seconds) "+timeElapsed);
        dataContext.statusCode=code;
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error in connection "+ex.getMessage());
    }

    public int connectionAliveTime(){
        closedTime=new Date();
        int timeInSeconds=(int) (closedTime.getTime()-openedTime.getTime())/1000;
        dataContext.timeTaken=timeInSeconds;
        return timeInSeconds;
    }
}
