package edu.ucla.cs.cs144;

import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
    	
    	String query = request.getParameter("q");
    	String url = "http://google.com/complete/search?output=toolbar&q="+query;
    	
    	URL u = new URL(url);
    	HttpURLConnection conn = (HttpURLConnection)u.openConnection();
    	
    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	String input;
    	StringBuffer buf = new StringBuffer();
    	
    	while((input = reader.readLine()) != null){
    		buf.append(input);
    	}
    	
    	response.setContentType("text/xml");
    	response.getWriter().write(buf.toString());
    }
}
