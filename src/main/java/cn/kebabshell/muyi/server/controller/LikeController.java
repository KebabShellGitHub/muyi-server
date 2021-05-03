package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.entity.HitBase;
import cn.kebabshell.muyi.common.entity.LikeBase;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.LikeService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    MyResult add(@RequestBody LikeBase likeBase, HttpServletRequest request){
        String token = request.getHeader("Token");
        return service.addLike(likeBase, token) ?
                new MyResult(ResultCode.SUCCESS, true)
                :
                new MyResult(ResultCode.ERROR, false);
    }

    @PostMapping("/cancel")
    MyResult cancel(@RequestBody LikeBase likeBase, HttpServletRequest request){
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


    @GetMapping("/flag")
    MyResult likeFlag(Integer userId, Integer picId){
        return new MyResult(ResultCode.SUCCESS, service.likeFlag(userId, picId));
    }

}
