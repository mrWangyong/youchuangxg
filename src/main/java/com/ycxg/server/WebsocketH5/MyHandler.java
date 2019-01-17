package com.ycxg.server.WebsocketH5;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ycxg.server.Service.UserService;
import com.ycxg.server.model.User;
import com.ycxg.server.redis.RedisRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
public class MyHandler implements WebSocketHandler{

    @Autowired
    private RedisRepository redisRepository;

    private static final Map<String, WebSocketSession> users;

    private static ApplicationContext applicationContext;//启动类set入，调用下面set方法

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    static {
        users = new HashMap<>();
    }
    //新增socket
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        String ID = session.getUri().toString().split("ID=")[1];
        System.out.println(ID);
//        redisRepository.set("WEBSOCKET_USERID:"+ID,ID);

        if (ID != null) {
            users.put(ID, session);
            session.sendMessage(new TextMessage("成功建立socket连接"));
            System.out.println("ID="+ID);
            System.out.println("session="+session);
        }
        System.out.println("当前在线人数："+users.size());
    }

    //接收socket信息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        try{
            JSONObject jsonobject = JSONObject.fromObject(webSocketMessage.getPayload());
            System.out.println(jsonobject.get("id"));
            System.out.println(jsonobject.get("toUserId"));
            String toUserId = jsonobject.get("toUserId").toString() ;
            String fromUserId = jsonobject.get("id").toString() ;
            String toUserName = jsonobject.get("toUserName").toString() ;
            String fromUserName = jsonobject.get("fromUserName").toString() ;
            System.out.println(jsonobject.get("message")+":来自"+(String)webSocketSession.getAttributes().get("WEBSOCKET_USERID")+"的消息");

            String toUserMessage = fromUserId+":"+jsonobject.get("message")+":"+fromUserName ;
            String fromUserMessage = fromUserId+":"+jsonobject.get("message")+":"+fromUserName ;
            String toAllUserMessage = fromUserId+":"+jsonobject.get("message")+":"+fromUserName ;

            if (toUserId != "allUser"){   //一对一聊天
                sendMessageToUser(toUserId+"",new TextMessage(toUserMessage));
                sendMessageToUser(fromUserId+"",new TextMessage(fromUserMessage));
            }else {    // 群聊
                sendMessageToAllUsers(new TextMessage(toAllUserMessage));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }
        return  allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");

        String ID = session.getUri().toString().split("ID=")[1];
        System.out.println("连接出错"+ID);
       // changeUserStatus(0,ID);
        users.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);

        String ID = session.getUri().toString().split("ID=")[1];
        int statusType = 0 ;

        changeUserStatus(statusType,ID);
        System.out.println("连接已关闭"+ID);

        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        try {
            Integer clientId = (Integer) session.getAttributes().get("WEBSOCKET_USERID");
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 判断当前用户是否处于登录状态
     * @param clientId
     * @return
     */
    public static boolean userIsOpen(String clientId) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return false;
        else {
            unusualLogin(clientId);
            return true;
        }
    }

    /**
     * 异常登录推送
     * @param clientId
     * @return
     */
    public static boolean unusualLogin(String clientId) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        TextMessage message = new TextMessage("403");
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    *  改变用户状态
    * @param clientId
    * @param statusType
    * */
    public void changeUserStatus(Integer statusType,String clientId){
        UserService userService  = (UserService)applicationContext.getBean(UserService.class);
        User user = new User();
        int userId = Integer.parseInt(clientId);
        user.setUserId(userId);
        user.setStatus(statusType);
        int result = userService.updataStatus(user);
        System.out.println("result="+result);
    }

}

