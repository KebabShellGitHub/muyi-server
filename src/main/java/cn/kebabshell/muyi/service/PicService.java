package cn.kebabshell.muyi.service;

import cn.kebabshell.muyi.common.dto.BigPicDTO;
import cn.kebabshell.muyi.common.dto.SimplePicDTO;
import cn.kebabshell.muyi.common.dto.SmallPicDTO;
import cn.kebabshell.muyi.common.entity.PicCarousel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PicService {

    /**
     * 拿到走马灯（幻灯片）的一组图片
     * @param count
     * @return
     */
    List<PicCarousel> getCarouselPic(int count);

    /**
     * 拿到推荐图片卡片信息（推荐算法待定）
     * @param count
     * @return
     */
    List<SmallPicDTO> getRecommendPic(int count);

    /**
     * 拿到某分类下的热门？图片
     * 这个逻辑应该要在前端处理
     * @param sortName 分类名
     * @param pageNum 页码
     * @param count 数量
     * @return
     */
    List<SmallPicDTO> getSortPic(String[] sortName, int pageNum, int count);

    /**
     * 热门图片（组件）卡片信息
     * @param pageNum 页码
     * @param count 数量
     * @return
     */
    List<SmallPicDTO> getHotPic(int pageNum, int count);

    /**
     * 所有图片卡片信息
     * @param pageNum
     * @param count
     * @return
     */
    List<SmallPicDTO> getAllPic(int pageNum, int count);

    /**
     * 添加图片
     * @param bigPicDTO
     * @param file
     * @return
     */
    Boolean addPic(BigPicDTO bigPicDTO, MultipartFile file);

    /**
     * 删除图片（只是把删除字段置为true）
     * @param picId
     * @return
     */
    Boolean delPic(String token, Long picId);
}
