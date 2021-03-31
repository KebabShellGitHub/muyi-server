package cn.kebabshell.muyi.handler;

import cn.kebabshell.muyi.handler.exception.MyAuthException;
import cn.kebabshell.muyi.handler.exception.MyTokenExpiredException;
import cn.kebabshell.muyi.handler.exception.MyUserEffectiveException;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by KebabShell
 * on 2020/4/20 下午 11:22
 *
 * 自定义异常处理类
 *
 */
@ControllerAdvice
public class MyExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(value = MyTokenExpiredException.class)
    @ResponseBody
    public MyResult noToken(HttpServletRequest request, HttpServletResponse response, MyTokenExpiredException e){
        log.warn(e.getMessage());
        return new MyResult(ResultCode.TOKEN_EXPIRED);
    }
    @ExceptionHandler(value = ExpiredJwtException.class)
    @ResponseBody
    public MyResult noToken1(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException e){
        log.warn(e.getMessage());
        return new MyResult(ResultCode.TOKEN_EXPIRED);
    }
    @ExceptionHandler(value = MyUserEffectiveException.class)
    @ResponseBody
    public MyResult illegalUser(HttpServletRequest request, HttpServletResponse response, MyUserEffectiveException e){
        log.warn(e.getMessage());
        return new MyResult(ResultCode.ILLEGAL_USER);
    }
    @ExceptionHandler(value = MyAuthException.class)
    @ResponseBody
    public MyResult noAuth(HttpServletRequest request, HttpServletResponse response, MyAuthException e){
        log.warn(e.getMessage());
        return new MyResult(ResultCode.NO_AUTHORITY);
    }
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public MyResult auth(HttpServletRequest request, HttpServletResponse response, AuthorizationException e){
        log.warn(e.getMessage());
        return new MyResult(ResultCode.NO_AUTHORITY);
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public MyResult common(HttpServletRequest request, HttpServletResponse response, Exception e){
        log.warn(e.getMessage());
        return new MyResult(ResultCode.ERROR);
    }
}
