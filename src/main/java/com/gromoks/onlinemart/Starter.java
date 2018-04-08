package com.gromoks.onlinemart;

import com.gromoks.onlinemart.dao.config.JdbcConnection;
import com.gromoks.onlinemart.dao.jdbc.JdbcProductDao;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.service.security.SessionStore;
import com.gromoks.onlinemart.web.filter.SecurityFilter;
import com.gromoks.onlinemart.web.servlet.ProductServlet;
import com.gromoks.onlinemart.web.servlet.ProductsServlet;
import com.gromoks.onlinemart.web.servlet.security.LoginServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {

    public static void main(String[] args) throws Exception {

        ProductsServlet productsServlet = new ProductsServlet(initProductService());
        ProductServlet productServlet = new ProductServlet();
        SessionStore sessionStore = new SessionStore();

        LoginServlet loginServlet = new LoginServlet(sessionStore);

        SecurityFilter securityFilter = new SecurityFilter(sessionStore);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(productServlet), "/product/*");

        context.addFilter(new FilterHolder(securityFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }

    private static ProductService initProductService() {
        JdbcConnection jdbcConnection = new JdbcConnection();
        JdbcProductDao jdbcProductDao = new JdbcProductDao(jdbcConnection);
        ProductService productService = new ProductService(jdbcProductDao);
        return productService;
    }
}
