package top.coderak.core.base.config;

import top.coderak.core.utils.MD5Utils;
import top.coderak.entity.User;
import top.coderak.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    ShiroConfig shiroConfig;

    @Autowired
    private UserMapper userMapper;

    @SuppressWarnings("unused")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Set<String> stringSet = new HashSet<>();

        stringSet.add("user:show");

        stringSet.add("user:admin");

        info.setStringPermissions(stringSet);

        return info;
    }

    /**
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        System.out.println("-------身份认证方法--------");

        String account = (String) authenticationToken.getPrincipal();

        String userPwd = new String((char[]) authenticationToken.getCredentials());

        // 根据用户名从数据库获取密码
        User user = new User();

        if (account == null) {

            throw new AccountException("用户名不正确");
        } else {

            user = userMapper.selectByAccount(account);

            System.out.println(user);

            String tempString = MD5Utils.string2MD5(userPwd);

            String password = user.getPassword();

            System.out.println(tempString);

            System.out.println(password);

            if (!tempString.equals(password)) {

                System.out.println("$$$$$$$$$");

                throw new AccountException("密码不正确");
            }
        }

        return new SimpleAuthenticationInfo(account, userPwd, getName());
    }

    @Bean
    public CustomRealm customRealm() {

        CustomRealm customRealm = new CustomRealm();

        // 告诉realm,使用credentialsMatcher加密算法类来验证密文
        customRealm.setCredentialsMatcher(shiroConfig.hashedCredentialsMatcher());

        customRealm.setCachingEnabled(false);

        return customRealm;
    }

}
