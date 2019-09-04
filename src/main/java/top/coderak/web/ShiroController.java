package top.coderak.web;

import top.coderak.core.base.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.base.controller.BaseController;


/**
 * UserController
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
@RestController
@RequestMapping(value = "/login")
public class ShiroController extends BaseController {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public void defaultLogin() {

        writeSuccessResult("请登录");
    }


    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void login(@RequestParam("account") String account, @RequestParam("password") String password) {

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();

        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);

        // 执行认证登陆
        try {

            subject.login(token);
        } catch (UnknownAccountException uae) {

            writeSuccessResult("未知账户");
        } catch (IncorrectCredentialsException ice) {

            writeSuccessResult("密码不正确");
        } catch (LockedAccountException lae) {

            writeSuccessResult("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {

            writeSuccessResult("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {

            writeSuccessResult("用户名或密码不正确！");
        }
        if (subject.isAuthenticated()) {

            writeSuccessResult("登录成功" + "username:" + token.getUsername());
        } else {

            token.clear();

            writeSuccessResult("登录失败");
        }
    }

}
