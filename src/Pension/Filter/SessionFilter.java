package Pension.filter;

import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-13
 * Time: 下午4:00
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpSession session=req.getSession(false);
        if(session==null){
            Map map=new HashMap();
            map.put("success",false);
            map.put("message","没有会话信息,如果您还有未保存的业务,请另打开一个tab页面,登陆.再保存此业务");
            PrintWriter pw=response.getWriter();
            pw.print(JSONObject.fromObject(map).toString());
            pw.close();
        }else{
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
