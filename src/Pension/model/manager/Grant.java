package Pension.model.manager;

import Pension.common.CommonDbUtil;
import Pension.common.RtnType;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * User: Administrator
 * Date: 14-3-16
 * Time: 下午3:25
 */
public class Grant {
    private HttpServletRequest request;
    private Connection conn;

    public String save(){  //批处理理操作,以后实现
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        int result=0;
        String functionidsOjb=request.getParameter("functionids");
        String roleid=request.getParameter("roleid");
        if(null==roleid){
            return RtnType.FAILURE;
        }
        String[] functionids=null;
        if(null!=functionidsOjb){
            functionids=functionidsOjb.split(",");
            commonDbUtil.execute("delete from xt_rolefunc where roleid='"+roleid+"'");
            for(String f:functionids){
                String sql="insert into xt_rolefunc(ROLEID,FUNCTIONID)values('"+roleid+"','"+f+"')";
                System.out.println(sql);
                commonDbUtil.execute(sql);
            }
        }
        return RtnType.SUCCESS;
    }
}
