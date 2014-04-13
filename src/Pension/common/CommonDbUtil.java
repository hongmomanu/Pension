package Pension.common;

import Pension.common.db.BaseDbQuery;
import Pension.common.db.BaseDbUtil;
import Pension.common.db.DbUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * User: weipan
 * Date: 14-3-13
 * Time: 上午11:50
 */
public class CommonDbUtil {

    private BaseDbUtil baseDbUtil=null;
    private BaseDbQuery baseDbQuery=null;
    public CommonDbUtil(Connection conn){
        baseDbUtil=new BaseDbUtil(conn);
        baseDbQuery=new BaseDbQuery(conn);
    }
    public CommonDbUtil(){
        Connection conn= DbUtil.get();
        baseDbUtil=new BaseDbUtil(conn);
        baseDbQuery=new BaseDbQuery(conn);
    }
    /*
    插入数据col_vals到tablename
     */
    public int insertTableVales(Map<String, Object> col_vals, String tablename) {
        return baseDbUtil.insertTableVales(col_vals, tablename);
    }

    /*
    更新表数据
    @param col_vals 数据
    @param tablename 表名
    @param colnames 条件数据

     */
    public int updateTableVales(Map<String, Object> col_vals, String tablename, Map<String, String> colnames) {
        return baseDbUtil.updateTableVales(col_vals, tablename, colnames);
    }

    /*
    判断查询结果是否有结果
     */
    public boolean isExist(String sql) {
        return baseDbUtil.isExist(sql);
    }

    /*执行一个sql语句*/
    public void execute(String sql) {
        baseDbUtil.execute(sql);
    }

    //查询SEQUENCE的下一个值
    public Long getSequence(String sequence){
        return baseDbQuery.querySequence(sequence);
    }

    //查询,返回List<Map>
    public List<Map<String,Object>> query(String sql){
        return baseDbQuery.query(sql);
    }
}
