package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.handler.result.MyMsg;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 18:15
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 主页热门摄影师
     * @param count
     * @return List<UserBase>
     */
    @GetMapping("/hm/hot")
    MyResult getAuthor(int count){
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * 登录
     * @param user 用户名密码
     * @return 只返回token，进入详情页再请求详细信息
     */
    @PostMapping("login")
    MyResult login(@RequestBody AuthUser user){
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * 注销
     * @return 。。。
     */
    @GetMapping("/logout")
    MyResult logout() {
        //让前端删除token
        return new MyResult(ResultCode.SUCCESS);
    }

    /**
     * 拿到用户详情页的信息
     * @param userId 如果是自己，就不需要id，如果是别人，就要
     * @return BigUserDTO
     */
    @GetMapping("/self")
    MyResult getUser(Integer userId){
        String token;
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

}
