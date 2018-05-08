package com.epam.project.controller;

import com.epam.project.domain.Invoice;
import com.epam.project.service.IInvoiceServ;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ControllerServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ControllerServlet.class);
    IInvoiceServ invoiceServ;

    @Override
    public void init() throws ServletException {
        super.init();
        invoiceServ = ServiceFactory.getInvoiceService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doTest(req, resp);
    }

    private void doTest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        List<Invoice> invoices = invoiceServ.findAllInvoices();
        log.info(invoices);
        resp.getWriter().print("<body><h1>TEST</h1><br/>");
        for (Invoice invoice : invoices)
            resp.getWriter().print("<body><h1>" + invoice + "</h1><br/>");
        resp.getWriter().print("</body>");
    }
}
