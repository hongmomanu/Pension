<%@ page import="Pension.propertycheck.control.ProperCheckControl" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Enumeration" %>
<%--
  Created by IntelliJ IDEA.
  User: weipan
  Date: 13-11-19
  Time: 下午14:32
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
        String tipName="事件名称-";
        String eventName=request.getParameter("eventName");
        if(null==eventName){
            out.print("{success:false,message:'无对应的操作,要指明事件名称'}");
            return;
        }

        Map<String,Object> params=new HashMap<String,Object>();
        ProperCheckControl propchk=new ProperCheckControl();
        if(request.getParameter("rowsname")!=null){
             propchk.setRowsname(request.getParameter("rowsname").toString());
        }
        if(request.getParameter("totalname")!=null){
             propchk.setTotalname(request.getParameter("totalname").toString());
        }



        if("registerfamilyinfo".equals(eventName)){                      //保存家庭财产信息和家庭人员信息
            params.put("fm01",request.getParameter("fm01"));
            params.put("familymembers",request.getParameter("familymembers"));
            params.put("isprocess",request.getParameter("isprocess"));
            out.print(propchk.saveFamliyPropertyInfo(params));
        }else if("updatefamilyinfo".equals(eventName)){               //更新家庭财产信息和家庭人员信息
            params.put("fm01",request.getParameter("fm01"));
            params.put("fmy001",request.getParameter("fmy001"));
            params.put("familymembers",request.getParameter("familymembers"));
            params.put("isprocess",request.getParameter("isprocess"));
            out.print(propchk.updateFamliyPropertyInfo(params));
        }else if("changefamilyinfo".equals(eventName)){               //变更家庭财产信息和家庭人员信息     变更业务 //注销业务
            params.put("fm01",request.getParameter("fm01"));
            params.put("fmy001",request.getParameter("fmy001"));
            params.put("familymembers",request.getParameter("familymembers"));
            params.put("isprocess",request.getParameter("isprocess"));
            out.print(propchk.changeFamliyPropertyInfo(params));
        }else if("delfamilypropertybyfmy001".equals(eventName)){ //未提交前删除
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.delFamliyPropertyInfoByFmy001(paraMap));
        }else if("recoverapplybyfmy001".equals(eventName)){ //未提交前恢复之前的状态
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.delFamliyPropertyInfoByFmy001(paraMap));
        }else if("cancelsubmitbyfmy001".equals(eventName)){ //未审核前取消提交
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.cancelsubmitbyfmy001(paraMap));
        }else if("getfamilypropertyinfo".equals(eventName)){       //查询家庭信息
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                  String name=(String)e.nextElement();
                  paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.getFamilyPropertyInfo(paraMap));
        }else if("getFamilyPropertyInfoByFmy001".equals(eventName)){       //根据fm01表的主键查询一个家庭信息
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                  String name=(String)e.nextElement();
                  paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.getFamilyPropertyInfoByFmy001(paraMap));
        }else if("getfamilypropertyinfobycheckrole".equals(eventName)){    //核定人员查询家庭信息
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.getFamilyPropertyInfoByCheckRole(paraMap));
        }else if("checkpropertyitem".equals(eventName)){                 //提交核定信息
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.doCheckItem(paraMap));
        }else if("getperopertycheckitemdetailbyowerid".equals(eventName)
                ||"getperopertycheckitemdetailbyfmy001".equals(eventName)){     //根据fmy001查询fm03中的全部记录(核定信息)
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.getPropertyCheckItemDatilByFmy001(paraMap));
        }else if("changebusinessprocessstatus".equals(eventName)){   //提交申请
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.changeBusinessStatus(paraMap));
        }
        else if("processcheck".equals(eventName)){      //processcheck   审核审批
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.processCheck(paraMap));
        }
        else if("getprocesscheckbyfmy001".equals(eventName)){      //获得审批记录
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.getProcessCheck(paraMap));
        }

        else if("getfamilymembersbyfmy001".equals(eventName)){      //获得家庭人员信息
            Enumeration e  =(Enumeration) request.getParameterNames();
            Map paraMap=new HashMap<String,Object>();
            while(e.hasMoreElements()){
                String name=(String)e.nextElement();
                paraMap.put(name,request.getParameter(name));
            }
            out.print(propchk.getfamilymembersbyfmy001(paraMap));
        }


         //无相应处理时的默认操作提示
        else{
            out.print("{success:'false',message:'无对应的操作'}");
        }
%>