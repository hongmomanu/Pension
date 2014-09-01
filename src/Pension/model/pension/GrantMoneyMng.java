package Pension.model.pension;

import Pension.common.AppException;
import Pension.common.CommonDbUtil;
import Pension.common.IParam;
import Pension.common.RtnType;
import Pension.model.Model;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-17
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class GrantMoneyMng extends Model {

    /*
    当月待发放数据查询和其他月需要补发数据
     */
    public int queryNeedGrant() throws AppException {
        Integer page=Integer.parseInt(getRequest().getParameter(IParam.PAGE));
        Integer rows=Integer.parseInt(getRequest().getParameter(IParam.ROWS));
        String grantType=getRequest().getParameter("granttype");
        String bsnyue=getRequest().getParameter("bsnyue");
        String sql="select o.name,o.identityid,n.* from t_needassessment n,t_oldpeople o  where n.lr_id=o.lr_id ";
        if(grantType==null||"0".equals(grantType)){
           sql+=" and not exists( select 1 from t_grantmoney where pg_id=n.pg_id and bsnyue= TO_NUMBER('"+bsnyue+"', '999999'))";
        }
        this.query(sql,page,rows);
        return RtnType.NORMALSUCCESS;
    }

    public int query() throws AppException {
        Integer page=Integer.parseInt(getRequest().getParameter(IParam.PAGE));
        Integer rows=Integer.parseInt(getRequest().getParameter(IParam.ROWS));
        String sql="select g.grantid,o.name,o.identityid,o.birthd,o.gender,o.age,o.nation,o.address,o.type,o.registration,g.money,g.bsnyue" +
                ",n.* from t_needassessment n,t_oldpeople o,t_grantmoney g where o.lr_id=n.lr_id and g.pg_id=n.pg_id order by n.pg_id desc";
        this.query(sql, page, rows);
        return RtnType.NORMALSUCCESS;
    }
    public int delete(){
        CommonDbUtil dbUtil=new CommonDbUtil();
        dbUtil.execute("delete from t_grantmoney where grantid="+getRequest().getParameter("grantid"));
        return RtnType.NORMALSUCCESS;
    }
    public int save(){
        String grantType=getRequest().getParameter("granttype");
        String grantData=getRequest().getParameter("grantdata");
        String money=getRequest().getParameter("money");
        if(money==null||money.equals("")){
            money="0";
        }
        String grantids="";
        List<Integer[]> listData=new ArrayList<Integer[]>();
        if(null!=grantData&&!"".equals(grantData)){
            JSONArray ja=JSONArray.fromObject(grantData);
            for(int i=0;i<ja.size();i++){
                JSONObject jo=JSONObject.fromObject(ja.get(i));
                if(!grantids.equals("")){
                    grantids+=",";
                }
                grantids+=jo.getString("pg_id");
                Integer[] a={jo.getInt("pg_id"),jo.getInt("money")};
                listData.add(a);
            }
        }
        System.out.println(grantids);
        String bsnyue=getRequest().getParameter("bsnyue");
        CommonDbUtil dbUtil=new CommonDbUtil();
        if("regrant".equals(grantType)){ //重新发放
            String delsql="delete from t_grantmoney where bsnyue= TO_NUMBER('"+bsnyue+"', '999999')";
            if(grantids.length()>0){
                delsql+=" and pg_id in ("+grantids+")";
            }
           dbUtil.execute(delsql);
        }
        //List existslist=dbUtil.query("select pg_id from t_grantmoney where bsnyue= TO_NUMBER('"+bsnyue+"', '999999')");
        if(grantids.length()>0){
            Iterator it=listData.iterator();
            while(it.hasNext()){
                Integer[] a=(Integer[])it.next();
                dbUtil.execute("insert into t_grantmoney(grantid,pg_id,money,bsnyue,granttime)values(SEQ_GRANTMONEY.nextval,"+a[0]+","+a[1]+","+bsnyue+",sysdate)");
            }
        }else{
            List needGrantList=dbUtil.query("select n.pg_id from t_needassessment n where (n.active is null or n.ACTIVE<>'2') " +
                    "and not exists (select 1 from t_grantmoney where pg_id=n.pg_id and bsnyue="+bsnyue+")");
            Iterator it=needGrantList.iterator();
            while(it.hasNext()){
                Integer pg_id=(Integer)it.next();
                dbUtil.execute("insert into t_grantmoney(grantid,pg_id,money,bsnyue,granttime)values(SEQ_GRANTMONEY.nextval,"+pg_id+","+money+","+bsnyue+",sysdate)");
            }
        }

        return RtnType.NORMALSUCCESS;
    }
}
