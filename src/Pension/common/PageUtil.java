package Pension.common;


import org.hibernate.repackage.cglib.beans.BeanGenerator;
import org.hibernate.repackage.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * User: weipan
 * Date: 14-1-23
 * Time: 下午2:28
 * Desc: 页面元素属性复制工具,把map对象属性复制到实例对象上
 */
public class PageUtil {


    /*
     * @param  Object map
     * @param  Object dest 实体对象
     */
    public static void CopyProperties(Map map, Object dest)throws Exception {
        CopyProperties(copyHtmlEleToObj(map),dest);
    }

    /*
     * @param  Object source
     * @param  Object dest 实体对象
     */
    public static void CopyProperties(Object source, Object dest)throws Exception {
        //获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try{
            for(int i=0;i<destProperty.length;i++){
                Class localClass = destProperty[i].getPropertyType();
                for(int j=0;j<sourceProperty.length;j++){

                    if(destProperty[i].getName().equals(sourceProperty[j].getName())){
                        //调用source的getter方法和dest的setter方法
                        /*destProperty[j].getWriteMethod().invoke(dest,
                                sourceProperty[i].getReadMethod().invoke(source));*/
                        copyByType(localClass, destProperty[i],dest, sourceProperty[j].getReadMethod().invoke(source)+"");
                        break;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("属性复制失败:"+e.getMessage());
        }
    }


    private static void copyByType(Class localClass,PropertyDescriptor pd,Object dest,String str)
            throws InvocationTargetException, IllegalAccessException, AppException {

        if (localClass.equals(Date.class))
        {
            if (str.length() <= 10)
                pd.getWriteMethod().invoke(dest, DateUtil.parseDate(str));
            else
                pd.getWriteMethod().invoke(dest, DateUtil.parseDateTime(str) );
        }
        else if (localClass.equals(String.class))
            pd.getWriteMethod().invoke(dest, str);
        else if ((localClass == Integer.class) || (localClass == Integer.TYPE))
            pd.getWriteMethod().invoke(dest, str.trim().equals("") ? null : Integer.valueOf(Integer.parseInt(str)) );
        else if ((localClass == Short.class) || (localClass == Short.TYPE))
            pd.getWriteMethod().invoke(dest, str.trim().equals("") ? null : Short.valueOf(Short.parseShort(str)));
        else if ((localClass == Long.class) || (localClass == Long.TYPE))
            pd.getWriteMethod().invoke(dest, str.trim().equals("") ? null : Long.valueOf(Long.parseLong(str)) );
        else if ((localClass == Float.class) || (localClass == Float.TYPE))
            pd.getWriteMethod().invoke(dest, str.trim().equals("") ? null : Float.valueOf(Float.parseFloat(str)) );
        else if ((localClass == Double.class) || (localClass == Double.TYPE))
            pd.getWriteMethod().invoke(dest, str.trim().equals("") ? null : Double.valueOf(Double.parseDouble(str)) );
    }


    private static Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext();) {
            String key = (String) i.next();
            generator.addProperty(key,String.class);
        }
        return generator.create();
    }

    /*
    复制map中的数据到对象属性中
     */
    private static Object copyHtmlEleToObj(Map propertyMap){
        Object object=generateBean(propertyMap);
        Set keySet = propertyMap.keySet();
        BeanMap beanMap= BeanMap.create(object);
        for (Iterator i = keySet.iterator(); i.hasNext();) {
            String key = (String) i.next();
            beanMap.put(key,propertyMap.get(key));
        }
        return object;
    }
}
