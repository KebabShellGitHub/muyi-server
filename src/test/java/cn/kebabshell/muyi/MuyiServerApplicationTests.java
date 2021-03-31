package cn.kebabshell.muyi;

import cn.kebabshell.muyi.common.dto.BigUserDTO;
import cn.kebabshell.muyi.common.dto.HotHitDTO;
import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.common.entity.PicCategory;
import cn.kebabshell.muyi.common.entity.UserBase;
import cn.kebabshell.muyi.common.entity.UserDtl;
import cn.kebabshell.muyi.common.mapper.AuthUserMapper;
import cn.kebabshell.muyi.common.mapper.HitBaseMapper;
import cn.kebabshell.muyi.common.mapper.PicCategoryMapper;
import cn.kebabshell.muyi.common.mapper.UserBaseMapper;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import cn.kebabshell.muyi.service.UserService;
import cn.kebabshell.muyi.utils.ThumbnailsUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
@MapperScan("cn/kebabshell/muyi/common/mapper")
class MuyiServerApplicationTests {

    @Autowired
    private AuthUserMapper mapper;

    @Autowired
    private UserService service;
    @Autowired
    private HitBaseMapper hitBaseMapper;
    @Autowired
    private PicCategoryMapper picCategoryMapper;

    @Transactional(rollbackFor = Exception.class)
    void SQLInsertTest() {
        AuthUser user = new AuthUser();
        user.setUserName("test2");
        user.setUserPassword("test");
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        mapper.insert(user);
    }

    @Test
    void test() {
        try {
            SQLInsertTest();
        }catch (Exception e){
            System.out.println("error");
        }
    }

    @Test
    void test2() {
        AuthUser user = new AuthUser();
        LocalDateTime now = LocalDateTime.now();
        user.setUserName("test");
        user.setUserPassword("test");
        user.setGmtCreate(now);
        user.setGmtModified(now);
        UserBase userBase = new UserBase();
        userBase.setUserName("test");
        userBase.setGmtCreate(now);
        userBase.setGmtModified(now);
        userBase.setUserAvatarThumbUrl("#");
        UserDtl userDtl = new UserDtl();
        userDtl.setUserAvatarUrl("#");
        userDtl.setGmtCreate(now);
        userDtl.setGmtModified(now);
        BigUserDTO bigUserDTO = new BigUserDTO();
        bigUserDTO.setAuthUser(user);
        bigUserDTO.setUserBase(userBase);
        bigUserDTO.setUserDtl(userDtl);

        try {
            service.register(bigUserDTO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(-1);
        } finally {
            System.out.println(1);
        }
    }

    @Test
    void test3(){
        AuthUser authUser = new AuthUser();
        authUser.setUserName("bbb");
        UpdateWrapper<AuthUser> authUserUpdateWrapper = new UpdateWrapper<>();
        authUserUpdateWrapper.eq("id", 1006);
        mapper.update(authUser, authUserUpdateWrapper);
    }

    @Test
    void test4(){
        Page<HotHitDTO> hotHit = hitBaseMapper.getHotHit(new Page<>(1, 1));
        hotHit.getRecords().forEach(System.out::println);
    }

    @Test
    void test5(){
        QueryWrapper<PicCategory> picCategoryQueryWrapper = new QueryWrapper<>();
        picCategoryQueryWrapper
                .select("pic_id")
                .in("category_name", new String[]{"t1", "t2"})
                .groupBy("pic_id")
                .having("count(*) = 2");

        picCategoryMapper.selectList(picCategoryQueryWrapper).forEach(System.out::println);
    }

    @Test
    void test6(){
        try {
            Thumbnails.of("/E:/m.jpg").scale(0.5).toFile("/E:/mm.jpg");
            ThumbnailsUtil.watermark("/E:/mm.jpg", Positions.BOTTOM_RIGHT, "/E:/watermark.png", 0.5f, "/E:/mm.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test7(){
        AuthUser authUser = new AuthUser();
        authUser.setUserName("test");
        MyResult myResult = new MyResult(ResultCode.SUCCESS, authUser);
        System.out.println(myResult.getData().toString());
    }

}
