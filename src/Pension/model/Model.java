package Pension.model;

import Pension.common.AppException;
import Pension.common.CommQuery;
import Pension.common.IParam;
import Pension.common.sys.audit.AuditBusiness;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-13
 * Time: 上午8:14
 */
public class Model {
    private static final ThreadLocal<HttpServletRequest> localRequest=
            new ThreadLocal<HttpServletRequest>();
    private static final ThreadLocal<Map> localMap = new ThreadLocal<Map>();

    public HttpServletRequest getRequest() {
        return localRequest.get();
    }

    public void setRequest(HttpServletRequest request) {
         localRequest.set(request);
    }

    public void query(String sql,  int page, int rows) throws AppException {
        Map map=CommQuery.query(sql,page,rows);
        localMap.get().put(IParam.TOTAL,map.get(IParam.TOTAL));
        localMap.get().put(IParam.ROWS,map.get(IParam.ROWS));
    }
    public void query(String sql) throws AppException {
        Map map=CommQuery.query(sql);
        localMap.get().put(IParam.ROWS,map.get(IParam.ROWS));
    }

    public void setTest(Object obj){
        localMap.get().put("test",obj);
    }
    public void setLocalMap(Map map){
        localMap.set(map);
    }
    public void setMessage(String message){
        localMap.get().put(IParam.MESSAGE,message);
    }

    public Map queryCurrentAudit(String functionid,String tprkey) throws AppException {
        return new AuditBusiness().queryCurrentAudit(functionid,tprkey);
    }
    public Map queryHistoryAudit(String functionid,String tprkey) throws AppException {
        return new AuditBusiness().queryHistoryAudit(functionid,tprkey);
    }
}
