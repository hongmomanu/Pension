package Pension.serverlet;

import Pension.common.ModelManager;
import Pension.common.ParameterUtil;
import Pension.common.db.DbUtil;
import Pension.jdbc.JdbcFactory;
import Pension.model.Model;
import net.sf.json.JSONObject;

import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-13
 * Time: 下午3:21
 * Desc: 老年人管理,统一入口控制
 */
public class LRServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName=(String)request.getParameter("eventName");
        if(request.getParameter("model")==null){
            request.setAttribute("message","缺少请求信息");
            request.getRequestDispatcher("page/output.jsp").forward(request,response);
        }
        if(eventName!=null&&!"".equals(eventName)){
            DbUtil.get();
            request.setAttribute("message", doEvent(request, ParameterUtil.toMap(request)));
            DbUtil.close();
            request.getRequestDispatcher("page/output.jsp").forward(request,response);
        }else {
            String page=(String)request.getParameter("page");
            if(page!=null&&!"".equals(page)){
                page=page.replace('.','/');
                page="page/"+page+".jsp";
                request.setAttribute("page",page);
            }else{  //如果没有
                request.setAttribute("page","page/"+request.getParameter("model").replace('.','/')+".jsp");
            }
            request.getRequestDispatcher("main.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //反射生成对象,并根据eventName参数调用对象的方法
    private String doEvent(HttpServletRequest request,Map param){
        String result=null;
        try {
            String model="Pension.model."+request.getParameter("model");
            Object o=ModelManager.getModel(model);
            Class pm=  Class.forName(model);
            Model superModel=(Model)o;
            superModel.setRequest(request);
            Map map=new HashMap();
            superModel.setLocalMap(map);
            //执行对象的方法
            result=(String)pm.getMethod(request.getParameter("eventName")).invoke(o);
            System.out.println(JSONObject.fromObject(map).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
