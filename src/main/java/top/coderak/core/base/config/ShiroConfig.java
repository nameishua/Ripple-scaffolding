package top.coderak.core.base.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login/check");

        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new JWTFilter());
        filters.put("cors", new CorsFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // Public endpoints
        filterChainDefinitionMap.put("/login/**", "anon,cors");
        filterChainDefinitionMap.put("/framework/**", "anon,cors");
        filterChainDefinitionMap.put("/actuator/**", "anon,cors");

        filterChainDefinitionMap.put("/swagger-ui.html", "anon,cors");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon,cors");
        filterChainDefinitionMap.put("/v2/api-docs", "anon,cors");
        filterChainDefinitionMap.put("/webjars/**", "anon,cors");

        filterChainDefinitionMap.put("/index.html", "anon,cors");
        filterChainDefinitionMap.put("/js/**", "anon,cors");
        filterChainDefinitionMap.put("/css/**", "anon,cors");
        filterChainDefinitionMap.put("/images/**", "anon,cors");

        filterChainDefinitionMap.put("/h2-console/**", "anon,cors");
        filterChainDefinitionMap.put("/druid/**", "anon,cors");

        // Protected endpoints with CORS support
        filterChainDefinitionMap.put("/admin/**", "cors,jwt");
        filterChainDefinitionMap.put("/user/**", "cors,jwt");
        filterChainDefinitionMap.put("/sequence/**", "cors,jwt");

        // Default: CORS + JWT
        filterChainDefinitionMap.put("/**", "cors,jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {

        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();

        defaultSecurityManager.setRealm(customRealm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        defaultSecurityManager.setSubjectDAO(subjectDAO);

        return defaultSecurityManager;
    }

    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

}
