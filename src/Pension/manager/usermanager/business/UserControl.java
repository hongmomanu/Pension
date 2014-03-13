package Pension.manager.usermanager.business;

import Pension.conmmon.ComonDao;
import Pension.manager.usermanager.impl.UserImplement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 13-8-9
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class UserControl {
    private static String  UserTable="users";
    public String getUsers(int start,int limit,String keyword,String totalname,String rowsname){

        UserImplement user=new UserImplement();
        ArrayList list=user.getUsers(start, limit, keyword);

        if(totalname==null){
            return   JSONArray.fromObject(list).toString();
        }else{
            String sql="select count(*) from "+ UserTable;
            if(keyword!=null)sql+=" where username like '%"+keyword+"%'";
            ComonDao cd=new ComonDao();

            Map<String,Object> res=new HashMap<String, Object>();
            res.put(rowsname,list);
            res.put(totalname,cd.getTotalCountBySql(sql));
            return  JSONObject.fromObject(res).toString();
        }


    }
    public String EditUser(int userid,String username,String displayname,String password,boolean iscommon,String oldpassword){
        UserImplement user=new UserImplement();
        Map<String,Object> res=new HashMap<String, Object>();
        int resultid=user.editUser(userid,username,displayname,password,iscommon,oldpassword);
        if(resultid>=0)res.put("success",true);
        else res.put("success",false);
        return JSONObject.fromObject(res).toString();


    }
    public String addNewUser(String username,String password,int roleid,int divisionid,String displayname){
        UserImplement user=new UserImplement();
        Map<String,Object> res=new HashMap<String, Object>();
        int userid=user.addnewUser(username, password, roleid,divisionid,displayname);
        if(userid>=0)res.put("success",true);
        else res.put("success",false);
        return JSONObject.fromObject(res).toString();


    }
    public String delUser(int userid){
        UserImplement user=new UserImplement();
        Map<String,Object> res=new HashMap<String, Object>();
        int resultid=user.delUser(userid);
        if(resultid>=0)res.put("success",true);
        else res.put("success",false);
        return JSONObject.fromObject(res).toString();

    }
    public Map<String,Object> login(String username,String password){
        UserImplement user=new UserImplement();
        return user.login(username, password);

    }
}
