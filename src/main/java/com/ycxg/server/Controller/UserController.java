package com.ycxg.server.Controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycxg.server.Service.SmsService;
import com.ycxg.server.Service.UserService;
import com.ycxg.server.WebsocketH5.MyHandler;


import com.ycxg.server.model.Sms;
import com.ycxg.server.model.User;
import com.ycxg.server.redis.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService UserService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisRepository redisRepository;



    /*
    *  注册
    *
    * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/register",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object> userRegister(
            @RequestBody User user) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();

        String userName = user.getUserName();
        String password = user.getPassword();

        try {
            UserService.register(user);
        }catch (Exception E) {
            result.put("resultCode","201");
            result.put("message","用户名已经存在");
            return  result  ;
        }

        result.put("resultCode","200");
        result.put("message","注册成功");
        return  result  ;
    }

    /*
     *  登录
     *
     * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/login",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object> userLogin(
            @RequestBody User user) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();

        String userName = user.getUserName();
        String password = user.getPassword();

        List<User> userInfo =  UserService.login(user);

        if(userInfo.size() == 0){
            result.put("resultCode","202");
            result.put("message","账号或者密码出错");
            return  result  ;
        }else {

            int userId =  userInfo.get(0).getUserId();

            user.setUserName("");
            user.setPassword("");

            user.setStatus(1);
            user.setUserId(userId);
            UserService.updataStatus(user);

            String clientId = Integer.toString(userId);
            MyHandler myHandler = new MyHandler();
            boolean isOpen = MyHandler.userIsOpen(clientId);
            System.out.println(isOpen);


            result.put("resultCode",200);
            result.put("message","登录成功");
            result.put("userId",userId);
            return  result  ;
        }
    }


    /*
     *  用户列表
     *
     * */

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getUserlist/{pageNum}/{pageSize}",produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public Map<String , Object> getUserlist(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        Map<String , Object> result = new HashMap<String , Object>();
        List<User> userlist=  UserService.getUserlist(pageNum,pageSize);
        result.put("resultCode","200");
        result.put("message","请求成功");
        result.put("userlist",userlist);
        return  result  ;
    }


    /*
     *  修改密码
     *
     * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/editorUser",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object> editorUser(
            @RequestBody User user) throws FileNotFoundException, UnsupportedEncodingException {

        Map<String , Object> result = new HashMap<String , Object>();
        List<User> userInfo =  UserService.login(user);
        if(userInfo.size() == 0){
            result.put("resultCode","202");
            result.put("message","原密码出错");
            return  result  ;
        }
        user.setPassword(user.getPasswordNew());
        int res =  UserService.editorUser(user);
        result.put("resultCode","200");
        result.put("message","请求成功");
       // result.put("res",res);
        return  result  ;
    }


    /*
     * 计算总条数
     * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/countUser",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.GET)
    public Map<String , Object>  countUser() throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();
        int count =  UserService.countUser();
        result.put("resultCode","200");
        result.put("message","成功了");
        result.put("count",count);
        return  result ;
    }


    /*
     * 发短信
     * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/sendMsg",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object>  sendMsg(@RequestBody Sms sms) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();
        String phonenum = sms.getPhone();
        Map<String , Object> resMap =  smsService.sendSmsCode(phonenum);
        result.put("resMap",resMap);
        result.put("message","短信已发送，请注意查收");
        result.put("code",200);
        return  result ;
    }

    /*
     * 短信验证
     * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/checkMsgCode",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object>  checkMsgCode(@RequestBody Sms sms) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();
        boolean res =  smsService.validSmsCode("smoke:"+sms.getPhone(),sms.getCode());
        if(res){
            result.put("resCode",200);
            result.put("message","验证成功");
        }else {
            result.put("resCode",201);
            result.put("message","验证失败");
        }
        return  result ;
    }

    /*
     * redis 测试
     * */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/redisTest",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object>  redisTest(@RequestBody Sms sms) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();

        String userName = "1";
        String password = "1";
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        List<User> userInfo =  UserService.login(user);
        String str = toJSON(userInfo.get(0));
        redisRepository.set("youchuangxg_phone:"+sms.getPhone(),str);
        String resStr = redisRepository.get("youchuangxg_phone:"+sms.getPhone());

        String[] str1 = {"AAA","BBB","CCC"};
        String[] str2 = {"1","2","3"};

        redisRepository.set(str1,str2);


        result.put("resCode",200);
        result.put("message","操作成功");
        result.put("resStr",resStr);
        return  result ;
    }

    /**
     * 将对象转换为json格式字符串
     *
     * @return json string
     */
    public static String toJSON(Object obj) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 将json形式字符串转换为java实体类
     *
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readValue;
    }
}
