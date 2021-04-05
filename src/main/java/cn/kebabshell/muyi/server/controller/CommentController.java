package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.entity.CommentBase;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.CommentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @create: 2021-03-26 19:57
 **/

@RestController
@RequestMapping("/cm")
public class CommentController {
    private static Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService service;

    /**
     * 添加评论
     * @param commentBase
     * @return
     */
    @PostMapping("/add")
    @RequiresRoles("common")
    MyResult addComment(CommentBase commentBase){
        log.info("addComment:commentBase:" + commentBase);
        return service.addComment(commentBase) ?
                new MyResult(ResultCode.SUCCESS)
                :
                new MyResult(ResultCode.ERROR);
    }

    /**
     * 删除评论（仅限管理员）
     * @param commentId
     * @return
     */
    @GetMapping("/del")
    @RequiresRoles("common-root")
    MyResult delComment(Integer commentId){
        log.info("del:commentId:" + commentId);
        return service.delComment(commentId) ?
                new MyResult(ResultCode.SUCCESS)
                :
                new MyResult(ResultCode.ERROR);
    }

    /**
     * 查评论
     * @param picId
     * @param pageNum
     * @param count
     * @return
     */
    @GetMapping("/get")
    MyResult getComment(int picId, int pageNum, int count){
        return new MyResult(
                ResultCode.SUCCESS,
                service.getComment(picId, pageNum, count)
        );
    }
}
