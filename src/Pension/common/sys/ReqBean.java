package Pension.common.sys;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-14
 * Time: 上午8:53
 */
public class ReqBean {
    private static final ThreadLocal<HttpServletRequest> localMap = new ThreadLocal<HttpServletRequest>();

    public  void  setLocalReq(HttpServletRequest req){
        localMap.set(req);
    }
    public  HttpServletRequest getLocalReq(){
        return localMap.get();
    }
}
