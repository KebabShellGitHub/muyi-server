package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.entity.HitBase;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.HitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 19:58
 **/
@RestController
@RequestMapping("/hit")
public class HitController {

    @Autowired
    private HitService service;

    /**
     * 添加点击记录(流量监控？)
     * @param hitBase
     * @return
     */
    @PostMapping("/add")
    MyResult add(@RequestBody HitBase hitBase){
        service.addPicHit(hitBase);
        return new MyResult(ResultCode.SUCCESS);
    }

    @GetMapping("/count")
    MyResult count(Integer picId){
        return new MyResult(ResultCode.SUCCESS, service.getPicHitCount(picId));
    }
    @PostMapping("/get")
    MyResult getHit(Integer picId, int pageNum, int count){
        return new MyResult(ResultCode.SUCCESS, service.getPicHitList(picId, pageNum, count));
    }
}
