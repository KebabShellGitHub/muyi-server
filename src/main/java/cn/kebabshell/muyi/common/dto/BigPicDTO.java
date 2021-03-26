package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.PicBase;
import cn.kebabshell.muyi.common.entity.PicDtl;
import lombok.Data;

/**
 * 上传
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-26 19:49
 **/

@Data
public class BigPicDTO {
    private PicBase picBase;
    private PicDtl picDtl;
}
