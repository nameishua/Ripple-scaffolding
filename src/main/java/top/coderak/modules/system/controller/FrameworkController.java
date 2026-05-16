package top.coderak.modules.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.api.ApiResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Framework metadata and readiness endpoint.
 */
@RestController
@RequestMapping("/framework")
public class FrameworkController {

    @Value("${spring.application.name:ripple}")
    private String applicationName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @GetMapping("/info")
    public ApiResult<Map<String, Object>> info() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("name", applicationName);
        data.put("profile", activeProfile);
        data.put("springBoot", SpringBootVersion.getVersion());
        data.put("springFramework", SpringVersion.getVersion());
        data.put("baseline", "modernized-ripple");
        data.put("goals", new String[]{"secure-by-default", "plugin-ready", "code-generator-ready"});
        return ApiResult.ok(data);
    }
}
