package io.wisoft.iotplatform.cnc.configurer;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                       final FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
  }

}
