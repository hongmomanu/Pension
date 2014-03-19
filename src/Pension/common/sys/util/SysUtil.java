package Pension.common.sys.util;

/**
 * User: Administrator
 * Date: 14-3-17
 * Time: 下午8:02
 */
public class SysUtil {
    private static final ThreadLocal tl = new ThreadLocal();
    public static void setCacheCurrentUser(CurrentUser paramCurrentUser)
    {
        tl.set(paramCurrentUser);
    }

    public static CurrentUser getCacheCurrentUser()
    {
        CurrentUser localCurrentUser = (CurrentUser)tl.get();
        return localCurrentUser;
    }
}
