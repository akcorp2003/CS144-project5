package edu.ucla.cs.cs144;

import java.util.ArrayList;

public class Item {
	public String item_id;
	public String name;
	public String location;
	public String country;
	public String lat;
	public String longi;
	public String buy_price;
	public String first_bid;
	public String num_bids;
	public String seller_id;
	public String seller_rating;
	public String started;
	public String ends;
	public String desc;
	public ArrayList<String> categories;
	public ArrayList<Bid> bids;
	
	public Item(){
		categories = new ArrayList<String>();
		bids = new ArrayList<Bid>();
	}
	
	
}
