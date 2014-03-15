package Pension.model.manager;

import Pension.conmmon.CommonDbUtil;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    业务操作中的树
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
        return query(commonDbUtil,node);
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
        return query(commonDbUtil,node);
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
        return query(commonDbUtil,node);
    }

    private String query(CommonDbUtil commonDbUtil,String node){
        String sql="select t.*,(select count(1) from jet_function where parent=t.functionid) leafcount from jet_function t where t.parent='"+node+"'";
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
}
