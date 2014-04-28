package Pension.model.pension;

import Pension.common.CommonDbUtil;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.common.sys.audit.AuditBean;
import Pension.common.sys.audit.AuditManager;
import Pension.common.sys.audit.IMultilevelAudit;
import Pension.model.Model;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-20
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class PensionPeopleInfo extends Model implements IMultilevelAudit {

    public String save(){                 //老年基本信息保存方法
        int result=0;
        CommonDbUtil commonDbUtil=new CommonDbUtil();

        Map map= ParameterUtil.toMap(this.getRequest());                                                //获取提交过来的结果

        String gxmess = map.get("p1").toString();                                                  //获得老年关系人员数据
        JSONArray ga = JSONArray.fromObject(gxmess);                                 //转换成JSON数据

        String briefmessage = map.get("name").toString()+","+map.get("gender").toString()+",出生于"+map.get("birthd").toString()+",原籍"+map.get("registration").toString()+",现居住"+map.get("address").toString();


        Long id=commonDbUtil.getSequence("seq_t_oldpeople");                //创建唯一标识
        map.put("lr_id",id);                                                                                 //存放到老年基础信息表里

        for(int i=0;i<ga.size();i++){                                                                     //循环将多个老年关系数据存入数据库
           Map m = ParameterUtil.toMap(JSONObject.fromObject(ga.get(i)))  ;
            m.put("lr_id",id) ;                                                                                      //将唯一标识存入关系表里
            commonDbUtil.insertTableVales(m ," T_OLDSOCREL");                          //将数据存入数据库中
        }



        AuditManager.addAudit(id);                                                                    //审核
        //AuditManager.addAudit(id);
        result=commonDbUtil.insertTableVales(map,"t_oldpeople");                     //将老年基础数据存入数据库中
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }

    /*public String update(){                 //老年基本信息修改方法
        int result=0;
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String lr_id = request.getParameter("lr_id");             //获取唯一标识id

        String delsql = "DELETE FROM T_OLDSOCREL WHERE lr_id = '"+lr_id+"'";       //删除原先的关系数据 sql
        commonDbUtil.execute(delsql);                                                                                  // 执行sql语句

        Map where=new HashMap();                                 //
        where.put("lr_id",lr_id);                                            //将唯一标识存入条件中

        Map map= ParameterUtil.toMap(request);             //获取传过来的数据

        String gxmess = map.get("p1").toString();                   //获取老年关系数据
        JSONArray ga = JSONArray.fromObject(gxmess);
        for(int i=0;i<ga.size();i++){
            Map mc = ParameterUtil.toMap(JSONObject.fromObject(ga.get(i)))  ;
            mc.put("lr_id",lr_id);
            commonDbUtil.insertTableVales(mc,"T_OLDSOCREL");                //将新数据插入进去
        }*/

        //String lr_id = request.getParameter("lr_id");
        /*Long id=commonDbUtil.getSequence("seq_t_oldpeople");
        map.put("id",id);*/
       // Map where=new HashMap();
        //where.put("lr_id",lr_id);

        /*AuditManager.addAudit(id, "mHLcDiwTflgEshNKIiOV", "T_OLDPEOPLE",          //审核功能添加
                this.getClass().getName(), "没有摘要信息" + new Date().toString(),
                request.getSession().getAttribute("loginname").toString(),
                request.getSession().getAttribute("username").toString(),
                request.getSession().getAttribute("dvcode").toString()
        );*/
        //AuditManager.addAudit(id);
       /* result=commonDbUtil.updateTableVales(map,"t_oldpeople",where);
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }*/

   /* public String setGxDate(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        List<Map<String,Object>> gxlist = new ArrayList<Map<String,Object>>();
        String lr_id = request.getParameter("lr_id");
        String gxsql = "SELECT * FROM T_OLDSOCREL WHERE lr_id = "+lr_id;
        gxlist = commonDbUtil.query(gxsql);
        return JSONArray.fromObject(gxlist).toString();                                                   //return JSONArray.fromObject(commonDbUtil.query(sql)).toString()
    }*/

    @Override
    public Long audit(AuditBean auditBean) {                            //审核
        Long lr_id=Long.parseLong(auditBean.getTprkey());
        CommonDbUtil dbUtil=new CommonDbUtil();
        String sql="update T_OLDPEOPLE set active='1' where lr_id="+lr_id;
        dbUtil.execute(sql);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
