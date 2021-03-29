package cn.kebabshell.muyi.service.impl;

import cn.kebabshell.muyi.common.dto.*;
import cn.kebabshell.muyi.common.entity.*;
import cn.kebabshell.muyi.common.mapper.*;
import cn.kebabshell.muyi.config.MyStaticConfig;
import cn.kebabshell.muyi.service.PicService;
import cn.kebabshell.muyi.utils.FileSave;
import cn.kebabshell.muyi.utils.ThumbnailsUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-29 14:57
 **/

public class PicServiceImpl implements PicService {
    private static Logger log = LoggerFactory.getLogger(Exception.class);
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private PicBaseMapper picBaseMapper;
    @Autowired
    private PicDtlMapper picDtlMapper;
    @Autowired
    private PicCarouselMapper picCarouselMapper;
    @Autowired
    private HitBaseMapper hitBaseMapper;
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private CommentBaseMapper commentBaseMapper;
    @Autowired
    private LikeBaseMapper likeBaseMapper;
    @Autowired
    private PicCategoryMapper picCategoryMapper;
    @Autowired
    private CategoryBaseMapper categoryBaseMapper;

    @Override
    public List<PicCarousel> getCarouselPic(int count) {
        return picCarouselMapper.selectList(null);
    }

    @Override
    public List<SmallPicDTO> getRecommendPic(int count) {
        return null;
    }

    @Override
    public List<SmallPicDTO> getSortPic(String[] sortName, int pageNum, int count) {
        if (sortName.length == 0){
            return getAllPic(pageNum, count);
        }
        LinkedList<SmallPicDTO> res = new LinkedList<>();
        QueryWrapper<PicCategory> picCategoryQueryWrapper = new QueryWrapper<>();
        picCategoryQueryWrapper
                .select("pic_id")
                .in("category_name", sortName)
                .groupBy("pic_id")
                .having("count(*) = " + sortName.length);
        List<PicCategory> records = picCategoryMapper.selectPage(new Page<>(pageNum, count), picCategoryQueryWrapper).getRecords();
        for (PicCategory record : records) {
            Integer picId = record.getPicId();
            PicBase picBase = picBaseMapper.selectById(picId);

            // 拿到摄影师的信息
            Integer authorId = picBase.getPicAuthorId();
            UserBase userBase = getUser(authorId);

            // 拿到所属分类
            List<CategoryBase> categoryBaseList = getCategoryList(picId);

            // 加入结果
            res.add(new SmallPicDTO(
                    picBase,
                    userBase,
                    getPicStatistic(picId),
                    categoryBaseList
            ));
        }
        return res;
    }

    @Override
    public List<SmallPicDTO> getHotPic(int pageNum, int count) {
        // cache
        String hotPicStr;
        if ((hotPicStr = template.opsForValue().get("list:pic:hot:" + pageNum + ":" + count)) != null) {
            return JSONObject.parseArray(hotPicStr, SmallPicDTO.class);
        }
        // no cache
        // 拿到热门图片基本数据（图片id）
        List<HotHitPicDTO> records = hitBaseMapper.getHotPicHit(new Page<>(pageNum, count)).getRecords();
        LinkedList<SmallPicDTO> res = new LinkedList<>();

        // 遍历构建对象
        for (HotHitPicDTO record : records) {
            Integer picId = record.getPid();
            // 点击数量
            Integer hitCount = record.getNum();
            // Got
            PicBase picBase = picBaseMapper.selectById(picId);
            // 只显示没有被删除的图片
            if (!picBase.getIsDeleted()) {
                // 拿到摄影师的信息
                Integer authorId = picBase.getPicAuthorId();
                UserBase userBase = getUser(authorId);

                // 拿到like数量
                Integer likeCount = getLikeCount(picId);

                // 拿到comment数量
                Integer commentCount = getCommentCount(picId);

                // 拿到所属分类
                List<CategoryBase> categoryBaseList = getCategoryList(picId);

                // 加入结果
                res.add(new SmallPicDTO(
                        picBase,
                        userBase,
                        new PicStatisticDTO(hitCount,
                                likeCount,
                                commentCount),
                        categoryBaseList
                ));
            }
        }
        // add cache
        template.opsForValue().set(
                "list:pic:hot:" + pageNum + ":" + count,
                JSON.toJSONString(res), 300,
                TimeUnit.SECONDS);

        log.info("拿到热门图片（卡片数据）:List<SmallPicDTO>");
        return res;
    }

