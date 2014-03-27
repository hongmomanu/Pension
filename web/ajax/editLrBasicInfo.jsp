<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-26
  Time: 上午9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Pension.business.control.LrbasicInfo" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    LrbasicInfo lc = new LrbasicInfo();
    String peopleid = request.getParameter("peopleid");
    out.print(lc.editLrbasicInfo());


%>