package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.dto.BigUserDTO;
import cn.kebabshell.muyi.common.dto.CtrlServiceDTO;
import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 18:15
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(Exception.class);
    @Autowired
    private UserService service;
    /**
     * 主页热门摄影师
     * @param count
     * @return List<UserBase>
     */
    @GetMapping("/hm/hot")
    MyResult getHotAuthor(int count){
        return new MyResult(
                ResultCode.SUCCESS, service.getHotAuthor(count));
    }

    /**
     * 登录
     * @param user 用户名密码
     * @return 只返回token，进入详情页再请求详细信息
     */
    @PostMapping("login")
    MyResult login(AuthUser user){
        CtrlServiceDTO<String> login = service.login(user);

        if (login.getSuccess())
            return new MyResult(ResultCode.SUCCESS, login.getData());
        else
            // 这个CtrlServiceDTO是无奈之举...不想改了
            return new MyResult(ResultCode.ERROR, login.getMsg());
    }

    /**
     * 注册，头像使用默认的
     * @param user
     * @return
     */
    @PostMapping("/register")
    MyResult register(BigUserDTO user) {
        // 失败返回错误信息（用户名重复）
        try {
            return new MyResult(ResultCode.SUCCESS, service.register(user));
        }catch (Exception e){
            log.warn(e.getMessage());
            return new MyResult(ResultCode.REPEATED_USER);
        }
    }

    /**
     * 更新用户信息
     * @param bigUserDTO
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/udp")
    MyResult update(BigUserDTO bigUserDTO, MultipartFile file, HttpServletRequest request){
        String token = request.getHeader("Token");
        try {
            Boolean update = service.update(bigUserDTO, file, token);
            if (update)
                return new MyResult(ResultCode.SUCCESS);
            else
                // 这里待定
                return new MyResult(ResultCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            return new MyResult(ResultCode.ERROR);
        }
    }

    /**
     * 删除此用户。
     * 感觉不应该开放这个接口，而是改为封禁
     * @param delId
     * @return
     */
    @PostMapping("/del")
    @RequiresRoles("root")
    MyResult del(Integer delId){

        try {
            service.del(delId);
            return new MyResult(ResultCode.SUCCESS);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return new MyResult(ResultCode.ERROR);
    }

    /**
     * 封禁用户，图片保留，压力小点
     * @param benId
     * @return
     */
    @PostMapping("/ben")
    MyResult ben(Integer benId){
        return new MyResult(ResultCode.ERROR);

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
    @PostMapping("/get")
    MyResult getUser(Integer userId, HttpServletRequest request){
        String token = request.getHeader("Token");
        CtrlServiceDTO<BigUserDTO> user = service.getUser(userId, token);
        if (user.getSuccess())
            return new MyResult(ResultCode.SUCCESS, user.getData());
        else
            return new MyResult(ResultCode.ERROR, user.getMsg());
    }




}
