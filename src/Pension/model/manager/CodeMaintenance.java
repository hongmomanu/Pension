package Pension.model.manager;

import Pension.common.AppException;
import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.model.Model;
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
public class CodeMaintenance extends Model {
    public int queryCode9() throws AppException {
        Integer page=Integer.parseInt(this.getRequest().getParameter("page"));
        Integer rows=Integer.parseInt(this.getRequest().getParameter("rows"));
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        String sql="select * from xt_combo order by aaa100 asc";
        this.query(sql,page,rows);
        return RtnType.NORMALSUCCESS;
    }

    public String queryCode10(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        String aaa100=this.getRequest().getParameter("aaa100");
        String sql="select * from xt_combodt where lower(aaa100)=lower('"+aaa100+"') order by aaa102 asc";
        return JSONArray.fromObject(commonDbUtil.query(sql)).toString();
    }

    public String saveCode9(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map= ParameterUtil.toMap(this.getRequest());
        String isnew=map.get("isnew").toString();
        int count=0;
        String tableName="xt_combo";
        if("-1".equals(isnew)){
            count=commonDbUtil.insertTableVales(map,tableName);
        }else{

        }
        return count>0?RtnType.SUCCESS:RtnType.FAILURE;
    }
}