    @Override
    public List<SmallPicDTO> getAllPic(int pageNum, int count) {
        // cache
        String hotPicStr;
        if ((hotPicStr = template.opsForValue().get("list:pic:all:" + pageNum + ":" + count)) != null) {
            return JSONObject.parseArray(hotPicStr, SmallPicDTO.class);
        }
        // no cache
        // 拿到热门图片基本数据（图片id）
        LinkedList<SmallPicDTO> res = new LinkedList<>();
        QueryWrapper<PicBase> picBaseQueryWrapper = new QueryWrapper<>();
        picBaseQueryWrapper
                .eq("is_deleted", false)
                .orderByDesc("gmt_create");
        List<PicBase> records = picBaseMapper.selectPage(new Page<>(pageNum, count), picBaseQueryWrapper).getRecords();

        // 遍历构建对象
        for (PicBase picBase : records) {
            Integer picId = picBase.getId();

            // 拿到摄影师的信息
            Integer authorId = picBase.getPicAuthorId();
            UserBase userBase = getUser(authorId);


            // 拿到所属分类
            List<CategoryBase> categoryBaseList = getCategoryList(picId);

            // 加入结果
            res.add(new SmallPicDTO(
                    picBase,
                    userBase,
                    getPicStatistic(picId),
                    categoryBaseList
            ));
        }
        // add cache
        template.opsForValue().set(
                "list:pic:all:" + pageNum + ":" + count,
                JSON.toJSONString(res), 300,
                TimeUnit.SECONDS);

        log.info("拿到pageNum:" + pageNum + ",count:" + count + "所有图片（卡片数据）:List<SmallPicDTO>");
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addPic(BigPicDTO bigPicDTO, MultipartFile file) {
        String picFileName = FileSave.save(file, MyStaticConfig.FOLDER_PIC);
        ThumbnailsUtil.changeScale(
                MyStaticConfig.DIR + MyStaticConfig.FOLDER_PIC + picFileName,
                0.5,
                MyStaticConfig.DIR + MyStaticConfig.FOLDER_PIC_THUMB + picFileName);
        PicBase picBase = bigPicDTO.getPicBase();
        picBase.setPicThumbUrl(MyStaticConfig.FOLDER_PIC_THUMB + picFileName);
        PicDtl picDtl = bigPicDTO.getPicDtl();
        picDtl.setPicUrl(MyStaticConfig.FOLDER_PIC + picFileName);
        picBaseMapper.insert(picBase);
        picDtlMapper.insert(picDtl);

        return true;
    }

    @Override
    public Boolean delPic(String token, Long picId) {
        return null;
    }

    private PicStatisticDTO getPicStatistic(Integer picId){
        Integer hitCount = getHitCount(picId);
        Integer likeCount = getLikeCount(picId);
        Integer commentCount = getCommentCount(picId);
        return new PicStatisticDTO(hitCount, likeCount, commentCount);
    }

    private int getLikeCount(Integer picId){
        // 拿到like数量
        QueryWrapper<LikeBase> likeBaseQueryWrapper = new QueryWrapper<>();
        likeBaseQueryWrapper.eq("like_pic_id", picId);
        return likeBaseMapper.selectCount(likeBaseQueryWrapper);
    }
    private int getCommentCount(Integer picId){
        // 拿到comment数量
        QueryWrapper<CommentBase> commentBaseQueryWrapper = new QueryWrapper<>();
        commentBaseQueryWrapper.eq("comment_pic_id", picId);
        return commentBaseMapper.selectCount(commentBaseQueryWrapper);
    }
    private int getHitCount(Integer picId){
        // 点击数量
        QueryWrapper<HitBase> hitBaseQueryWrapper = new QueryWrapper<>();
        hitBaseQueryWrapper.eq("hit_pic_id", picId);
        return hitBaseMapper.selectCount(hitBaseQueryWrapper);
    }
    private List<CategoryBase> getCategoryList(Integer picId){
        // 拿到所属分类
        QueryWrapper<CategoryBase> categoryBaseQueryWrapper = new QueryWrapper<>();
        categoryBaseQueryWrapper
                .eq("pic_id", picId);
        return categoryBaseMapper.selectList(categoryBaseQueryWrapper);
    }
    private UserBase getUser(Integer userId){
        QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
        userBaseQueryWrapper.eq("user_id", userId);
        return userBaseMapper.selectList(userBaseQueryWrapper).get(0);
    }
}
