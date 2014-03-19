package Pension.serverlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String msg="";
        if(null==searchtype){
            msg= "缺少searchtype参数";
        }else{
            msg=getSearchType(searchtype);
        }
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private String getSearchType(String searchtype){
          return null;
    }
}
