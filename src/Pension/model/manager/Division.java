package Pension.model.manager;

import Pension.common.AppException;
import Pension.common.CommonDbUtil;
import Pension.common.RtnType;
import Pension.model.Model;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: weipan
 * Date: 14-4-3
 * Time: 下午2:40
 */
public class Division extends Model {

    public int queryDivision() throws AppException {
        String userid=this.getRequest().getParameter("userid");
        Integer page=Integer.parseInt(this.getRequest().getParameter("page"));
        Integer rows=Integer.parseInt(this.getRequest().getParameter("rows"));

        String keyword=this.getRequest().getParameter("q");
        String sql="select t.* from division t where (t.dvcode like '"
                +keyword+"%' or t.dvname like '%"+keyword+"%') order by t.dvcode asc";
        this.query(sql,page,rows);
        return RtnType.NORMALSUCCESS;
    }
}
