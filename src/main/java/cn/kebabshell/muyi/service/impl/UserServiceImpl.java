package cn.kebabshell.muyi.service.impl;

import cn.kebabshell.muyi.common.dto.BigUserDTO;
import cn.kebabshell.muyi.common.dto.CtrlServiceDTO;
import cn.kebabshell.muyi.common.dto.HotHitDTO;
import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.common.entity.UserBase;
import cn.kebabshell.muyi.common.entity.UserDtl;
import cn.kebabshell.muyi.common.mapper.AuthUserMapper;
import cn.kebabshell.muyi.common.mapper.HitBaseMapper;
import cn.kebabshell.muyi.common.mapper.UserBaseMapper;
import cn.kebabshell.muyi.common.mapper.UserDtlMapper;
import cn.kebabshell.muyi.service.UserService;
import cn.kebabshell.muyi.utils.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-27 15:49
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private UserDtlMapper userDtlMapper;
    @Autowired
    private AuthUserMapper authUserMapper;
    @Autowired
    private HitBaseMapper hitBaseMapper;
    @Autowired
    private StringRedisTemplate template;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(BigUserDTO bigUserDTO) {
        // 拿到三个user信息
        UserBase userBase = bigUserDTO.getUserBase();
        UserDtl userDtl = bigUserDTO.getUserDtl();
        AuthUser authUser = bigUserDTO.getAuthUser();
        // 先插入用户权限表，拿到id
        authUserMapper.insert(authUser);
        Integer userId = authUser.getId();
        // 赋值id
        userBase.setUserId(userId);
        userDtl.setUserId(userId);
        // 插入另外两个用户表
        userBaseMapper.insert(userBase);
        userDtlMapper.insert(userDtl);

        // 拿到用户名，生成token，返回，事务异常上层接
        String userName = authUser.getUserName();
        return JWTUtil.createToken(userName);
    }

    @Override
    public CtrlServiceDTO<String> login(AuthUser authUser) {
        // 查用户
        QueryWrapper<AuthUser> authUserQueryWrapper = new QueryWrapper<>();
        authUserQueryWrapper.eq("user_name", authUser.getUserName());
        AuthUser gotUser = authUserMapper.selectList(authUserQueryWrapper).get(0);

        // 用户不存在
        if (gotUser == null)
            return new CtrlServiceDTO<>(false, "用户不存在", null);
            // 密码错误
        else if (!authUser.getUserPassword().equals(gotUser.getUserPassword()))
            return new CtrlServiceDTO<>(false, "密码错误", null);
        else if (gotUser.getIsBenning())
            return new CtrlServiceDTO<>(false, "已封禁", null);
        // 正确，返回token
        return new CtrlServiceDTO<>(true, "", JWTUtil.createToken(gotUser.getUserName()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(BigUserDTO bigUserDTO, MultipartFile file, String token) {
        String userName = JWTUtil.getUserName(token);
        // 验证用户
        // 根据id拿到用户名，验证token
        // ...

        //保存图片
        if (file != null) {
            // 保存缩略图和原图
            String avatarThumb = "#";
            String avatar = "#";
            bigUserDTO.getUserBase().setUserAvatarThumbUrl(avatarThumb);
            bigUserDTO.getUserDtl().setUserAvatarUrl(avatar);
        }

        // 更新三个表
        Integer userId = bigUserDTO.getAuthUser().getId();
        authUserMapper.updateById(bigUserDTO.getAuthUser());

        UpdateWrapper<UserBase> userBaseUpdateWrapper = new UpdateWrapper<>();
        userBaseUpdateWrapper.eq("user_id", userId);
        userBaseMapper.update(bigUserDTO.getUserBase(), userBaseUpdateWrapper);

        UpdateWrapper<UserDtl> userDtlUpdateWrapper = new UpdateWrapper<>();
        userDtlUpdateWrapper.eq("userId", userId);
        userDtlMapper.update(bigUserDTO.getUserDtl(), userDtlUpdateWrapper);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean del(Integer delId) {
        // auth_user
        authUserMapper.deleteById(delId);
        // user_base
        QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
        userBaseQueryWrapper.eq("user_id", delId);
        userBaseMapper.delete(userBaseQueryWrapper);
        // user_dtl
        QueryWrapper<UserDtl> userDtlQueryWrapper = new QueryWrapper<>();
        userDtlQueryWrapper.eq("user_id", delId);
        userDtlMapper.delete(userDtlQueryWrapper);

        // delete pics 待定
        // ...
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean ben(Integer benId) {
        LocalDateTime now = LocalDateTime.now();

        UpdateWrapper<AuthUser> authUserUpdateWrapper = new UpdateWrapper<>();
        authUserUpdateWrapper
                .eq("id", benId)
                .set("is_benning", true)
                .set("gmt_modified", now);
        authUserMapper.delete(authUserUpdateWrapper);

        UpdateWrapper<UserBase> userBaseUpdateWrapper = new UpdateWrapper<>();
        userBaseUpdateWrapper
                .eq("user_id", benId)
                .set("is_benning", true)
                .set("gmt_modified", now);
        userBaseMapper.delete(userBaseUpdateWrapper);

        return true;
    }

    @Override
    public CtrlServiceDTO<BigUserDTO> getUser(Integer userId, String token) {
        UserBase userBase;
        UserDtl userDtl;

        // 以有没有userId为准


        if (userId == null) {
            // 如果是自己
            String userName = JWTUtil.getUserName(token);

            QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
            userBaseQueryWrapper
                    .eq("user_name", userName);
            userBase = userBaseMapper.selectList(userBaseQueryWrapper).get(0);

            // 有封禁或删除信息，直接返回信息
            if (userBase.getIsBenning() || userBase.getIsDeleted())
                return new CtrlServiceDTO<>(false, "你已被封禁", null);


            QueryWrapper<UserDtl> userDtlQueryWrapper = new QueryWrapper<>();
            userDtlQueryWrapper
                    .eq("user_name", userBase.getUserName());
            userDtl = userDtlMapper.selectList(userDtlQueryWrapper).get(0);

            return new CtrlServiceDTO<>(true, "", new BigUserDTO(null, userBase, userDtl));

        } else {
            // 如果是别人
            QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
            userBaseQueryWrapper
                    .eq("user_id", userId);
            userBase = userBaseMapper.selectList(userBaseQueryWrapper).get(0);

            // 有封禁或删除信息，直接返回信息
            if (userBase.getIsBenning() || userBase.getIsDeleted())
                return new CtrlServiceDTO<>(false, "此用户已被封禁或删除", null);

            QueryWrapper<UserDtl> userDtlQueryWrapper = new QueryWrapper<>();
            userDtlQueryWrapper
                    .eq("user_id", userId);
            userDtl = userDtlMapper.selectList(userDtlQueryWrapper).get(0);

            return new CtrlServiceDTO<>(true, "", new BigUserDTO(null, userBase, userDtl));
        }
    }

    @Override
    public List<UserBase> getHotAuthor(int count) {
        String listStr;
        if ((listStr = template.opsForValue().get("list:author:hot")) != null){
            return JSONObject.parseArray(listStr, UserBase.class);
        }
        LinkedList<UserBase> userBases = new LinkedList<>();
        List<HotHitDTO> list = hitBaseMapper.getHotHit(new Page<>(1, count)).getRecords();
        for (HotHitDTO hit : list) {
            QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
            userBaseQueryWrapper.eq("user_id", hit.getUid());
            userBases.push(userBaseMapper.selectList(userBaseQueryWrapper).get(0));
        }
        template.opsForValue().set("list:author:hot", JSON.toJSONString(userBases), 300, TimeUnit.SECONDS);
        return userBases;
    }

}
