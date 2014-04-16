package Pension.serverlet;


import Pension.common.CommonDbUtil;
import Pension.common.PageUtil;
import Pension.common.sys.util.CurrentUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        List list=commonDbUtil.query("select userid,passwd,loginname,username,regionid,(select dvname from division where dvcode=regionid) dvname  from xt_user where loginname='"+username+"' and passwd='"+password+"'");
        if(list.size()>0){
            Map map=(Map)list.get(0);
            CurrentUser user=new CurrentUser();
            try {
                //PageUtil.CopyProperties(map,user);
                user.setUserid(map.get("userid").toString());
                user.setPasswd(map.get("passwd").toString());
                user.setLoginName(map.get("loginname").toString());
                user.setUserName(map.get("username").toString());
                user.setRegionid(map.get("regionid").toString());
                user.setDvname(map.get("dvname").toString());

                request.getSession().setAttribute("user",user);
                System.out.print("************************************");
                System.out.println(user.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            request.getSession().setAttribute("loginerromsg","用户名或密码错误");
        }
        response.sendRedirect("");
    }



}

