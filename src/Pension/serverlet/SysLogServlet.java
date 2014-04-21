package Pension.serverlet;

import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.db.DbUtil;
import Pension.common.sys.userlog.UserLog;
import Pension.common.sys.util.CurrentUser;
import Pension.common.sys.util.SysUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-18
 * Time: 上午7:58
 */
public class SysLogServlet  extends HttpServlet {
    private UserLog userLog=new UserLog();
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName=(String)request.getParameter("eventName");
        String method=(String)request.getParameter("method");

        if(method==null){
            request.setAttribute("message","缺少请求信息");
            request.getRequestDispatcher("page/output.jsp").forward(request,response);
        }else{

            if(eventName!=null&&!"".equals(eventName)){
                CurrentUser user=(CurrentUser)request.getSession().getAttribute("user");
                SysUtil.setCacheCurrentUser(user);//获得当前用户信息
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
                request.setAttribute("page","page/syslog.jsp");
                request.setAttribute("method",method);
                request.getRequestDispatcher("main.jsp").forward(request, response);
            }
        }

    }

    private String doIf(String en,HttpServletRequest request,Map map) throws Exception {
        String method=request.getParameter("method");


        CurrentUser user= SysUtil.getCacheCurrentUser();

        if("queryLog".equals(en)){
            Integer page=Integer.parseInt(request.getParameter("page"));
            Integer rows=Integer.parseInt(request.getParameter("rows"));
            return JSONObject.fromObject(userLog.query(method,page,rows)).toString();
        }if("queryOriginalpage".equals(en)){
            Long opseno=Long.parseLong(request.getParameter("opseno"));
            return userLog.clobExport(opseno);
        }else{
            return null;
        }
    }

}
