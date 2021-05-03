package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.PicBase;
import cn.kebabshell.muyi.common.entity.PicDtl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 19:49
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigPicDTO {
    private PicBase picBase;
    private PicDtl picDtl;
    private String[] sorts;
}
