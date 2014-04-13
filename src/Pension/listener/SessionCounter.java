package Pension.listener;

/**
 * User: Administrator
 * Date: 14-4-13
 * Time: 下午4:24
 */
import javax.servlet.http.*;
//import jp.co.sysmex.sps.app.web.WebAccountBean;
//①HttpSessionListener 接口的实现。
public class SessionCounter implements HttpSessionListener {
    private static int sesCount = 0;
    //②session生成时触发sessionCreated方法
    public void sessionCreated(HttpSessionEvent hse) {
        sesCount++;
        //ServletContext sc = hse.getSession().getServletContext();
        String sessid = hse.getSession().getId();

        System.out.println(" session Created " + sesCount);
        System.out.println(" session ++ " + sessid);
    }
    //③session无效时触发sessionDestroyed方法
    //此时session中的内容还可以正常取到
    public void sessionDestroyed(HttpSessionEvent hse) {
        String sessid = hse.getSession().getId();

        System.out.println(" session Destroyed " + sesCount);
        System.out.println(" session -- " + sessid);

        // WebAccountBean account =  (WebAccountBean)(hse.getSession().getAttribute("ACCOUNT_KEY"));
        // System.out.println(account.getEnterpriseCode());
        // System.out.println(account.getEnterpriseFullKanjiName());

        sesCount--;
    }

    public static int getActiveSessions(){
        return sesCount;
    }
}