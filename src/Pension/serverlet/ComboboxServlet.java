package Pension.serverlet;

import Pension.common.CommonDbUtil;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * User: Administrator
 * Date: 14-3-19
 * Time: 下午1:38
 * Desc: 获得下拉选择项
 */
public class ComboboxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchtype=req.getParameter("searchtype");
        String aaa102=req.getParameter("aaa102");
        String rows=req.getParameter("rows");
        String message="";
        if(null==searchtype){
            message= "缺少searchtype参数";
        }else{
            message=getSearchType(searchtype,aaa102,rows);
        }
        req.setAttribute("message",message);
        req.getRequestDispatcher("page/output.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private String getSearchType(String searchtype,String aaa102,String rows){
        String resultjson="";
        Connection conn= JdbcFactory.getConn();
        CommonDbUtil dbUtil=new CommonDbUtil(conn);
        try{
            String sql="select aaa102 id,aaa103 text from aa10 where aaa100='"+searchtype+"' order by aaa102 asc";
            if("-1".equals(rows)){
                sql="select  LOWER(AAA100) ename,aaa102 id,aaa103 text from aa10 order by id,text";
                Map map=new HashMap();
                List lsa=dbUtil.query(sql);
                Iterator ita=lsa.iterator();
                while(ita.hasNext()){
                    Map m=(Map)ita.next();
                    String name=(String)m.get("ename");
                    List map_list=(List)map.get(name);
                    if(map_list!=null){
                        map_list.add(m);
                    }else{
                        List l=new ArrayList();
                        l.add(m);
                        map.put(name,l);
                    }
                }
                Map result=new HashMap();
                result.put("success",true);
                result.put("rows",map);
                resultjson= JSONObject.fromObject(result).toString();
            }else{
                List list=dbUtil.query(sql);

                if(aaa102!=null){
                    Iterator it=list.iterator();
                    Map map=null;
                    while(it.hasNext()){
                        map=(Map)it.next();
                        if(aaa102.equals(map.get("id"))){
                            map.put("selected",true);
                            break;
                        }
                    }
                }/*else{
                    if(list.size()>0){
                        Map map=(Map)list.get(0);
                        map.put("selected",true);//第一个为默认值
                    }
                }*/


                resultjson= JSONArray.fromObject(list).toString();
            }
        }finally {
            try {
                if(null!=conn)conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultjson;
    }
}
