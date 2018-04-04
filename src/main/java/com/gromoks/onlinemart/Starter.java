package com.gromoks.onlinemart;

import com.gromoks.onlinemart.web.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {

    public static void main(String[] args) throws Exception {

        ProductsServlet productsServlet = new ProductsServlet();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder(productsServlet), "/products");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
