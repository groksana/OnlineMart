package com.gromoks.onlinemart;

import com.gromoks.onlinemart.dao.ProductDao;
import com.gromoks.onlinemart.dao.UserDao;
import com.gromoks.onlinemart.dao.config.MyDataSource;
import com.gromoks.onlinemart.dao.jdbc.JdbcProductDao;
import com.gromoks.onlinemart.dao.jdbc.JdbcUserDao;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.service.UserService;
import com.gromoks.onlinemart.service.impl.ProductServiceImpl;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.service.impl.UserServiceImpl;
import com.gromoks.onlinemart.web.filter.MdcFilter;
import com.gromoks.onlinemart.web.filter.SecurityFilter;
import com.gromoks.onlinemart.web.servlet.*;
import com.gromoks.onlinemart.web.servlet.security.LoginServlet;
import com.gromoks.onlinemart.web.servlet.security.LogoutServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {

    public static String PORT = System.getenv("PORT");

    public static void main(String[] args) throws Exception {
        // common
        SessionStore sessionStore = new SessionStore();
        MyDataSource myDataSource = new MyDataSource();
        ProductDao jdbcProductDao = new JdbcProductDao(myDataSource);
        UserDao jdbcUserDao = new JdbcUserDao(myDataSource);
        ProductService productService = new ProductServiceImpl(jdbcProductDao);
        UserService userService = new UserServiceImpl(jdbcUserDao);

        // servlet
        ProductsServlet productsServlet = new ProductsServlet(productService, sessionStore);
        ProductServlet productServlet = new ProductServlet(productService, sessionStore);
        NewProductServlet newProductServlet = new NewProductServlet(productService, sessionStore);
        ProductSearchServlet productSearchServlet = new ProductSearchServlet(productService, sessionStore);
        CartAddServlet cartAddServlet = new CartAddServlet(productService, sessionStore);
        CartServlet cartServlet = new CartServlet(sessionStore);
        UserServlet userServlet = new UserServlet(sessionStore);
        AssetsServlet assetsServlet = new AssetsServlet();

        // security
        LoginServlet loginServlet = new LoginServlet(userService, sessionStore);
        LogoutServlet logoutServlet = new LogoutServlet(sessionStore);
        SecurityFilter securityFilter = new SecurityFilter(sessionStore);

        //logging
        MdcFilter mdcFilter = new MdcFilter(sessionStore);

        // server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder(productSearchServlet), "/products/search");
        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(logoutServlet), "/logout");
        context.addServlet(new ServletHolder(productServlet), "/product/*");
        context.addServlet(new ServletHolder(newProductServlet), "/newproduct");
        context.addServlet(new ServletHolder(cartAddServlet), "/cart/*");
        context.addServlet(new ServletHolder(cartServlet), "/cart");
        context.addServlet(new ServletHolder(userServlet), "/user");
        context.addServlet(new ServletHolder(assetsServlet), "/assets/*");

        context.addFilter(new FilterHolder(securityFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(securityFilter), "/user/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(securityFilter), "/newproduct/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(mdcFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(Integer.parseInt(PORT));
        server.setHandler(context);

        server.start();
    }
}
