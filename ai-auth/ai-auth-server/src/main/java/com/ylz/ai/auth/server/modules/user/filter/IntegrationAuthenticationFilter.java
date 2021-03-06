package com.ylz.ai.auth.server.modules.user.filter;

import com.ylz.ai.auth.server.modules.user.authenticator.IntegrationAuthenticator;
import com.ylz.ai.auth.server.modules.user.entity.IntegrationAuthentication;
import com.ylz.ai.auth.server.modules.user.entity.IntegrationAuthenticationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @Description 集成请求过滤器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:38
 */
@Component
public class IntegrationAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {
    private static final String AUTH_TYPE_PARAM_NAME = "auth_type";
    private static final String OAUTH_TOKEN_URL = "/oauth/token";

    private Collection<IntegrationAuthenticator> authenticators;
    private ApplicationContext applicationContext;
    private RequestMatcher requestMatcher;

    public IntegrationAuthenticationFilter() {
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST")
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (requestMatcher.matches(request)) {
            //设置集成登录信息
            IntegrationAuthentication integrationAuthentication = new IntegrationAuthentication();
            integrationAuthentication.setAuthType(request.getParameter(AUTH_TYPE_PARAM_NAME));
            integrationAuthentication.setAuthParameters(request.getParameterMap());
            IntegrationAuthenticationContext.set(integrationAuthentication);
            try {
                //预处理
                this.prepare(integrationAuthentication, request, response);

                filterChain.doFilter(request, response);

                //后置处理
                this.complete(integrationAuthentication);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } finally {
                IntegrationAuthenticationContext.clear();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * @Description 进行预处理
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:38
     */
    private void prepare(IntegrationAuthentication integrationAuthentication, HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException {
        //延迟加载认证器
        if (this.authenticators == null) {
            synchronized (this) {
                Map<String, IntegrationAuthenticator> integrationAuthenticatorMap = applicationContext.getBeansOfType(IntegrationAuthenticator.class);
                if (integrationAuthenticatorMap != null) {
                    this.authenticators = integrationAuthenticatorMap.values();
                }
            }
        }
        if (this.authenticators == null) {
            this.authenticators = new ArrayList<>();
        }
        for (IntegrationAuthenticator authenticator : authenticators) {
            if (authenticator.support(integrationAuthentication)) {
                authenticator.prepare(integrationAuthentication, request, response);
            }
        }
    }

    /**
     * @Description 后置处理
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:38
     */
    private void complete(IntegrationAuthentication integrationAuthentication) {
        for (IntegrationAuthenticator authenticator : authenticators) {
            if (authenticator.support(integrationAuthentication)) {
                authenticator.complete(integrationAuthentication);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
