package Pension.common.sys.audit;

import Pension.business.entity.User;
import Pension.common.CommonDbUtil;
import Pension.common.sys.ReqBean;
import Pension.jdbc.JdbcFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-15
 * Time: 下午8:01
 */
public class AuditManager {



    public static void addAudit(Long paramLong)
    {
        ReqBean reqBean=new ReqBean();
        HttpServletRequest req=reqBean.getLocalReq();
        User user=(User)req.getSession().getAttribute("user");
        String functionid=(String)req.getParameter("functionid");
        //_$1().addAudit(paramLong);
        CommonDbUtil dbUtil=new CommonDbUtil();
        Long auditid=dbUtil.getSequence("SEQ_OPAUDIT");
        Map map=new HashMap();
        map.put("auditid",auditid);
        map.put("tprkey",paramLong);
        map.put("functionid",functionid);
        map.put("digest","测试中的摘要");
        map.put("loginname",user.getLoginname());
        map.put("username",user.getUsername());
        map.put("dvcode",user.getRegionid());

        dbUtil.insertTableVales(map,"opauditbean");
        String sql_auditbean="insert into opauditbean(auditid,beanvalue) values("+auditid+","+paramLong+")";
        String sql_audit="insert into opaudit(auditid,auflag,auendflag,aulevel)values("+auditid+",0,0,'0')";
        dbUtil.execute(sql_auditbean);
        dbUtil.execute(sql_audit);
    }



    private static void addAudit(Long paramLong,String functionid,
                                String tablename,String classname,String digest,
                                String loginname,String username,String dvcode)
    {
        //_$1().addAudit(paramLong);
        Connection conn=JdbcFactory.getConn();

        CommonDbUtil dbUtil=new CommonDbUtil(conn);
        Long auditid=dbUtil.getSequence("SEQ_OPAUDIT");
        Map map=new HashMap();
        map.put("auditid",auditid);
        map.put("tprkey",paramLong);
        map.put("tname",tablename);
        map.put("functionid",functionid);
        map.put("classname",classname);
        map.put("digest",digest);
        map.put("loginname",loginname);
        map.put("username",username);
        map.put("dvcode",dvcode);

        dbUtil.insertTableVales(map,"opauditbean");
        String sql_audit="insert into opaudit(auditid,auflag,auendflag,aulevel)values("+auditid+",0,0,'0')";
        dbUtil.execute(sql_audit);
        try {
            if(null!=conn)conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
