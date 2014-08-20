package Pension.model.manager;

import Pension.common.CommonDbUtil;
import Pension.common.RtnType;
import Pension.common.sys.annotation.OperationLog;
import Pension.model.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * User: Administrator
 * Date: 14-3-16
 * Time: 下午3:25
 */
public class Grant extends Model {

    public String saveRoleFunction(){  //批处理理操作,以后实现
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        int result=0;
        String functionidsOjb=this.getRequest().getParameter("functionids");
        String roleid=this.getRequest().getParameter("roleid");
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

    public String saveRoleUser(){  //批处理理操作,以后实现
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        int result=0;
        String roleidsObj=this.getRequest().getParameter("roleids");
        String userid=this.getRequest().getParameter("userid");
        if(null==userid){
            return RtnType.FAILURE;
        }
        String[] roleids=null;
        if(null!=roleidsObj){
            roleids=roleidsObj.split(",");
            commonDbUtil.execute("delete from xt_roleuser where userid='"+userid+"'");
            for(String r:roleids){
                String sql="insert into xt_roleuser(userid,roleid)values('"+userid+"','"+r+"')";
                System.out.println(sql);
                commonDbUtil.execute(sql);
            }
        }
        return RtnType.SUCCESS;
    }
}
