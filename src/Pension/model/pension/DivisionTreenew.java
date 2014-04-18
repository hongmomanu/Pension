package Pension.model.pension;

import Pension.jdbc.JdbcFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-16
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
public class DivisionTreenew {
    private static final String DivisionTable="division";

    public String getDivisions(int parentid,boolean onlychildren){
        Map<String,Object> res=new HashMap<String, Object>();

        //DivisionImplement di=new DivisionImplement();
        if(onlychildren){
            return JSONArray.fromObject(this.getDivisions(parentid)).toString();
        }
        res.put("text","");
        res.put("children",this.getDivisions(parentid));
        return JSONObject.fromObject(res).toString();

    }


    public ArrayList<Map<String, Object>> getDivisions(int parentid){
        Connection testConn= JdbcFactory.getConn("oracle");
        int leaf;
        //String sql=  "select divisionname,divisionpath,rowid,signaturepath from "+DivisionTable+" where parentid=?";
        /*String sql=  "select a.divisionname,a.divisionpath,a.rowid,a.signaturepath,(select count(1) from divisions b where b.parentid=a.rowid)
         as leaf from "  +DivisionTable+" a where a.parentid=?";*/
        String sql = "SELECT a.dvname,a.totalname,a.dvhigh,a.dvcode,a.dvrank,(select count(1) from division b where a.dvcode=b.dvhigh) AS leaf  FROM "+DivisionTable
                +" a WHERE  a.dvhigh = ?";

        PreparedStatement pstmt = JdbcFactory.getPstmt(testConn, sql);
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            pstmt.setString(1,Integer.toString(parentid));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if(rs.getObject("leaf")==null){
                    leaf = 0;
                }else{
                    leaf = rs.getInt("leaf");
                }
                Map<String,Object> obj=new HashMap<String, Object>();
                obj.put("text",rs.getString("dvname"));                                        //dvname        obj.put("text",rs.getString("divisionname"));
                obj.put("divisionpath",rs.getString("totalname"));                             // totalname          obj.put("divisionpath",rs.getString("divisionpath"));
                obj.put("id",rs.getInt("dvrank"));                                                          // dvcode  dvrank          obj.put("id",rs.getInt("rowid"));
                obj.put("parentid",rs.getString("dvcode"));                                                              //dvhigh                obj.put("parentid",parentid);
                //obj.put("signaturepath",rs.getString("signaturepath"));                         //                          obj.put("signaturepath",rs.getString("signaturepath"));
                obj.put("iconCls",leaf>0?"":"division-tree-leaf");
                obj.put("leaf",leaf>0?false:true);
                obj.put("state",leaf>0?"closed":"open");
               /*
               obj.put("iconCls",Integer.parseInt(rs.getString("leaf"))>0?"":"division-tree-leaf");
                obj.put("leaf",Integer.parseInt(rs.getString("leaf"))>0?false:true);
                obj.put("state",Integer.parseInt(rs.getString("leaf"))>0?"closed":"open");
               if(rs.getString("signaturepath")==null||rs.getString("signaturepath").equals("")){
                    obj.put("qtip","无签章图片");
                }else{
                    obj.put("qtip","<img width=\"200\" height=\"200\" src=\""+ rs.getString("signaturepath")+"\">");
                }*/
                list.add(obj);

            }
            return list;
        }catch (Exception E){
           /* log.debug(E.getMessage());*/
            E.printStackTrace();
            return list;
        }
    
    }

}
