package Pension.model.manager;

import Pension.common.*;
import Pension.common.sys.util.CurrentUser;
import Pension.common.sys.util.SysUtil;
import Pension.model.Model;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.*;

/**
 * User: Administrator
 * Date: 14-3-14
 * Time: 下午9:22
 */
public class Function extends Model {

    /*
    业务操作中的树,左边业务菜单的,根据人员权限的来显示
     */
    public String queryFunctionTree() throws AppException {
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        StringBuffer sb=new StringBuffer();
        String node=this.getRequest().getParameter("node");
        if(null==node){
            node=this.getRequest().getParameter("id");
        }
        if(null==node||"".equals(node)||"root".equals(node)){
            node="businessmenu";
        }
        CurrentUser user= SysUtil.getCacheCurrentUser();
        return query(commonDbUtil,node,user.getUserid());
    }
    /*
    查询功能全部树
     */
    public String queryFunctionTreeMng(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        StringBuffer sb=new StringBuffer();
        String node=this.getRequest().getParameter("node");
        if(null==node){
            node=this.getRequest().getParameter("id");
        }
        if(null==node||"".equals(node)||"root".equals(node)){
            node="-1";
        }
        return query(commonDbUtil,node,null);
        //return generateTotalTree();
    }
    /*
    查询功能
     */
    public String queryFunctionById(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        StringBuffer sb=new StringBuffer();
        String node=this.getRequest().getParameter("node");
        if(null==node){
            node=this.getRequest().getParameter("id");
        }
        String sql="select * from xt_function where functionid='"+node+"'";
        List list=commonDbUtil.query(sql);
        Map map=new HashMap();
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return JSONObject.fromObject(map).toString();
    }

    private String query(CommonDbUtil commonDbUtil,String node,String userid){
        String sql="select t.*,(select count(1) from xt_function where parent=t.functionid) leafcount from xt_function t where t.parent='"+node+"'";
        if(userid!=null){
            sql+=" and t.nodetype <> '2' and t.functionid in (select rf.functionid from xt_roleuser ru,xt_rolefunc rf where ru.userid='"+userid+
                    "' and ru.roleid=rf.roleid)";
        }
        sql+=" order by t.orderno asc";
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        List querylist=commonDbUtil.query(sql);
        Iterator iterator=querylist.iterator();
        while(iterator.hasNext()){
            Map map=(Map)iterator.next();
            boolean isleaf=map.get("nodetype").equals("1")?false:true;
            map.put("id",map.get("functionid"));
            map.put("text",map.get("title"));
            map.put("leaf",isleaf);
            map.put("value",map.get("location"));
            if(isleaf){
                map.put("state","open");
                map.put("leafcount",map.get("leafcount"));//子类的数量
            }else{
                map.put("state","closed");
            }
            list.add(map);
        }

        String json= JSONArray.fromObject(list).toString();
        return json;
    }

    public String saveFunction(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map=ParameterUtil.toMap(this.getRequest());
        int result=0;
        if("-1".equals(map.get("functionid"))){
            map.put("functionid", MakeRandomString.genString());
            result=commonDbUtil.insertTableVales(map,"xt_function");
        }else{
            Map m=new HashMap();
            m.put("functionid",map.get("functionid"));
            result=commonDbUtil.updateTableVales(map,"xt_function",m);
        }
        return result>0?RtnType.SUCCESS:RtnType.FAILURE;
    }
    public String deleteFunction(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        String functionid=this.getRequest().getParameter("functionid");
        if(null!=functionid){
            commonDbUtil.execute("delete from xt_function where functionid='"+functionid+"'");
        }
        return RtnType.SUCCESS;
    }

    private String generateTotalTree(){

        String sql="select functionid,parent,title from xt_function";
        sql="select t.functionid id,t.title text,t.functionid,t.parent from xt_function t " +
                " start with parent='totalroot'" +
                " connect by prior functionid=parent";
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        List list=commonDbUtil.query(sql);
        JSONArray jsonArray=JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        return TreeGenerator.generate(JSONArray.fromObject(list),"functionid","parent","totalroot").toString();
    }

    public void setTest2(){
        String s=getRequest().getParameter("name");
        System.out.println(s);
        this.setTest(s+" by weipan");
    }
    private String getSql(String node){
        String sql="select t.functionid,\n" +
                "       t.functionid id,\n" +
                "       t.location,\n" +
                "       t.location value,\n" +
                "       t.title,\n" +
                "       t.title text,\n" +
                "       t.parent,\n" +
                "       t.orderno,\n" +
                "       t.nodetype,\n" +
                "       t.type,\n" +
                "       decode(t.nodetype, '1', 'false', 'true') leaf,\n" +
                "       decode(t.nodetype, '1', 'open', 'closed') leaf\n" +
                "  from xt_function t where t.parent='"+node+"'  order by t.orderno asc";
        return sql;
    }

}
