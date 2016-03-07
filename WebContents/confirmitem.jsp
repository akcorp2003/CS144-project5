<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<%@ page import="edu.ucla.cs.cs144.Item" %>
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<% 
String itemid = (String) request.getAttribute("id");
String itemname = (String) request.getAttribute("item_name");
String buy_price = (String) request.getAttribute("buy_price");
String sellerid = (String) request.getAttribute("seller_id");
String credit_num = (String) request.getAttribute("cred_num");
String date_time = (String) request.getAttribute("time");
int cred_err = (Integer) request.getAttribute("cred_err");

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

<%
if(cred_err == 1){
%>
	<div class="wrapper">
	<div class="col-xs-12">
		<p>Nice try but that's not a valid credit card number. Try again or get a new card.</p>
	</div>
	</div>
<%
}
else{
%>
	<div class="wrapper">
	<div class="col-xs-12">
		<h3> CONGRATULATIONS! You bought the item: <%= itemname %></h3>
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
			<tr>
				<th scope="row">Credit Card Number:</th>
				<td><%= credit_num %></th>
			</tr>
			<tr>
				<th scope="row">Time of Purchase:</th>
				<td><%= date_time %></th>
			</tr>
		</tbody>
	</table>
	<div class="btn-group" role="group" aria-label="Navigation buttons">
		<a class="btn btn-primary" href="item">Return To Item Page</a>
	</div>

	</div>
	</div>
	
<%
}
%>

</body>
</html>