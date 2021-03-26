package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.UserBase;
import cn.kebabshell.muyi.common.entity.UserDtl;
import lombok.Data;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 18:43
 **/

@Data
public class BigUserDTO {
    private UserBase userBase;
    private UserDtl userDtl;
}
