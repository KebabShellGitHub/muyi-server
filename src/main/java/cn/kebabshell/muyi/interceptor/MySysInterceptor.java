package cn.kebabshell.muyi.interceptor;

import cn.kebabshell.muyi.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by KebabShell
 * on 2020/5/1 下午 08:08
 */
public class MySysInterceptor implements HandlerInterceptor {

    //@Autowired
    //private SysService service;
    //@Autowired
    //private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 访问者的IP
        String ip = request.getRemoteAddr();
        // 访问地址
        String url = request.getRequestURL().toString();
        //得到用户的浏览器名
//        String browser = BrowserUtil.getOsAndBrowserInfo(request);

        //LogOperation logOperation = new LogOperation();
        //
        //String token = request.getHeader("Token");
        //if (token != null && !token.isEmpty()) {
        //    String userName = JWTUtil.getUserName(token);
        //    Long userId = userService.findByName(userName).getId();
        //    logOperation.setUserId(userId);
        //}
        //
        //logOperation.setUrl(url);
        //logOperation.setIp(ip);
        //logOperation.setCreateTime(new Date());
        //
        //service.addLog(logOperation);

        return true;
    }
}
