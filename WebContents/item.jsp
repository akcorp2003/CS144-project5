<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<%@ page import="edu.ucla.cs.cs144.Item" %>
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<% Item item = (Item) request.getAttribute("item"); 
String itemid = (String) request.getAttribute("id");
String error = (String) request.getAttribute("error");
String empty = (String) request.getAttribute("empty");
String item_loct ="";
String lat = "";
String longi = "";
if(item != null){
	item_loct = item.location + ", " + item.country;
	lat = item.lat;
	longi = item.longi;
}


%>

<html>
<head>
	<title>Item</title>
	<link rel="stylesheet" type="text/css" href="bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="main.css">

	<style>
	.category{
		font-size:11px;
	}
	</style>

	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
	<script type="text/javascript" 
    		src="http://maps.google.com/maps/api/js?key=AIzaSyCv8YhEOYsX2usxs8cQPmfa8TO_CVpbENc"> 
	</script> 
	<script type="text/javascript"> 
		function initialize(){
			geocoder = new google.maps.Geocoder();
			var item_loct = "<%= item_loct %>";
			var latitude = "<%= lat %>";
			var longitude = "<%= longi %>";
			geocoder.geocode( {'address': item_loct}, function(results,status){
				if(status == google.maps.GeocoderStatus.OK){
					if((latitude !== "") && (longitude !== "")){
						var item_coord = new google.maps.LatLng(latitude, longitude);
						var map_settings = {
							zoom: 14,
							center: item_coord,
							mapTypeId: google.maps.MapTypeId.ROADMAP
						};
						var item_map = new google.maps.Map(document.getElementById("my_itemmap"), map_settings);
						item_map.setCenter(item_coord);
						var marker = new google.maps.Marker({
								map: item_map,
								position: item_coord
							}); 
					}
					else {
						var map_settings = {
							zoom: 14,
							center: results[0].geometry.location,
							mapTypeId: google.maps.MapTypeId.ROADMAP
						};
						var item_map = new google.maps.Map(document.getElementById("my_itemmap"), map_settings);
						item_map.setCenter(results[0].geometry.location);
						var marker = new google.maps.Marker({
							map: item_map,
							position: results[0].geometry.location
						}); 
					}
				}
				else{
					var map_settings = {
						zoom: 1,
						center: new google.maps.LatLng(0, 78),
						mapTypeId: google.maps.MapTypeId.ROADMAP
					};
					var world_map = new google.maps.Map(document.getElementById("my_itemmap"), map_settings);
				}
			});
		}
	</script>
	
</head>
<body onload="initialize()">



<div class="wrapper">
	<nav role="navigation" class="navbar navbar-default">
	    <div class="navbar-header">         
	        <a href="#" class="navbar-brand">Project 4</a>
	    </div>
	    <div id="navbarCollapse" class="collapse navbar-collapse">
	        <ul class="nav navbar-nav">
	            <li><a href="/eBay/search">Search</a></li>
	            <li class="active"><a href="#">Items</a></li>
	        </ul>
	    </div>
    </nav>

    <div class="col-xs-12">
    	<form action="/eBay/item" method="GET" id="searchform" autocomplete="off">
			<div class="form-group">
				<div class="form-group">
				<label>Search Items</label>
				<input class="form-control" type="text" name="id" placeholder="Enter the ItemID">
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">Search!</button>
				</div>
			</div>
		</form>

<%
if(empty=="true"){
%>
<p>Please specify an ItemID</p>
<%
}else if(itemid==null){
	%>
	<p>The ItemID you specified was not found</p>
	<%
}else{
%>

<hr/>

		<h3>Item Information</h3>

		<p><strong>ID : </strong> <%=itemid%></p>

		<p><strong>Name: </strong> <%= item.name %></p>
		
		<p><strong>Buy Price: </strong> <%= item.buy_price %>
		<%
			if(item.buy_price != "N/A"){
		%>
			<form action="/eBay/buyitem">
				<button type="submit" class="btn btn-primary">Buy NOW!</button>
			</form>
		<%
			}
		%>
		</p>
		<p><strong>First Bid: </strong> <%= item.first_bid %></p>
		<p><strong>Number of Bids: </strong> <%=item.num_bids %></p>
		<p><strong>Seller ID: </strong> <%= item.seller_id %></p>
		<p><strong>Seller Rating: </strong> <%= item.seller_rating %></p>
		<%
			if(item.started == "" || item.started == null){
		%>
			<p><strong>Started: </strong> N/A</p>
		<%
		}
		else {
		%>
		<p><strong>Started: </strong> <%= item.started %></p>
		<%
		}
		%>
		<%
			if(item.ends == "" || item.ends == null){
		%>
			<p><strong>Ends: </strong> N/A</p>
		<%
		}
		else {
		%>
			<p><strong>Ends: </strong> <%= item.ends %></p>
		<%
		}
		%>
		<p><strong>Description: </strong> <%= item.desc %></p>
		


		<p><strong>Categories: </strong>
		
		<%
			for(int i = 0; i < item.categories.size(); i++){
		%>
			
			<%= (String) item.categories.get(i) %><% if(i < item.categories.size()-1){ %>, <%}%>
		<%
		}
		%>
		</p>

		<p><strong>Location: </strong> <%= item.location %></p>
		<p><strong>Country: </strong> <%= item.country %></p>
		
		
		<div id="my_itemmap" style="width:450px; height:350px"></div>
					


		<hr/>
		<h3>Bid Information</h3>


		<% if(item.bids.size() < 1){ %>
		<p>There are no current bidders for this item.</p>
		<% } else{ %>


		<table class="table table-striped">
		<thead>
			<tr>
			<th>Bidder ID</th><th>Rating</th><th>Location</th><th>Country</th><th>Time</th><th>Amount</th>
			</tr>
		</thead>
		<tbody>
		
		<%
		for(int i = 0; i < item.bids.size(); i++){
		%>
			<tr>
			<td><%= item.bids.get(i).user_id %></td>
			<td><%= item.bids.get(i).rating %></td>
			<td><%= item.bids.get(i).location %></td>
			<td><%= item.bids.get(i).country %></td>
			<td><%= item.bids.get(i).time %></td>
			<td><%= item.bids.get(i).amt %></td>
			</tr>
		<%
		}}
		%>


	<%
	}
	%>
	<br/>
	</div>
</div>
</body>
</html>