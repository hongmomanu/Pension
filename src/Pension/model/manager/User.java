package Pension.model.manager;


import Pension.common.CommonDbUtil;
import Pension.common.MakeRandomString;
import Pension.common.ParameterUtil;
import Pension.common.RtnType;
import Pension.model.Model;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.*;

public class User extends Model {


    /*
    业务操作中的树
     */
    public String queryDivisionTree(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        StringBuffer sb=new StringBuffer();
        String node=this.getRequest().getParameter("node");
        if(null==node){
            node=this.getRequest().getParameter("id"); //如果node没有值,则取id的值
        }
        if(null==node||"".equals(node)||"root".equals(node)){
            node="330100"; //浙江省杭州市
        }
        return query(commonDbUtil,node);
    }

    public String queryUser(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        StringBuffer sb=new StringBuffer();
        String dvcode=this.getRequest().getParameter("dvcode");
        if(null==dvcode||"".equals(dvcode)){
            dvcode="330100"; //浙江省杭州市
        }
        String sql="select u.*,d.* from xt_user u,division d where d.dvcode=u.regionid and u.regionid='"+dvcode+"'";
        return JSONArray.fromObject(commonDbUtil.query(sql)).toString();
    }

    public String saveUser(){
        CommonDbUtil commonDbUtil=new CommonDbUtil();
        Map map= ParameterUtil.toMap(this.getRequest());
        //map.put("createdate",) 指定时间
        int result=0;
        if("-1".equals(map.get("userid"))){
            map.put("userid", MakeRandomString.genString());
            result=commonDbUtil.insertTableVales(map,"xt_user");
        }else{
            Map m=new HashMap();
            m.put("userid",map.get("userid"));
            result=commonDbUtil.updateTableVales(map,"xt_user",m);
        }
        return result>0? RtnType.SUCCESS:RtnType.FAILURE;
    }


    private String query(CommonDbUtil commonDbUtil,String node){
        String sql="select t.dvcode,t.dvcode id,t.dvname,t.dvname text,(select count(1) from division where dvhigh=t.dvcode) leafcount from division t where t.dvhigh='"+node+"'";
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        List querylist=commonDbUtil.query(sql);
        Iterator iterator=querylist.iterator();
        while(iterator.hasNext()){
            Map map=(Map)iterator.next();
            boolean isleaf=(map.get("leaf")+"").equals("0")?true:false;
            map.put("leaf",isleaf);
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
