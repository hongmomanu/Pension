package Pension.common.sys.audit;

import Pension.common.db.DbUtil;
import Pension.jdbc.JdbcFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-23
 * Time: 下午4:02
 */
public class CallBack {
    public static void doAudit(Long id) throws Exception {
        AuditBean ab=new AuditBean();
        String sql="select b.tprkey,a.auditid,a.auflag,a.auuser,a.audate,a.aulevel,a.audesc,a.auendflag,f.location from xt_function f,sysuserlog b,opaudit a " +
                " where f.functionid=b.functionid and b.opseno=a.opseno and a.auditid=?";
        PreparedStatement pstmt= DbUtil.get().prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs=pstmt.executeQuery();
        String location=null;
        if(rs.next()){
            if(null==location){
               location=rs.getString("location");
            }
           ab.setAuditid(id);
           ab.setTprkey(rs.getString("tprkey"));

           Map currentAudit=new HashMap();
            currentAudit.put("auditid",rs.getLong("auditid"));
            currentAudit.put("auflag",rs.getString("auflag"));
            currentAudit.put("audate",rs.getDate("audate"));
            currentAudit.put("aulevel",rs.getString("aulevel"));
            currentAudit.put("audesc",rs.getString("audesc"));
            currentAudit.put("auendflag",rs.getString("auendflag"));
           ab.setCurrentAudit(currentAudit);
        }
        rs.close();
        pstmt.close();

        if(null!=location){
            Class c=Class.forName("Pension.model."+location);
            Class[] iters=c.getInterfaces();
            boolean b=false;
            for(Class i:iters){
                if(IMultilevelAudit.class.getName().equals(i.getName())){
                    b=true;
                    break;
                }
            }
            if(!b){
                throw new Exception("IMultilevelAudit 接口未实现");
            }
            IMultilevelAudit audit=(IMultilevelAudit)c.newInstance();
            audit.audit(ab);
        }
    }
}
