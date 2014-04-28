package Pension.test;

import Pension.common.db.DbUtil;
import Pension.jdbc.JdbcFactory;
import junit.framework.TestCase;

/**
 * User: Administrator
 * Date: 14-4-12
 * Time: 下午10:02
 */
public class TestThreadLocal extends TestCase{

    public void test001(){
        for(int i=0;i<100;i++){
            DbUtil.get();

        }
    }
    public void test002(){
        for(int i=0;i<100;i++){
            JdbcFactory.getConn();
        }
    }

    public void test003(){
        new A().test();
        new B().test();
        new B().test();
    }
    public void test004() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
       Class clz=Class.forName("Pension.test.C");
        T t=(T)clz.newInstance();
        t.test();
    }
    public void test005() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for(int i=0;i<100;i++){
            this.test004();
        }
    }
}


class A{
    public void test(){
        DbUtil.get();
    }
}
class B{
    public void test(){
        DbUtil.get();
    }
}