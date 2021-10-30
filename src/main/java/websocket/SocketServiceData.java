package websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketServiceData {
    String URI;
    String actualMessage;
    String expectedMessage;
    Map<String,String> requestHeaders=new HashMap<>();
    List<String> messageList=new ArrayList<>();
    int statusCode;
    int timeOut=10;
    int timeTaken;
}
