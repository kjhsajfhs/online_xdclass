package xyz.kyjef.online_xdclass.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.kyjef.online_xdclass.domain.User;
import xyz.kyjef.online_xdclass.request.LoginRequest;
import xyz.kyjef.online_xdclass.service.UserService;
import xyz.kyjef.online_xdclass.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo ){

        int rows = userService.save(userInfo);

        return rows == 1 ? JsonData.buildSuccess("注册成功"): JsonData.buildError("注册失败，请重试");

    }


    /**
     * 登录接口
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest){

        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(), loginRequest.getPwd());

        return token == null ?JsonData.buildError("登录失败，账号密码错误"): JsonData.buildSuccess(token,"登录成功");

    }


    /**
     * 根据用户id查询用户信息
     * @param request
     * @return
     */
    @GetMapping("findUserInfoByToken")
    public JsonData findUserInfoByToken(HttpServletRequest request){

        Integer userId = (Integer) request.getAttribute("user_id");

        if(userId == null){
            return JsonData.buildError("查询失败");
        }

        User user =  userService.findByUserId(userId);

        return JsonData.buildSuccess(user,"查询成功");

    }


}
