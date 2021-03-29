package cn.kebabshell.muyi.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-29 15:12
 **/
@Data
public class HotHitPicDTO {
    @TableField("pid")
    private Integer pid;
    @TableField("num")
    private Integer num;
}
