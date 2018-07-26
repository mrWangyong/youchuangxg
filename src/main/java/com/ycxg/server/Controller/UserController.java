package com.ycxg.server.Controller;


import com.ycxg.server.Service.UserService;
import com.ycxg.server.model.License;
import com.ycxg.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService UserService;

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
            int userId =   userInfo.get(0).getUserId();
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
}
