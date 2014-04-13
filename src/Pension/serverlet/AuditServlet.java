package Pension.serverlet;

import Pension.business.entity.User;
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
    private AuditBusiness auditBusiness=new AuditBusiness();
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName=(String)request.getParameter("eventName");
        String method=(String)request.getParameter("method");
        if(method==null){
            request.setAttribute("message","缺少请求信息");
            request.getRequestDispatcher("page/output.jsp").forward(request,response);
        }else{

            if(eventName!=null&&!"".equals(eventName)){
                DbUtil.get();
                try {
                    DbUtil.begin();
                    request.setAttribute("message",doIf(eventName, request, ParameterUtil.toMap(request)));
                    DbUtil.commit();
                }  catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("message", RtnType.FAILURE);
                    DbUtil.rollback();
                }finally {
                    DbUtil.close();
                }
                request.getRequestDispatcher("page/output.jsp").forward(request,response);
            }else{
                request.setAttribute("page","page/audit.jsp");
                request.setAttribute("method",method);
                request.getRequestDispatcher("main.jsp").forward(request, response);
            }
        }

    }

    private String doIf(String en,HttpServletRequest request,Map map) throws Exception {
        String method=request.getParameter("method");
        User user=(User)request.getSession().getAttribute("user");
        String loginname= user.getLoginname();
        String dvcode=user.getRegionid();
        if("queryAudit".equals(en)){
            return auditBusiness.query(method, map, loginname, dvcode);
        }if("saveAudit".equals(en)){
            JSONArray jarray=JSONArray.fromObject(map.get("auditmsgs"));
            return auditBusiness.doBanchAudit(method, jarray, loginname);
        }else{
            return null;
        }
    }



}
