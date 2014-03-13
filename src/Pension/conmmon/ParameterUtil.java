package Pension.conmmon;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: weipan
 * Date: 14-1-13
 * Time: 下午8:45
 * Desc: 参数工具,主要是 HttpServletRequest 请求的信息
 */
public class ParameterUtil {

    /*
     将HttpServletRequest中的信息转成Map
     */
    public static Map toMap(HttpServletRequest request){
        Map<String,Object> params=new HashMap<String,Object>();
        Enumeration e  =(Enumeration) request.getParameterNames();
        while(e.hasMoreElements()){
            String  parName=(String)e.nextElement();
            params.put(parName,request.getParameter(parName));
        }
        return params;
    }

    /*
     将JSONObject中的信息转成Map
     */
    public Map<String,Object> toMap(JSONObject jsonObj){
        Iterator<String> it=jsonObj.keys();
        Map<String,Object> map=new HashMap<String,Object>();
        while(it.hasNext()){
            String name=it.next();
            map.put(name,jsonObj.get(name));
        }
        return map;
    }
}
