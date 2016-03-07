<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<%@ page import="edu.ucla.cs.cs144.Item" %>
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<% 
String itemid = (String) request.getAttribute("id");
String itemname = (String) request.getAttribute("item_name");
String buy_price = (String) request.getAttribute("buy_price");
String sellerid = (String) request.getAttribute("seller_id");

String encrypttransact_URL = "https://" + request.getServerName() + ":8443" + request.getContextPath() + "/confirmitem";

%>

<html>
<head>
	<title>Buy Item</title>
	<link rel="stylesheet" type="text/css" href="bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="main.css">

	<style>
	.category{
		font-size:11px;
	}
	</style>	
</head>

<body>
	<div class="wrapper"
	<div class="col-xs-12">
	<h3> Buying item </h3>
	
	<table class="table table-striped">
	<tbody>
		<tr>
			<th scope="row">Item ID:</th>
			<td><%= itemid %></td>
		</tr>
		<tr>
			<th scope="row">Name of Item:</th>
			<td><%= itemname %></td>
		</tr>
		<tr>
			<th scope="row">Buy Price:</th>
			<td><%= buy_price %></td>
		</tr>
		<tr>
			<th scope="row">Seller ID:</th>
			<td><%= sellerid %></td>
		</tr>
	</tbody>
	</table>

	<form action="<%= encrypttransact_URL %>">
		<div class="form-group">
			<label>Please enter your credit card number.</label>
			<input class="form-control" type="text" name="cred_num" placeholder="Credit Card Number">
			<input type="hidden" name="buyingitemID" value="<%= itemid %>" />
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-primary">BUY IT!</button>
		</div>
	</form>
	
	</div>
	</div>
	
</body>

</html>