package cn.kebabshell.muyi.common.dto;

import lombok.Data;

/**
 * @program: muyi-server
 * @description: 简化版图片类，只有id和url
 * @author: KebabShell
 * @create: 2021-03-26 17:51
 **/

@Data
public class SimplePicDTO {
    private Integer id;
    private String picUrl;
}
