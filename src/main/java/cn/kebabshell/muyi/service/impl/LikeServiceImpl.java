package cn.kebabshell.muyi.service.impl;

import cn.kebabshell.muyi.common.entity.LikeBase;
import cn.kebabshell.muyi.common.entity.UserBase;
import cn.kebabshell.muyi.common.mapper.LikeBaseMapper;
import cn.kebabshell.muyi.common.mapper.UserBaseMapper;
import cn.kebabshell.muyi.service.LikeService;
import cn.kebabshell.muyi.utils.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-31 18:41
 **/
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeBaseMapper likeBaseMapper;
    @Autowired
    private UserBaseMapper userBaseMapper;

    @Override
    public Boolean addLike(LikeBase likeBase, String token) {
        if (token != null) {
            String userName = JWTUtil.getUserName(token);
            Integer userId = likeBase.getLikeUserId();
            QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
            userBaseQueryWrapper
                    .eq("user_id", userId)
                    .eq("user_name", userName);
            UserBase userBase = userBaseMapper.selectOne(userBaseQueryWrapper);
            if (userBase == null)
                return false;
        }
        likeBaseMapper.insert(likeBase);
        return true;
    }

    @Override
    public Boolean cancelLike(LikeBase likeBase, String token) {
        if (token == null)
            return false;
        else {
            String userName = JWTUtil.getUserName(token);
            Integer userId = likeBase.getLikeUserId();
            QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
            userBaseQueryWrapper
                    .eq("user_id", userId)
                    .eq("user_name", userName);
            UserBase userBase = userBaseMapper.selectOne(userBaseQueryWrapper);
            if (userBase == null)
                return false;

            QueryWrapper<LikeBase> likeBaseQueryWrapper = new QueryWrapper<>();
            likeBaseQueryWrapper
                    .eq("like_pic_id", likeBase.getLikePicId())
                    .eq("like_user_id", userBase.getUserId());
            LikeBase got = likeBaseMapper.selectOne(likeBaseQueryWrapper);
            if (got == null)
                return false;
            else
                likeBaseMapper.deleteById(got.getId());
            return true;
        }
    }

    @Override
    public int getLikeCount(Integer picId) {
        QueryWrapper<LikeBase> likeBaseQueryWrapper = new QueryWrapper<>();
        likeBaseQueryWrapper
                .eq("like_pic_id", picId);
        return likeBaseMapper.selectCount(likeBaseQueryWrapper);
    }
}
