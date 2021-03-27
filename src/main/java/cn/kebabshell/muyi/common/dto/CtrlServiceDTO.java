package cn.kebabshell.muyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: muyi-server
 * @description: 用于Controller和Service层传输
 * @author: KebabShell
 * @create: 2021-03-27 18:04
 **/
@Data
@AllArgsConstructor
public class CtrlServiceDTO<T> {
    private Boolean success;
    private String msg;
    private T data;
}
