<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-8-9
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="Pension.business.control.BusinessProcessControl" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    if(request.getParameter("businessid")==null){
       out.print("缺少参数businessid");
    }
    else{
        int businessid=Integer.parseInt(request.getParameter("businessid"));
        BusinessProcessControl pc=new BusinessProcessControl();
        out.print(pc.delBusinessbybid(businessid));
    }
%>