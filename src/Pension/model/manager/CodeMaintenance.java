package Pension.model.manager;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-19
 * Time: 下午1:02
 */
public class CodeMaintenance {
    private HttpServletRequest request;
    private Connection conn;

    public String queryCode9(){
        Integer page=Integer.parseInt(request.getParameter("page"));
        Integer rows=Integer.parseInt(request.getParameter("rows"));
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String sql="select * from aa09 order by aaa100 asc";
        List list=commonDbUtil.query("SELECT * FROM (SELECT tt.*, ROWNUM ro FROM ("+sql+
                ") tt WHERE ROWNUM <="+(page)*rows+") WHERE ro > "+(page-1)*rows);
        int count=commonDbUtil.query(sql).size();
        Map map=new HashMap();
        map.put("total",count);
        map.put("rows",list);
        return JSONObject.fromObject(map).toString();
    }

    public String queryCode10(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String aaa100=request.getParameter("aaa100");
        String sql="select * from aa10 where lower(aaa100)=lower('"+aaa100+"') order by aaa102 asc";
        return JSONArray.fromObject(commonDbUtil.query(sql)).toString();
    }

    public String saveCode9(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        String isnew=map.get("isnew").toString();
        int count=0;
        String tableName="aa09";
        if("-1".equals(isnew)){
            count=commonDbUtil.insertTableVales(map,tableName);
        }else{

        }
        return count>0?RtnType.SUCCESS:RtnType.FAILURE;
    }
}
