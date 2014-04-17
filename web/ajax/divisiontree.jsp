<%@ page import="Pension.business.pension.DivisionTree" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-4-15
  Time: 上午10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    DivisionTree dt = new DivisionTree();
    out.print(dt.divisiontree());
%>