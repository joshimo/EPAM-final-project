package com.epam.project.controller;

import com.epam.project.commands.ICommand;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.ProductServiceException;
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
    private RequestResolver requestResolver = RequestResolver.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
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
        ICommand command = requestResolver.getCommand(req);
        System.out.println("path info: " + req.getPathInfo());
        System.out.println("method: " + req.getMethod());
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }


    /** Test methods. Shall be deleted */
    private void doTest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        try {
            List<Product> products = ServiceFactory.getProductService().findAllProducts();
            //log.info(products);
            resp.getWriter().print("<body><h1>TEST</h1><br/>");
            for (Product product : products)
                resp.getWriter().print("<body><div>" + product.toString().replace("\n", "<br/>") + "</div><br/>");
            resp.getWriter().print("</body>");
        } catch (ProductServiceException pse) {
            log.info(pse);
        }
    }

    private void redirectToTestPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        try {
            List<Product> products = ServiceFactory.getProductService().findAllProducts();
            req.setAttribute("products", products);
            req.getRequestDispatcher("/pages/main.jsp").forward(req, resp);
        } catch (ProductServiceException pse) {
            log.info(pse);
        }
    }
}