package hello.login.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();


        try {

        } catch (Exception e) {

        } finally {

        }


    }

    @Override
    public void destroy() {
        log.info("destroy");
    }
}
