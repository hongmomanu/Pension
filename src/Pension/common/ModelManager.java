package Pension.common;

import Pension.model.Model;
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


    public static synchronized Object   addModel(String model) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(null==modelMap.get(model)){
            Class c=Class.forName(model);
            modelMap.put(model,c.newInstance());
            return modelMap.get(model);
        }
        return modelMap.get(model);
    }

    public static Object getModel(String model){
        Object o= modelMap.get(model);
        boolean casch=true;
        if(null==o){
            try {
                if(casch){
                    o=addModel(model);
                }else{
                    return  Class.forName(model).newInstance();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return o;
    }
}
