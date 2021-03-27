package cn.kebabshell.muyi.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-27 22:54
 **/
@Data
public class HotHitDTO {
    @TableField("uid")
    private Integer uid;
    @TableField("num")
    private Integer num;
}
