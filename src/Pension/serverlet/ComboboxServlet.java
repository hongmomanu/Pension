package Pension.serverlet;

import Pension.common.CommonDbUtil;
import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        String message="";
        if(null==searchtype){
            message= "缺少searchtype参数";
        }else{
            message=getSearchType(searchtype,aaa102);
        }
        req.setAttribute("message",message);
        req.getRequestDispatcher("page/output.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private String getSearchType(String searchtype,String aaa102){
        Connection conn= JdbcFactory.getConn();
        CommonDbUtil dbUtil=new CommonDbUtil(conn);
        List list=dbUtil.query("select aaa102 id,aaa103 text from aa10 where aaa100='"+searchtype+"'");
        try {
            if(null!=conn)conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        }else{
            if(list.size()>0){
                Map map=(Map)list.get(0);
                map.put("selected",true);//第一个为默认值
            }
        }

        return JSONArray.fromObject(list).toString();
    }
}
