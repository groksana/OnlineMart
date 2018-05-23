package com.gromoks.onlinemart.web.servlet.security;

import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;

public class AccessDeniedServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start to process Access Denied Servlet");
        PrintWriter writer = resp.getWriter();

        String addProductState = checkAddProductState(req);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getHtmlPage("accessdenied", map);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
        log.info("Finish to process Access Denied Servlet");
    }
}
