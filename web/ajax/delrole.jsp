<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-8-9
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="Pension.manager.usermanager.business.RoleControl" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    if(request.getParameter("roleid")==null){
       out.print("缺少分页参数roleid");
    }
    else{
        int roleid=Integer.parseInt(request.getParameter("roleid"));
        RoleControl role=new RoleControl();
        out.print(role.delRole(roleid));
    }
%>