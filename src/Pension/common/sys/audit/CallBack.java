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
        String sql="select b.*,a.auflag,a.auuser,a.audate,a.aulevel,a.audesc,a.auendflag from opauditbean b,opaudit a " +
                " where b.auditid=a.auditid and b.auditid=?";
        PreparedStatement pstmt= DbUtil.get().prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
           ab.setAuditid(id);
           ab.setClassname(rs.getString("classname"));
           ab.setTname(rs.getString("tname"));
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

        if(null!=ab.getClassname()){
            Class c=Class.forName(ab.getClassname());
            Class[] iters=c.getInterfaces();
            boolean b=false;
            for(Class i:iters){
                System.out.println(i.getName());
                if(IMultilevelAudit.class.getName().equals(i.getName())){
                    b=true;
                    break;
                }
            }
            if(!b){
                throw new Exception("IMultilevelAudit 接口未实现");
            }
            IMultilevelAudit audit=(IMultilevelAudit)c.newInstance();
            audit.audit(JdbcFactory.getConn(),ab);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class c=Class.forName("Pension.model.hzyl.EvaluateLrInfo");
        Class[] iters=c.getInterfaces();
        boolean b=false;
        for(Class i:iters){
            System.out.println(i.getName());
           if(IMultilevelAudit.class.getName().equals(i.getName())){
               b=true;
               break;
           }
        }
        System.out.println(b);
    }
}
