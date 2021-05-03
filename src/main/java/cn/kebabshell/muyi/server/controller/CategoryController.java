package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-04-24 10:51
 **/

@RestController
@RequestMapping("/sort")
public class CategoryController {
    private static Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService service;

    @GetMapping("/all")
    MyResult getAllSort(){
        LOG.info("getAllSort");
        return new MyResult(ResultCode.SUCCESS, service.getAll());
    }

    @GetMapping("/pic")
    MyResult getPicSorts(Integer picId){
        LOG.info("getPicSorts");
        return new MyResult(ResultCode.SUCCESS, service.getPicSorts(picId));
    }
}
