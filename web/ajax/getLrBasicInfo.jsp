<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-24
  Time: 上午10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Pension.business.control.LrbasicInfo" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    LrbasicInfo lc = new LrbasicInfo();
    out.print(lc.findLrbasicInfo());
%>