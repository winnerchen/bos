package cn.itcast.bos.realm;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.impl.FacadeService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hasee on 2017/7/30.
 */
public class BosRealm extends AuthorizingRealm {
    @Autowired
    private FacadeService facadeService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----开始认证-----");
        UsernamePasswordToken myToken = (UsernamePasswordToken) authenticationToken;
        User existUser = facadeService.getUserService().findUserByEmail(myToken.getUsername());
        if (existUser == null) {
            return null;
        } else {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(existUser, existUser
                    .getPassword(), super.getName());
            return info;
        }
    }
}
