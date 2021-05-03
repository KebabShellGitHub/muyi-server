package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.dto.BigPicDTO;
import cn.kebabshell.muyi.common.dto.BigUserDTO;
import cn.kebabshell.muyi.common.dto.SimplePicDTO;
import cn.kebabshell.muyi.handler.result.MyMsg;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.PicService;
import cn.kebabshell.muyi.utils.FileSave;
import cn.kebabshell.muyi.utils.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: muyi-server
 * @description: 图片
 * @author: KebabShell
 * @create: 2021-03-26 17:48
 **/
@RestController
@RequestMapping("/pic")
public class PicController {
    private static Logger log = LoggerFactory.getLogger(PicController.class);
    @Autowired
    private PicService service;

    @GetMapping("/test")
    MyResult myTest(Integer id){
        return new MyResult(ResultCode.SUCCESS, id);
    }

    @GetMapping("/one")
    MyResult getOne(Integer picId){
        return new MyResult(ResultCode.SUCCESS, service.getOne(picId));
    }

    @GetMapping("/statistic")
    MyResult getPicStatistic(Integer picId){
        return new MyResult(ResultCode.SUCCESS, service.getPicStatisticInfo(picId));
    }
    /**
     * 拿到走马灯（幻灯片）的一组图片
     * @param count 图片数量
     * @return List<SimplePicDTO>
     */
    @GetMapping("/hm/car")
    MyResult getCarouselPic(@RequestParam("count") int count){
        log.info("getCarouselPic:count:" + count);
        return new MyResult(
                ResultCode.SUCCESS,
                service.getCarouselPic(count));
    }

    /**
     * 拿到home主页热门图片
     * @param count 图片数量
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hm/hot")
    MyResult getHomeHotPic(int count){
        log.info("getHomeHotPic:count:" + count);
        return new MyResult(
                ResultCode.SUCCESS,
                service.getHotPic(1, count));
    }
    /**
     * 拿到推荐图片（待定，有推荐算法）
     * @param count 图片数量
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hm/rec")
    MyResult getRecommendPic(int count){
        log.info("getRecommendPic:count:" + count);
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * 拿到一些分类组合的图片
     * @param sortName 分类数组
     * @param pageNum 页码
     * @param count 拿的数目
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hm/sort")
    MyResult getSortPic(String[] sortName, int pageNum, int count){
        log.info("getSortPic");
        return new MyResult(
                ResultCode.SUCCESS,
                service.getSortPic(sortName, pageNum, count));
    }

    /**
     * “热门页”，拿到第几页的热门图片
     * @param pageNum 页码
     * @param count 每页有多少个（多次必须一样）
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hot/all")
    MyResult getHotPic(int pageNum, int count){
        log.info("getHotPic");
        return new MyResult(
                ResultCode.SUCCESS,
                service.getHotPic(pageNum, count));
    }

    /**
     * “全部页”，拿到第几页的热门图片
     * @param pageNum 页码
     * @param count 每页有多少个（多次必须一样）
     * @return List<SmallPicDTO>
     */
    @GetMapping("/all")
    MyResult getAllPic(int pageNum, int count){
        log.info("getAllPic");
        return new MyResult(
                ResultCode.SUCCESS,
                service.getAllPic(pageNum, count));
    }

    /**
     * 添加图片，token
     * @param bigPicDTO
     * @param file
     * @return
     */
    @PostMapping("/add")
    public MyResult addPic(String bigPicDTO, MultipartFile file) {
        BigPicDTO dto = JSONObject.parseObject(bigPicDTO, BigPicDTO.class);
        log.info("addPic:bigPicDTO:" + dto);
        Boolean add = service.addPic(dto, file);
        return add ?
                new MyResult(ResultCode.SUCCESS)
                :
                new MyResult(ResultCode.ERROR);
    }

    @PostMapping("/add/test")
    public MyResult addPicTest(MultipartFile file, String bigPicDTO) {
        BigPicDTO dto = JSONObject.parseObject(bigPicDTO, BigPicDTO.class);
        FileSave.save(file, "pic-test/");
        System.out.println(dto);
        return new MyResult(ResultCode.SUCCESS);
    }

    /**
     * 删除图片，token
     * @param request
     * @param picId
     * @return
     */
    @GetMapping("/del")
    public MyResult delPic(HttpServletRequest request, Long picId) {
        String token = request.getHeader("Token");
        log.info("delPic:picId:" + picId);
        if (token == null) {
            return new MyResult(ResultCode.NO_LOGIN, "请重新登录");
        }
        Boolean del = service.delPic(token, picId);
        if (del){
            return new MyResult(ResultCode.ERROR);
        }else {
            return new MyResult(ResultCode.SUCCESS);
        }
    }

    @GetMapping("/user")
    MyResult getUserPic(Integer userId, int pageNum, int count){
        log.info("getUserPic userId:" + userId + ",pageNum:" + pageNum);
        return new MyResult(ResultCode.SUCCESS, service.getUserPic(userId, pageNum, count));
    }

    @GetMapping("/bg/all")
    MyResult getAllPicForRoot(int pageNum, int count){
        return new MyResult(ResultCode.SUCCESS, service.getAllPic(pageNum, count));
    }

}
