package Pension.serverlet;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 13-8-13
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
import Pension.manager.usermanager.business.UserControl;

import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jack
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.getSession().getAttribute(null)

        String username=request.getParameter("username");
        //System.out.println(request.getCharacterEncoding());
        String password=request.getParameter("password");
        String userpwd=request.getParameter("userpwd"); //兼容统一登陆
        if(null==password){
            password=userpwd;
            String decodeing="UTF-8";
            StringTokenizer st = new StringTokenizer(request.getHeader("User-Agent"),";");
            st.nextToken();//得到用户的浏览器名
            String userbrowser = st.nextToken();
            if(userbrowser.toUpperCase().contains("MSIE")){ //如果是ie浏览器进行GBK解码
                decodeing="GBK";
            }
            username=new String(username.getBytes("ISO-8859-1"),decodeing); //处理url中用户名的编码
        }

        UserControl user=new UserControl();
        Map<String,Object> login_obj=user.login(username, password);
        if(Boolean.parseBoolean(login_obj.get("issuccess").toString())){
            request.getSession().setAttribute("username",login_obj.get("username"));
            request.getSession().setAttribute("userid",login_obj.get("userid"));
            request.getSession().setAttribute("roleid",login_obj.get("roleid"));
            request.getSession().setAttribute("displayname",login_obj.get("displayname"));
            request.getSession().setAttribute("divisionpath",login_obj.get("divisionpath"));
            request.getSession().setAttribute("password",password);
            request.getSession().setAttribute("divisionid",login_obj.get("divisionid"));

        }else{
            request.getSession().setAttribute("loginerromsg",login_obj.get("msg"));
        }

        response.sendRedirect("");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}

