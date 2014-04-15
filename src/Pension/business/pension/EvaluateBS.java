package Pension.business.pension;

import Pension.common.CommonDbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-14
 * Time: 下午7:48
 */
public class EvaluateBS {
    CommonDbUtil commonDbUtil=new CommonDbUtil();
    public Object findLrBaseInfoById(Integer lr_id){
        String sql="SELECT * FROM T_OLDPEOPLE WHERE lr_id="+lr_id;
        List list=commonDbUtil.query(sql);
        Map map=null;
        if(list.size()>0){
            map=(Map)list.get(0);
        }
        return map;
    }
}
