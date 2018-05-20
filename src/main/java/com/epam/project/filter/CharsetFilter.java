package com.epam.project.filter;

import com.epam.project.controller.ControllerServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final Logger log = Logger.getLogger(ControllerServlet.class);

    private String requestEncoding;
    private String responseEncoding;

    public void init(FilterConfig config) throws ServletException {
        requestEncoding = config.getInitParameter("requestEncoding");
        responseEncoding = config.getInitParameter("responseEncoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain net)
         throws IOException, ServletException {
        //log.error("Before - Request encoding: " + request.getCharacterEncoding());
        //log.error("Before - Response encoding: " + response.getCharacterEncoding());
        request.setCharacterEncoding(requestEncoding);
        response.setCharacterEncoding(responseEncoding);
        //log.error("After - Request encoding: " + request.getCharacterEncoding());
        //log.error("After - Response encoding: " + response.getCharacterEncoding());
        net.doFilter(request, response);
    }

    public void destroy(){
    }
}
