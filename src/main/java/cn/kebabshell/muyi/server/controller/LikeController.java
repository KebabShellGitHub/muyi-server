package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.entity.HitBase;
import cn.kebabshell.muyi.common.entity.LikeBase;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.LikeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 19:58
 **/
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService service;
    /**
     * 点赞
     * @param likeBase
     * @return
     */
    @PostMapping("/add")
    MyResult add(LikeBase likeBase, HttpServletRequest request){
        String token = request.getHeader("Token");
        return service.addLike(likeBase, token) ?
                new MyResult(ResultCode.SUCCESS)
                :
                new MyResult(ResultCode.ERROR);
    }

    @PostMapping("/cancel")
    MyResult cancel(LikeBase likeBase, HttpServletRequest request){
        String token = request.getHeader("Token");
        return service.cancelLike(likeBase, token) ?
                new MyResult(ResultCode.SUCCESS)
                :
                new MyResult(ResultCode.ERROR);
    }

    @GetMapping("/count")
    MyResult count(Integer picId){
        return new MyResult(ResultCode.SUCCESS, service.getLikeCount(picId));
    }



}
