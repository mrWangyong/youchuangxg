package com.ycxg.server.Websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
@Controller
public class WsController {
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
        System.out.println("用户名："+message.getUsername());
        System.out.println("消息内容："+message.getMessage());
        String usernameRequest = message.getUsername() ;
        String messageRequest = message.getMessage() ;
        String result = usernameRequest +":"+ messageRequest;
        return new ResponseMessage(result);
    }

    //@MessageMapping("/welcome")
    @SendToUser("/topic/getResponseUsername")
    public ResponseMessage sayUser(RequestMessage message) {
        System.out.println("用户名："+message.getUsername());
        System.out.println("消息内容："+message.getMessage());
        String usernameRequest = message.getUsername() ;
        String messageRequest = message.getMessage() ;
        String userId = "666";
        String result = usernameRequest +":"+ messageRequest+":"+userId;
        return new ResponseMessage(result);
    }
}