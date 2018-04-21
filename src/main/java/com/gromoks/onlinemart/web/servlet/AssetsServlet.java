package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AssetsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String[] uris=req.getRequestURI().split("/");
        String filename = uris[2];

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();

        String page = thymeLeafPageGenerator.getCssPage(filename);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
