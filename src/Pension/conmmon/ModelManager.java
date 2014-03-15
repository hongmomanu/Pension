package Pension.conmmon;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-3-13
 * Time: 下午3:45
 * Desc: 管理LRServlet通过反射创建的对象
 */
public class ModelManager {
    private static final Logger log = Logger.getLogger(ModelManager.class);
    private static Map<String,Object> modelMap=new HashMap<String, Object>();

    public static synchronized void   addModel(String model,Object obj){
         modelMap.put(model,obj);
        log.info("当前model数量:"+modelMap.size()+"\n");
        log.info("当前model:"+model);
    }

    public static Object getModel(String model){
        return modelMap.get(model);
    }
}
