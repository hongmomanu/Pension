package Pension.model.pension;

import Pension.common.CommonDbUtil;
import Pension.model.Model;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-13
 * Time: 下午6:54
 */
public class EvaluateLrInfoGrid extends Model {

    public String query(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Integer page=Integer.parseInt(this.getRequest().getParameter("page"));
        Integer rows=Integer.parseInt(this.getRequest().getParameter("rows"));

        Map map= new HashMap();
        String sql="select o.name,o.identityid,o.birthd,o.gender,o.age,o.nation,o.address,o.type,o.registration" +
                ",n.* from t_needassessment n,t_oldpeople o where o.lr_id=n.lr_id order by n.pg_id desc";
        List list=commonDbUtil.query("SELECT * FROM (SELECT tt.*, ROWNUM ro FROM ("+sql+") tt WHERE ROWNUM <="+(page)*rows+") WHERE ro > "+(page-1)*rows);
        int count=commonDbUtil.query(sql).size();
        map.put("total",count);
        map.put("rows",list);
        return JSONObject.fromObject(map).toString();
    }
    public String queryById(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Integer id=Integer.parseInt(this.getRequest().getParameter("id"));

        Map map= new HashMap();
        String sql="SELECT t1.*,t2.* from t_needassessment t1,t_needassessmentsum t2 where t1.pg_id=t2.pg_id and t1.pg_id="+id;
        List list=commonDbUtil.query(sql);
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return JSONObject.fromObject(map).toString();
    }
}
