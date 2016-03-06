package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BuyItemServlet extends HttpServlet implements Servlet {
	
	public BuyItemServlet(){
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		try{
    		
			//make two servlets: buyitem and confirmitem
			//or make only one servlet (this one) and use a form with a hidden input to tell this
			//buyitem servlet which .jsp page it should route to
    		
    		
		} catch(Exception e){
    		request.setAttribute("error", e.getMessage());
    		request.getRequestDispatcher("/item.jsp").forward(request, response);
    	}
		
		
    }
	

}
