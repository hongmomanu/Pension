<%@ page import="Pension.business.pension.DivisionTree" %>
<%@ page import="Pension.model.pension.DivisionTreenew" %>
<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-8-9
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    if(request.getParameter("node")==null){
       out.print("缺少参数node");
    }
    else{
        int parentid=Integer.parseInt(request.getParameter("node"));
        boolean onlychild=request.getParameter("onlychild")==null?false:true;
        //DivisionControl dc=new DivisionControl();
        DivisionTreenew dt =  new DivisionTreenew();
        out.print(dt.getDivisions(parentid,onlychild));
        /*out.print("{\"text\":\"\",\"value\":1,\"children\":[{\"text\": \"舟山市\",\"id\":12,\"expanded\": false}]}");*/
    }
%>