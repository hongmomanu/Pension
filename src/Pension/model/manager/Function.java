package Pension.model.manager;

import Pension.common.CommonDbUtil;
import Pension.common.MakeRandomString;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.*;

/**
 * User: Administrator
 * Date: 14-3-14
 * Time: 下午9:22
 */
public class Function {
    private HttpServletRequest request;
    private Connection conn;

    /*
    业务操作中的树,左边业务菜单的,根据人员权限的来显示
     */
    public String queryFunctionTree(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        StringBuffer sb=new StringBuffer();
        String node=request.getParameter("node");
        if(null==node){
            node=request.getParameter("id");
        }
        if(null==node||"".equals(node)||"root".equals(node)){
            node="businessmenu";
        }
        return query(commonDbUtil,node,"");
    }
    /*
    查询功能全部树
     */
    public String queryFunctionTreeMng(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        StringBuffer sb=new StringBuffer();
        String node=request.getParameter("node");
        if(null==node){
            node=request.getParameter("id");
        }
        if(null==node||"".equals(node)||"root".equals(node)){
            node="-1";
        }
        return query(commonDbUtil,node,null);
    }
    /*
    查询功能
     */
    public String queryFunctionById(){
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        StringBuffer sb=new StringBuffer();
        String node=request.getParameter("node");
        if(null==node){
            node=request.getParameter("id");
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
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        Map map=ParameterUtil.toMap(request);
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
        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);
        String functionid=request.getParameter("functionid");
        if(null!=functionid){
            commonDbUtil.execute("delete from xt_function where functionid='"+functionid+"'");
        }
        return RtnType.SUCCESS;
    }
}
