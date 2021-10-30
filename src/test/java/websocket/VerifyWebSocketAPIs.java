package websocket;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VerifyWebSocketAPIs {
    SocketServiceData context;

    @BeforeTest
    public void createContext(){
        context=new SocketServiceData();
        context.URI = "wss://demo.piesocket.com/v3/channel_1?api_key=oCdCMcMPQpbvNjUIzqtvF1d2X2okWpDQj4AwARJuAgtjhzKxVEjQU6IdCjwm&notify_self";
        context.timeOut=5;
        context.expectedMessage="This is a test";
        context.actualMessage="This is a test";
    }

    @Test
    public void verifyWebSocketAPI(){
        SocketServiceData responseContext=WebClient.getInstance().connectAndListen(context);
        Assert.assertEquals(responseContext.statusCode,1000,"Status code is different");
    }

    @Test
    public void verifyWebSocketAPI_TimeOutsAutomatically(){
        context.actualMessage="Invalid message";
        SocketServiceData responseContext=WebClient.getInstance().connectAndListen(context);
        Assert.assertEquals(responseContext.statusCode,1006,"Status code is different");
    }
}
