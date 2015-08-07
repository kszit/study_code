package org.stu_webBase.modifyRequet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤掉param值的前后空格
 * @author Administrator
 *
 */
public class ModifyRequestFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		chain.doFilter(new ModifyRequest((HttpServletRequest) request), response);
		
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
