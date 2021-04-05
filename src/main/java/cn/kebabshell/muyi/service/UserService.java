package cn.kebabshell.muyi.service;

import cn.kebabshell.muyi.common.dto.BigUserDTO;
import cn.kebabshell.muyi.common.dto.CtrlServiceDTO;
import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.common.entity.UserBase;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    /**
     * 注册
     * @param bigUserDTO 用户详细信息
     * @return 根据用户名生成并返回 token，失败返回空串
     */
    String register(BigUserDTO bigUserDTO);

    /**
     * 登录
     * @param authUser
     * @return 返回token<String>
     */
    CtrlServiceDTO<String> login(AuthUser authUser);

    /**
     * 更新用户信息
     * @param bigUserDTO
     * @param file
     * @param token
     * @return 成功/失败
     */
    Boolean update(BigUserDTO bigUserDTO, MultipartFile file, String token);

    /**
     * 删除某个用户
     * @param delId 用户id
     * @return
     */
    Boolean del(Integer delId);

    /**
     * 封禁用户，没封图片，减少压力
     * @param benId
     * @return
     */
    Boolean ben(Integer benId);


    // 查

    /**
     * 如果是自己，就不需要id，如果是别人，就要
     * @param userId
     * @param token
     * @return
     */
    CtrlServiceDTO<BigUserDTO> getUser(Integer userId, String token);

    /**
     *
     * @param token
     * @return
     */
    UserBase getUserBase(String token);
    /**
     * 返回主页热门摄影师信息
     * @return
     */
    List<UserBase> getHotAuthor(int count);
}
