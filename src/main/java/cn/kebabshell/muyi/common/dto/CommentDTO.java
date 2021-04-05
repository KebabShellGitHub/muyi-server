package cn.kebabshell.muyi.common.dto;

import cn.kebabshell.muyi.common.entity.CommentBase;
import cn.kebabshell.muyi.common.entity.UserBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: muyi-server
 * @description: 评论的展示
 * @author: KebabShell
 * @create: 2021-04-05 22:30
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private CommentBase commentBase;
    private UserBase userBase;
}
