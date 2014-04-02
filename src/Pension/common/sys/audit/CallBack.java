package Pension.common.sys.audit;

import Pension.jdbc.JdbcFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Administrator
 * Date: 14-3-23
 * Time: 下午4:02
 */
public class CallBack {
    public static void doAudit(Long id) throws Exception {
          Connection conn= JdbcFactory.getConn();
        AuditBean ab=new AuditBean();
        String sql="select * from opauditbean where auditid=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
           ab.setAuditid(id);
           ab.setClassname(rs.getString("classname"));
           ab.setTname(rs.getString("tname"));
           ab.setTprkey(rs.getString("tprkey"));
        }
        rs.close();
        pstmt.close();
        conn.close();

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
