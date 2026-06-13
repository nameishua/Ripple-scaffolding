package top.coderak.core.base.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import top.coderak.core.utils.JWTUtils;
import top.coderak.core.utils.MD5Utils;
import top.coderak.entity.User;
import top.coderak.mapper.UserMapper;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken || token instanceof UsernamePasswordToken;
    }

    @SuppressWarnings("unused")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Set<String> stringSet = new HashSet<>();

        stringSet.add("user:show");

        stringSet.add("user:admin");

        info.setStringPermissions(stringSet);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        if (authenticationToken instanceof JWTToken) {
            return doJWTAuthentication((JWTToken) authenticationToken);
        } else {
            return doPasswordAuthentication((UsernamePasswordToken) authenticationToken);
        }
    }

    private AuthenticationInfo doJWTAuthentication(JWTToken jwtToken) {
        String token = (String) jwtToken.getCredentials();
        if (!JWTUtils.validateToken(token)) {
            throw new AuthenticationException("Token is invalid");
        }
        String account = JWTUtils.getAccount(token);
        return new SimpleAuthenticationInfo(account, token, getName());
    }

    private AuthenticationInfo doPasswordAuthentication(UsernamePasswordToken token) {
        log.debug("-------身份认证方法--------");

        String account = (String) token.getPrincipal();

        String userPwd = new String((char[]) token.getCredentials());

        User user = new User();

        if (account == null) {

            throw new AccountException("用户名不正确");
        } else {

            user = userMapper.selectByAccount(account);

            log.debug("User: {}", user);

            if (user == null) {
                throw new AccountException("用户不存在");
            }

            String tempString = MD5Utils.string2MD5(userPwd);

            String password = user.getPassword();

            log.debug("Input password hash: {}", tempString);
            log.debug("Stored password: {}", password);

            if (!tempString.equals(password)) {
                throw new AccountException("密码不正确");
            }
        }

        return new SimpleAuthenticationInfo(account, userPwd, getName());
    }
}
