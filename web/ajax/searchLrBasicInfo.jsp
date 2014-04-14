<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-25
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Pension.business.pension.LrbasicInfo" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    LrbasicInfo lc = new LrbasicInfo();
    String lrname = request.getParameter("lrname");
    String pages = request.getParameter("page");
    String rows = request.getParameter("rows") ;
    /*String ischeck = request.getParameter("ifchick");
    if(ischeck.equals("click"))  {
        pages = "1";
    }*/
    out.print(lc.findLrbasicInfo(lrname,pages,rows));


%>