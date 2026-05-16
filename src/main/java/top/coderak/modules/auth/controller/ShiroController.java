package top.coderak.modules.auth.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.api.ApiResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication endpoints.
 */
@RestController
@RequestMapping(value = "/login")
public class ShiroController {

    @GetMapping(value = "/check")
    public ApiResult<String> defaultLogin() {
        return ApiResult.fail("Please login first.");
    }

    @PostMapping(value = "/check")
    public ApiResult<Map<String, Object>> login(@RequestParam("account") String account,
                                                @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ApiResult.fail("Unknown account.");
        } catch (IncorrectCredentialsException e) {
            return ApiResult.fail("Incorrect password.");
        } catch (LockedAccountException e) {
            return ApiResult.fail("Account locked.");
        } catch (ExcessiveAttemptsException e) {
            return ApiResult.fail("Too many login attempts.");
        } catch (AuthenticationException e) {
            return ApiResult.fail("Invalid username or password.");
        }

        if (!subject.isAuthenticated()) {
            token.clear();
            return ApiResult.fail("Login failed.");
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("statusCode", "1001");
        data.put("statusName", "Login success");
        data.put("userName", token.getUsername());
        return ApiResult.ok(data);
    }
}
