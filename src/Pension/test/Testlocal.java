package Pension.test;

import java.util.Map;

/**
 * User: Administrator
 * Date: 14-4-14
 * Time: 上午9:02
 */
public class Testlocal {
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public void setLocal(String str){
        local.set(str);
    }
    public String getLocal(){
        return local.get();
    }

    public static void main(String[] args) {
        Testlocal t1=new Testlocal();
        t1.setLocal("eeeeeeeeee");
        System.out.println(t1.getLocal());
        Testlocal t2=new Testlocal();
        System.out.println(t2.getLocal());

        Testlocal t3=new Testlocal();
        System.out.println(t3.getLocal());
        t1.setLocal("eeeee222eeeee");
        System.out.println(t3.getLocal());
    }
}
