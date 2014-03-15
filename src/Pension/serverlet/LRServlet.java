package Pension.serverlet;

import Pension.conmmon.ModelManager;
import Pension.conmmon.ParameterUtil;
import Pension.jdbc.JdbcFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
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
            request.setAttribute("message",doEvent(request, ParameterUtil.toMap(request)));
            request.getRequestDispatcher("page/output.jsp").forward(request,response);
        }else {
            /*Map<String,String> map=new HashMap<String,String>();
            map.put("model",request.getParameter("model"));
            map.put("pageSelectedAtRuntime",request.getParameter("pageModel").replace('.','/')+".jsp");
            map.put("jsSelectedAtRuntime","page/"+request.getParameter("pageModel").replace('.','/')+".js");*/
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
            Connection conn=JdbcFactory.getConn();
            Class pm=  Class.forName(model);
            if(null==o){
                o=pm.newInstance();
                ModelManager.addModel(model,o);
            }

            //注入属性数据
            List fieldList=new ArrayList();
            for(Field f:pm.getDeclaredFields()){
                fieldList.add(f.getName());
            }
            if(fieldList.contains("request")){
                Field req=pm.getDeclaredField("request");
                req.setAccessible(true);
                req.set(o,request);
            }
            if(fieldList.contains("param")){
                Field req=pm.getDeclaredField("param");
                req.setAccessible(true);
                req.set(o,param);
            }
            if(fieldList.contains("conn")){
                Field req=pm.getDeclaredField("conn");
                req.setAccessible(true);
                req.set(o, conn);
            }

            //执行对象的方法
            result=(String)pm.getMethod(request.getParameter("eventName")).invoke(o);
            //回收connection
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
