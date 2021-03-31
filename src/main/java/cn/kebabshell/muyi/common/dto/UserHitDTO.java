package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.HitBase;
import cn.kebabshell.muyi.common.entity.UserBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: muyi-server
 * @description: 这个点击是是谁点的
 * @author: KebabShell
 * @create: 2021-03-31 17:46
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHitDTO {
    private HitBase hitBase;
    private UserBase userBase;
}
