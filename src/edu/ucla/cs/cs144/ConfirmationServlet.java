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


public class ConfirmationServlet extends HttpServlet implements Servlet {
	
	public ConfirmationServlet(){}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.isSecure()){
			String itemid = request.getParameter("buyingitemID");
			String credit_num = request.getParameter("cred_num");
			
			//1 is error and -1 is no error
			int cred_err = 1;
			
			//remove all whitespaces
			credit_num = credit_num.replaceAll("\\s+","");
			//check if credit card number is all numbers
			if(credit_num.matches("[0-9]+")){
				cred_err = -1;
			}
			
			HttpSession my_sesh = request.getSession(true);
			
			Map<String, ArrayList<String>> buying_items = (HashMap<String, ArrayList<String>>) my_sesh.getAttribute("sesh_itemmap");
			if(buying_items.containsKey(itemid)){
				ArrayList<String> item_properties = (ArrayList<String>) buying_items.get(itemid);
				request.setAttribute("id", item_properties.get(0));
				request.setAttribute("item_name", item_properties.get(1));
				request.setAttribute("buy_price", item_properties.get(2));
				request.setAttribute("seller_id", item_properties.get(3));
				request.setAttribute("cred_num", credit_num);
				Date curr = new Date();
				String curr_date = curr.toString();
				request.setAttribute("time", curr_date);
				request.setAttribute("cred_err", cred_err);
				
				request.getRequestDispatcher("/confirmitem.jsp").forward(request, response);
			}
		}
	}

}
