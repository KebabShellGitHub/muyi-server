package cn.kebabshell.muyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-29 15:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicStatisticDTO {
    private Integer hitCount;
    private Integer likeCount;
    private Integer commentCount;
}
