package Pension.manager.configmanager.business;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-18
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class GetMessageControl {
    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    public ArrayList<Map<String, Object>> getEnumsBytype(String type){
        if(type=="relation") {
            Map<String,Object> obj=new HashMap<String, Object>();
            obj.put("label","夫妻");
            obj.put("value","夫妻");
            list.add(obj);
        } else if(type =="sex"){
            Map<String,Object> obj=new HashMap<String, Object>();
            obj.put("label","男");
            obj.put("value","男");
            list.add(obj);
        }


        return list;
    }
}
