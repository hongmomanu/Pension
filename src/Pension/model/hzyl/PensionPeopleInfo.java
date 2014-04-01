package Pension.model.hzyl;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-20
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class PensionPeopleInfo {
    private HttpServletRequest request;
    private Connection conn;

    public String save(){                 //老年基本信息保存方法

        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        int result=0;
        Long id=commonDbUtil.getSequence("seq_t_oldpeople");
        map.put("id",id);

        AuditManager.addAudit(id, "mHLcDiwTflgEshNKIiOV", "T_OLDPEOPLE",          //审核功能添加
                this.getClass().getName(), "没有摘要信息" + new Date().toString(),
                request.getSession().getAttribute("loginname").toString(),
                request.getSession().getAttribute("username").toString(),
                request.getSession().getAttribute("dvcode").toString()
        );
        //AuditManager.addAudit(id);
        result=commonDbUtil.insertTableVales(map,"t_oldpeople");
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    public String update(){                 //老年基本信息修改方法

        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map= ParameterUtil.toMap(request);
        int result=0;
        String lr_id = request.getParameter("lr_id");
        /*Long id=commonDbUtil.getSequence("seq_t_oldpeople");
        map.put("id",id);*/
        Map where=new HashMap();
        where.put("lr_id",lr_id);

        /*AuditManager.addAudit(id, "mHLcDiwTflgEshNKIiOV", "T_OLDPEOPLE",          //审核功能添加
                this.getClass().getName(), "没有摘要信息" + new Date().toString(),
                request.getSession().getAttribute("loginname").toString(),
                request.getSession().getAttribute("username").toString(),
                request.getSession().getAttribute("dvcode").toString()
        );*/
        //AuditManager.addAudit(id);
        result=commonDbUtil.updateTableVales(map,"t_oldpeople",where);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }
}
