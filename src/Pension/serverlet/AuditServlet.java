package Pension.serverlet;

import Pension.common.AppException;
import Pension.common.RtnType;
import Pension.common.db.DbUtil;
import Pension.common.sys.audit.AuditBusiness;
import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:46
 */
public class AuditServlet extends HttpServlet {
    private AuditBusiness auditbs=new AuditBusiness();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName=(String)request.getParameter("eventName");
        String method=(String)request.getParameter("method");
        if(method==null){
            request.setAttribute("message","缺少请求信息");
            request.getRequestDispatcher("page/output.jsp").forward(request,response);
        }else{

            if(eventName!=null&&!"".equals(eventName)){
                Connection conn= DbUtil.get();
                try {
                    conn.setAutoCommit(false);
                    request.setAttribute("message",doIf(eventName, request, ParameterUtil.toMap(request),conn));
                    conn.commit();
                }  catch (Exception e) {
                    request.setAttribute("message", RtnType.FAILURE);
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }finally {
                    if(null!=conn) {
                        DbUtil.close();
                        System.out.println("关闭连接");
                    }
                }
                request.getRequestDispatcher("page/output.jsp").forward(request,response);
            }else{
                request.setAttribute("page","page/audit.jsp");
                request.setAttribute("method",method);
                request.getRequestDispatcher("main.jsp").forward(request, response);
            }
        }

    }

    private String doIf(String en,HttpServletRequest request,Map map,Connection conn) throws Exception {
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String method=request.getParameter("method");
        String loginname= request.getSession().getAttribute("loginname").toString();
        String dvcode= request.getSession().getAttribute("dvcode").toString();
        if("queryAudit".equals(en)){
            return this.queryAudit(commonDbUtil,method,map,loginname,dvcode);
        }if("saveAudit".equals(en)){
            JSONArray jarray=JSONArray.fromObject(map.get("auditmsgs"));
            return auditbs.doBanchAudit(conn,commonDbUtil, method, jarray, loginname);
        }else{
            return null;
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    private String queryAudit(CommonDbUtil commonDbUtil,String method,Map map,String userid,String dvcode) throws AppException {
        return auditbs.query(commonDbUtil,method,map,userid,dvcode);
    }

}
