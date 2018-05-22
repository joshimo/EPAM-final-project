package com.epam.project.taghandler;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ColontitleHandler extends TagSupport {

    private static final Logger log = Logger.getLogger(ColontitleHandler.class);

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<footer>");
            pageContext.getOut().write("<p class='footer'>");
            pageContext.getOut().write("Учебный проект по курсу Java Winter, Киев, 2018");
            pageContext.getOut().write("</p>");
            pageContext.getOut().write("</footer>");
        } catch(IOException ioe) {
            log.error(ioe);
            throw new JspException();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        return SKIP_BODY;
    }
}
