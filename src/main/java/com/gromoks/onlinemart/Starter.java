package com.gromoks.onlinemart;

import com.gromoks.onlinemart.dao.config.MyDataSource;
import com.gromoks.onlinemart.dao.jdbc.JdbcProductDao;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.web.filter.MdcFilter;
import com.gromoks.onlinemart.web.filter.SecurityFilter;
import com.gromoks.onlinemart.web.servlet.*;
import com.gromoks.onlinemart.web.servlet.security.LoginServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {

    public static void main(String[] args) throws Exception {
        // common
        SessionStore sessionStore = new SessionStore();
        MyDataSource myDataSource = new MyDataSource();
        JdbcProductDao jdbcProductDao = new JdbcProductDao(myDataSource);
        ProductService productService = new ProductService(jdbcProductDao);

        // servlet
        ProductsServlet productsServlet = new ProductsServlet(productService);
        ProductServlet productServlet = new ProductServlet(productService);
        CartAddServlet cartAddServlet = new CartAddServlet(productService, sessionStore);
        CartServlet cartServlet = new CartServlet(sessionStore);
        AssetsServlet assetsServlet = new AssetsServlet();

        // security
        LoginServlet loginServlet = new LoginServlet(sessionStore);
        SecurityFilter securityFilter = new SecurityFilter(sessionStore);

        //logging
        MdcFilter mdcFilter = new MdcFilter(sessionStore);

        // server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(productServlet), "/product/*");
        context.addServlet(new ServletHolder(cartAddServlet), "/cart/*");
        context.addServlet(new ServletHolder(cartServlet), "/cart");
        context.addServlet(new ServletHolder(assetsServlet), "/assets/*");

        context.addFilter(new FilterHolder(securityFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(mdcFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
