package com.estsoft.blogjpa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class UrlPrintFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("1. UrlPrintFilter init() - {}",filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("1. UrlPrintFilter {}", httpServletRequest.getRequestURL().toString());

        request.setAttribute("traceId",UUID.randomUUID().toString());
        log.info("1. UrlPrintFilter before");
        chain.doFilter(request,response);
        log.info("1. UrlPrintFilter after");
    }

    @Override
    public void destroy() {
       log.info("1. UrlPrintFilter destroy");
    }
}
