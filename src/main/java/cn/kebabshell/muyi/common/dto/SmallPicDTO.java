package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.PicBase;
import cn.kebabshell.muyi.common.entity.UserBase;
import lombok.Data;

/**
 * @program: muyi-server
 * @description: 小图组件的内容，缩略图啥的，作者头图等等，点击量，啥的
 * @author: KebabShell
 * @create: 2021-03-26 18:05
 **/
@Data
public class SmallPicDTO {
    private PicBase pic;
    private UserBase author;
    private Integer hitCount;
    private Integer likeCount;
    private Integer CommentCount;
}
