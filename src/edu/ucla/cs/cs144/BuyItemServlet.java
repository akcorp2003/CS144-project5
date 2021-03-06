package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
			String itemid = request.getParameter("buyingitemID");
			HttpSession my_sesh = request.getSession(true);
			
			my_sesh.setAttribute("item_bought", itemid);
			
			Map<String, ArrayList<String>> buying_items = (HashMap<String, ArrayList<String>>) my_sesh.getAttribute("sesh_itemmap");
			if(buying_items.containsKey(itemid)){
				ArrayList<String> item_properties = (ArrayList<String>) buying_items.get(itemid);
				request.setAttribute("id", item_properties.get(0));
				request.setAttribute("item_name", item_properties.get(1));
				request.setAttribute("buy_price", item_properties.get(2));
				request.setAttribute("seller_id", item_properties.get(3));
				
				request.getRequestDispatcher("/purchaseitem.jsp").forward(request, response);
			}
			
			
    		
    		
		} catch(Exception e){
    		request.setAttribute("error", e.getMessage());
    		request.getRequestDispatcher("/item.jsp").forward(request, response);
    	}
		
		
    }
	

}
