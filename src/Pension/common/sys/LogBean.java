package Pension.common.sys;


import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-17
 * Time: 下午1:09
 */
public class LogBean {
    private static final ThreadLocal<Map> localMap = new ThreadLocal<Map>();

    public  void  setLocalLog(Map map){
        localMap.set(map);
    }
    public  Map getLocalLog(){
        return localMap.get();
    }
}
