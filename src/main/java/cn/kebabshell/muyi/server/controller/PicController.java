package cn.kebabshell.muyi.server.controller;

import cn.kebabshell.muyi.common.dto.BigPicDTO;
import cn.kebabshell.muyi.common.dto.SimplePicDTO;
import cn.kebabshell.muyi.handler.result.MyMsg;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.utils.FileSave;
import cn.kebabshell.muyi.utils.JWTUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    /**
     * 拿到走马灯（幻灯片）的一组图片
     * @param count 图片数量
     * @return List<SimplePicDTO>
     */
    @GetMapping("/hm/car")
    MyResult getCarouselPic(int count){
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * 拿到home主页热门图片
     * @param count 图片数量
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hm/hot")
    MyResult getHomeHotPic(int count){
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }
    /**
     * 拿到推荐图片（待定，有推荐算法）
     * @param count 图片数量
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hm/rec")
    MyResult getRecommendPic(int count){
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
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * “热门页”，拿到第几页的热门图片
     * @param pageNum 页码
     * @param count 每页有多少个（多次必须一样）
     * @return List<SmallPicDTO>
     */
    @GetMapping("/hot/all")
    MyResult getHotPic(int pageNum, int count){
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * “全部页”，拿到第几页的热门图片
     * @param pageNum 页码
     * @param count 每页有多少个（多次必须一样）
     * @return List<SmallPicDTO>
     */
    @GetMapping("/all")
    MyResult getAllPic(int pageNum, int count){
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }

    /**
     * 添加图片，token
     * @param bigPicDTO
     * @param file
     * @return
     */
    @PostMapping("/add")
    public MyResult addPic(BigPicDTO bigPicDTO, MultipartFile file) {
        FileSave fileSave = new FileSave();
        // String path = fileSave.save(file);
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
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
        if (token == null) {
            return new MyResult(ResultCode.NO_LOGIN, "请重新登录");
        }
        String userName = JWTUtil.getUserName(token);
        //User user = userService.findByName(userName);
        //if (user == null) {
        //    return new MyResult(ResultCode.NO_USER);
        //} else if (!user.getEffective()) {
        //    return new MyResult(ResultCode.ILLEGAL_USER);
        //}
        //return service.deletePic(user.getId(), picId) ?
        //        new MyResult(ResultCode.SUCCESS) : new MyResult(ResultCode.ERROR);
        return new MyResult(
                ResultCode.SUCCESS.getCode(),
                MyMsg.SUCCESS.getMsg(),
                null);
    }






}
