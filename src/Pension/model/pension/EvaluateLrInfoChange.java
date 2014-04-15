package Pension.model.pension;

import Pension.common.*;
import Pension.common.db.DbUtil;
import Pension.model.Model;
import net.sf.json.JSONObject;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-10
 * Time: 下午1:55
 */
public class EvaluateLrInfoChange extends Model {
    public String save() throws AppException, SQLException {
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map= ParameterUtil.toMap(this.getRequest());
        int result=0;
        Long id=Long.parseLong((String)map.get("pg_id"));
        map.put("pg_id",id);
        Map where=new HashMap();
        where.put("pg_id",id+"");
        Map bgbefore= queryOldData("select a.*,b.* from t_needassessment a,t_needassessmentsum b where a.pg_id=b.pg_id and a.pg_id=" + id);
        saveBgData(bgbefore,map);
        result=commonDbUtil.updateTableVales(map,"t_needassessmentsum",where);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    /*保存变更前后的数据*/
    private void saveBgData(Map data,Map afterdata) throws SQLException {
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Long bgid=commonDbUtil.getSequence("SEQ_T_NEEDASSESSMENTBG");
        Map bgmap=new HashMap();
        bgmap.put("bgid",bgid);
        bgmap.put("pg_id",afterdata.get("pg_id"));
        bgmap.put("bgreason",afterdata.get("bgreason"));
        commonDbUtil.insertTableVales(bgmap,"t_needassessmentbg");
        PreparedStatement pstmt= DbUtil.get().prepareStatement(
                "insert into t_needassessmentbgdt(BGDTID,BGID,BGCOLUMN,BGVAL1,BGVAL2)" +
                        "values(SEQ_T_NEEDASSESSMENTBGDT.NEXTVAL,?,?,?,?)");
        Iterator it=data.keySet().iterator();
        while(it.hasNext()){
            String key;
            Object value;
            key=it.next().toString();
            value=(String)(data.get(key)+"");
            if("null".equals(value)){
                value="";
            }
            String aftvalue=(String)(afterdata.get(key)+"");
            if("null".equals(aftvalue)){
                aftvalue="";
            }
            if(!aftvalue.equals(value)){
                pstmt.setLong(1,bgid);
                pstmt.setString(2, key);
                pstmt.setString(3, value + "");
                pstmt.setString(4, afterdata.get(key)+"");
                pstmt.addBatch();
            }
        }
        pstmt.executeBatch();
        pstmt.close();
    }

    private Map  queryOldData(String sql) throws SQLException {
        Statement stmt= DbUtil.get().createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        Map map=new HashMap();
        ResultSetMetaData mtdata=rs.getMetaData();
        if(rs.next()){
             for(int i=1;i<mtdata.getColumnCount();i++){
                 String clumnName=mtdata.getColumnName(i).toLowerCase();
                 map.put(clumnName,rs.getString(clumnName));
             }
        }
        rs.close();
        stmt.close();
        return map;
    }

    @Test
    public void test() throws SQLException {
        Map map=this.queryOldData("select a.*,b.* from t_needassessment a,t_needassessmentsum b where a.pg_id=b.pg_id and a.pg_id=341");
        System.out.println(JSONObject.fromObject(map).toString());
    }

}
