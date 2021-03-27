package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.AuthUser;
import cn.kebabshell.muyi.common.entity.UserBase;
import cn.kebabshell.muyi.common.entity.UserDtl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 18:43
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigUserDTO {
    private AuthUser authUser;
    private UserBase userBase;
    private UserDtl userDtl;
}
