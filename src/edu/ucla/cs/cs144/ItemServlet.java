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

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
    	try{
    		String id = request.getParameter("id");
    		//if no id is provided, the "id" attribute will be null
    		if(id == null || id == ""){
    			request.setAttribute("empty", "true");
    			request.getRequestDispatcher("/item.jsp").forward(request, response);
    			return;
    		}
    		String itemxml = AuctionSearchClient.getXMLDataForItemId(id);
    		

    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = factory.newDocumentBuilder();
   			InputSource is = new InputSource(new StringReader(itemxml));
    		Document doc = builder.parse(is);
    		
    		Item item = new Item();
    		item.item_id = id;
    		item.name = doc.getElementsByTagName("Name").item(0).getTextContent();
    		if(doc.getElementsByTagName("Buy_Price").item(0) != null) {
				item.buy_price = doc.getElementsByTagName("Buy_Price").item(0).getTextContent();
			}
			else{
				item.buy_price = "N/A"; //to avoid warnings on MySQL
			}
    		item.country = doc.getElementsByTagName("Country").item(0).getTextContent();
    		item.location = doc.getElementsByTagName("Location").item(0).getTextContent();
    		
    		Element locs = (Element) doc.getElementsByTagName("Location").item(0);
			if(locs.getAttribute("Latitude") != null){
				item.lat = locs.getAttribute("Latitude");
			}
			String longi = "";
			if(locs.getAttribute("Longitude") != null){
				item.longi = locs.getAttribute("Longitude");
			}
    		
    		item.first_bid = doc.getElementsByTagName("First_Bid").item(0).getTextContent();
    		item.num_bids = doc.getElementsByTagName("Number_of_Bids").item(0).getTextContent();
    		Element n_userid = (Element) doc.getElementsByTagName("Seller").item(0);
			item.seller_id = n_userid.getAttribute("UserID");
    		item.seller_rating = n_userid.getAttribute("Rating");
    		String started = doc.getElementsByTagName("Started").item(0).getTextContent();
    		item.started = started;
    		String ends = doc.getElementsByTagName("Ends").item(0).getTextContent();
    		item.ends = ends;
    		String desc = doc.getElementsByTagName("Description").item(0).getTextContent();
    		item.desc = desc;
    		//add the bids
    		NodeList bidlist = doc.getElementsByTagName("Bid");
    		for(int x = 0; x < bidlist.getLength(); x++){
    			Element bid = (Element) bidlist.item(x);
    			Element bidder  = (Element) bid.getElementsByTagName("Bidder").item(0);
				String bidder_id = bidder.getAttribute("UserID");
				String b_rating = bidder.getAttribute("Rating");
				String s_time = bid.getElementsByTagName("Time").item(0).getTextContent();
				String amt = bid.getElementsByTagName("Amount").item(0).getTextContent();
				String country = bid.getElementsByTagName("Country").item(0).getTextContent();
				String location = bid.getElementsByTagName("Location").item(0).getTextContent();
				
				Bid a_bid = new Bid();
				a_bid.amt = amt;
				a_bid.country = country;
				a_bid.location = location;
				a_bid.rating = b_rating;
				a_bid.time = s_time;
				a_bid.user_id = bidder_id;
				item.bids.add(a_bid);
    		}
    		
    		//add the categories
    		NodeList cats = doc.getElementsByTagName("Category");
    		for(int i = 0; i < cats.getLength(); i++){
    			Element e_cat = (Element) cats.item(i);
    			String cat_txt = e_cat.getTextContent();
    			item.categories.add(cat_txt);
    		}
    		
    		request.setAttribute("id", id);
    		request.setAttribute("item", item);
    		
    		//add information about the item to this session ONLY IF the buy_price is a valid value
    		//this map serves as the "cache" for the session so that it doesn't need to query oak service frequently
    		Map<String, ArrayList<String>> buying_items;
    		
    		if(!item.buy_price.equals("N/A")){
    			 HttpSession my_sesh = request.getSession(true);
    			 
    			 if(my_sesh.getAttribute("sesh_itemmap") == null){
    				 buying_items = new HashMap<>();
    			 }
    			 else{
    				 buying_items = (HashMap<String, ArrayList<String>>) my_sesh.getAttribute("sesh_itemmap");
    			 }
    			 
    			 if(!buying_items.containsKey(id)){
    				 //add the information about the item into the Map
    				 //important info to add: ID, name, buy price, seller ID, description (?)
    				 ArrayList<String> item_info = new ArrayList<String>();
    				 item_info.add(item.item_id);
    				 item_info.add(item.name);
    				 item_info.add(item.buy_price);
    				 item_info.add(item.seller_id);
    				 
    				 buying_items.put(id, item_info);
    				 my_sesh.setAttribute("sesh_itemmap", buying_items);
    			 }
    		}
    		
    		request.getRequestDispatcher("/item.jsp").forward(request, response);
    		
    	} catch(Exception e){
    		request.setAttribute("error", e.getMessage());
    		request.getRequestDispatcher("/item.jsp").forward(request, response);
    	}
    }
}
