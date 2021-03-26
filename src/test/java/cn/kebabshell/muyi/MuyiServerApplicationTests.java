package cn.kebabshell.muyi;

import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.common.mapper.AuthUserMapper;
import cn.kebabshell.muyi.common.mapper.HitBaseMapper;
import cn.kebabshell.muyi.common.mapper.UserBaseMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
@MapperScan("cn/kebabshell/muyi/common/mapper")
class MuyiServerApplicationTests {

    @Autowired
    private AuthUserMapper mapper;
    @Test
    void SQLInsertTest() {
        AuthUser user = new AuthUser();
        user.setUserName("test");
        user.setUserPassword("test");
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        mapper.insert(user);
    }

}
