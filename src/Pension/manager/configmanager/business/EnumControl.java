package Pension.manager.configmanager.business;

import Pension.common.ComonDao;
import Pension.manager.configmanager.impl.EnumImplement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 13-8-16
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
public class EnumControl {
    private static String Enum_Table="enumerate";
    public String getEnums(int start,int limit,String keyword,String totalname,String rowsname){

        EnumImplement myenum= new EnumImplement();
        ArrayList list=myenum.getEnums(start, limit, keyword);
        if(totalname==null){
            return JSONArray.fromObject(list).toString();
        }
        else{
            String sql="select count(*) from "+ Enum_Table;
            if(keyword!=null)sql+=" where enumeratelabel like '%"+keyword+"%'";
            ComonDao cd=new ComonDao();
            Map<String,Object> res=new HashMap<String, Object>();
            res.put(rowsname,list);
            res.put(totalname,cd.getTotalCountBySql(sql));
            return  JSONObject.fromObject(res).toString();
        }


    }

    public String getEnumsBytype(String type){
        EnumImplement myenum= new EnumImplement();
        return JSONArray.fromObject(myenum.getEnumsByType(type)).toString();

    }

    public String addEnum(String label,String value,String type){
        EnumImplement myenum= new EnumImplement();
        Map<String,Object> res=new HashMap<String, Object>();
        int enumid=myenum.addEnum(label,value,type);
        if(enumid>=0)res.put("success",true);
        else res.put("success",false);
        return JSONObject.fromObject(res).toString();
    }
}
