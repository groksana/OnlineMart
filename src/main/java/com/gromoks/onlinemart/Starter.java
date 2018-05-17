package com.gromoks.onlinemart;

import com.gromoks.container.context.ApplicationContext;
import com.gromoks.container.context.ClassPathApplicationContext;
import com.gromoks.onlinemart.web.filter.MdcFilter;
import com.gromoks.onlinemart.web.filter.SecurityFilter;
import com.gromoks.onlinemart.web.filter.UserRoleFilter;
import com.gromoks.onlinemart.web.servlet.*;
import com.gromoks.onlinemart.web.servlet.security.AccessDeniedServlet;
import com.gromoks.onlinemart.web.servlet.security.LoginServlet;
import com.gromoks.onlinemart.web.servlet.security.LogoutServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {

    public static String PORT = "5000";

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathApplicationContext(new String[] {"src/main/resources/context/database-context.xml", "src/main/resources/context/onlinemart-context.xml"});

         // security
        SecurityFilter securityFilter = (SecurityFilter) applicationContext.getBean("securityFilter");
        UserRoleFilter userRoleFilter = (UserRoleFilter) applicationContext.getBean("userRoleFilter");

        //logging
        MdcFilter mdcFilter = (MdcFilter) applicationContext.getBean("mdcFilter");

        // server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder((ProductSearchServlet) applicationContext.getBean("productSearchServlet")), "/products/search");
        context.addServlet(new ServletHolder((ProductsServlet) applicationContext.getBean("productsServlet")), "/products");
        context.addServlet(new ServletHolder((LoginServlet) applicationContext.getBean("loginServlet")), "/login");
        context.addServlet(new ServletHolder((LogoutServlet) applicationContext.getBean("logoutServlet")), "/logout");
        context.addServlet(new ServletHolder((ProductServlet) applicationContext.getBean("productServlet")), "/product/*");
        context.addServlet(new ServletHolder((NewProductServlet) applicationContext.getBean("newProductServlet")), "/newproduct");
        context.addServlet(new ServletHolder((CartAddServlet) applicationContext.getBean("cartAddServlet")), "/cart/*");
        context.addServlet(new ServletHolder((CartServlet) applicationContext.getBean("cartServlet")), "/cart");
        context.addServlet(new ServletHolder((UserServlet) applicationContext.getBean("userServlet")), "/user");
        context.addServlet(new ServletHolder((AssetsServlet) applicationContext.getBean("assetsServlet")), "/assets/*");
        context.addServlet(new ServletHolder((AccessDeniedServlet) applicationContext.getBean("accessDeniedServlet")), "/accessdenied");

        context.addFilter(new FilterHolder(securityFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(securityFilter), "/user/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(securityFilter), "/newproduct/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(mdcFilter), "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(userRoleFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(Integer.parseInt(PORT));
        server.setHandler(context);

        server.start();
    }
}
