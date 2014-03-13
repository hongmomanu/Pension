<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-8-9
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="Pension.business.control.BusinessProcessControl" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    if (request.getParameter("limit") == null) {
        out.print("缺少参数limit");
    }
    if (request.getParameter("start") == null) {
        out.print("缺少参数start");
    } else {
        BusinessProcessControl bp = new BusinessProcessControl();
        int start = Integer.parseInt(request.getParameter("start"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String query = request.getParameter("query");
        String[] types = request.getParameterValues("type") == null ? request.getParameterValues("type[]")
                : request.getParameterValues("type");
        String totalname = request.getParameter("totalname");
        String rowsname = request.getParameter("rowsname");
        out.print(bp.searchbusinessbypid(start, limit, query, types,totalname,rowsname));
    }
%>