package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String query = request.getParameter("query");
        int numResultsToSkip = 0;
        int numResultsToReturn = 20;
        SearchResult[] results = null;
        
        String ntoskip = request.getParameter("numResultsToSkip");
        String ntoreturn = request.getParameter("numResultsToReturn");
        
       
        if(ntoskip != null){
        	numResultsToSkip = Integer.parseInt(ntoskip);
        }
        if (ntoreturn != null){
        	numResultsToReturn = Integer.parseInt(ntoreturn);
        }
        
        
        
          
        if(query != "" || query != null){
        	results = AuctionSearchClient.basicSearch(query, numResultsToSkip, numResultsToReturn+1);
        }
        
        request.setAttribute("results", results);
        request.setAttribute("query", query);
        request.setAttribute("numResultsToSkip", numResultsToSkip);
        request.setAttribute("numResultsToReturn", numResultsToReturn);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
        
        
    }
}
