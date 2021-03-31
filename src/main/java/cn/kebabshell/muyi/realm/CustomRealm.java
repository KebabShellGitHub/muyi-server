package cn.kebabshell.muyi.realm;

import cn.kebabshell.muyi.common.entity.*;
import cn.kebabshell.muyi.common.mapper.*;
import cn.kebabshell.muyi.handler.exception.MyNoUserException;
import cn.kebabshell.muyi.handler.exception.MyTokenExpiredException;
import cn.kebabshell.muyi.utils.JWTToken;
import cn.kebabshell.muyi.utils.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by KebabShell
 * on 2020/4/20 下午 09:28
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private AuthUserMapper authUserMapper;
    @Autowired
    private AuthRoleMapper authRoleMapper;
    @Autowired
    private AuthRolePermissionMapper authRolePermissionMapper;
    @Autowired
    private AuthPermissionMapper authPermissionMapper;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private UserDtlMapper userDtlMapper;
    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 设置realm的名字
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("CustomRealm");
    }

    /**
     * 授权
     * <p>
     * 先认证，再授权！！
     * 根据安全数据获取用户的权限/角色
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        //获取安全数据(username)
        String username = JWTUtil.getUserName(principalCollection.toString());

        Set<String> roles = new HashSet<>();

        Set<String> auth = new HashSet<>();
        //拿到用户的角色/权限
        QueryWrapper<AuthUser> authUserQueryWrapper = new QueryWrapper<>();
        authUserQueryWrapper.eq("user_name", username);
        Integer userId = authUserMapper.selectList(authUserQueryWrapper).get(0).getId();
        QueryWrapper<AuthUserRole> authUserRoleQueryWrapper = new QueryWrapper<>();
        authUserRoleQueryWrapper.eq("user_id", userId);
        List<AuthUserRole> authUserRoles = authUserRoleMapper.selectList(authUserRoleQueryWrapper);
        for (AuthUserRole authUserRole : authUserRoles) {
            Integer roleId = authUserRole.getRoleId();
            AuthRole authRole = authRoleMapper.selectById(roleId);
            roles.add(authRole.getRoleName());
            QueryWrapper<AuthRolePermission> authRolePermissionQueryWrapper = new QueryWrapper<>();
            authRolePermissionQueryWrapper.eq("role_id", roleId);
            List<AuthRolePermission> authRolePermissions = authRolePermissionMapper.selectList(authRolePermissionQueryWrapper);
            for (AuthRolePermission authRolePermission : authRolePermissions) {
                auth.add(
                        authPermissionMapper.selectById(authRolePermission.getPermissionId()).getPermissionName()
                );
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.setRoles(roles);
        info.setStringPermissions(auth);
        //返回权限/角色信息，交给shiro判断
        return info;
    }

    /**
     * 认证（登录用）
     * subject.login过来的
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("登录");
        //login传进的token
        String token = (String) authenticationToken.getCredentials();
        //解析拿到username，并查找用户且判断token的合法性
        String username = JWTUtil.getUserName(token);
        QueryWrapper<AuthUser> authUserQueryWrapper = new QueryWrapper<>();
        authUserQueryWrapper.eq("user_name", username);
        List<AuthUser> authUsers = authUserMapper.selectList(authUserQueryWrapper);

        if (authUsers == null) {
            //如果用户不存在,抛异常
            throw new MyNoUserException("找不到此用户");
        } else if (JWTUtil.verify(token)) {
            //如果过期,抛异常
            throw new MyTokenExpiredException("token过期了");
        } else {
            //如果用户存在且token不过期
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
    }
}
