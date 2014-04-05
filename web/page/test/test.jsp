<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html>
<head>
    <meta charset="utf-8">
    <title>测试</title>
    <style>
        form {
            margin: 0;
        }
        textarea {
            display: block;
        }
    </style>
    <link rel="stylesheet" href="http://localhost/kindeditor/themes/default/default.css">
    <script charset="utf-8" src="http://localhost/kindeditor/kindeditor-min.js"></script>
    <script charset="utf-8" src="http://localhost/kindeditor/lang/zh_CN.js"></script>
    <script>
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="content"]', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                allowImageUpload : false,
                items2 : [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', '|', 'emoticons', 'image', 'link']
            });
        });
    </script>
</head>
<body>
<textarea name="content">
<pre class="prettyprint lang-java">package Pension.model.hzyl;import Pension.common.CommonDbUtil;import Pension.common.MakeRandomString;import Pension.common.ParameterUtil;import Pension.common.RtnType;import Pension.common.sys.audit.AuditBean;import Pension.common.sys.audit.AuditManager;import Pension.common.sys.audit.IMultilevelAudit;import net.sf.json.JSONArray;import net.sf.json.JSONObject;import javax.servlet.http.HttpServletRequest;import java.sql.Connection;import java.util.Date;import java.util.HashMap;import java.util.List;import java.util.Map;public class EvaluateLrInfo implements IMultilevelAudit {    private HttpServletRequest request;    private Connection conn;    public String save(){        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);        Map map= ParameterUtil.toMap(request);        String digest=request.getParameter("name")+                request.getParameter("identityid")+                request.getParameter("registration");        int result=0;        Long id=commonDbUtil.getSequence("SEQ_T_NEEDASSESSMENT");        map.put("pg_id",id);        String tablename="t_needassessment";        AuditManager.addAudit(id, "wJhlMNIq8C20mH7Bm6tj", tablename,                this.getClass().getName(), digest,                request.getSession().getAttribute("loginname").toString(),                request.getSession().getAttribute("username").toString(),                request.getSession().getAttribute("dvcode").toString()                );        result=commonDbUtil.insertTableVales(map,tablename);        return result&gt;0? RtnType.SUCCESS:RtnType.FAILURE;    }    public String query(){        CommonDbUtil commonDbUtil=new CommonDbUtil(conn);        Integer page=Integer.parseInt(request.getParameter("page"));        Integer rows=Integer.parseInt(request.getParameter("rows"));        Map map= new HashMap();        String sql="select o.name,o.identityid,o.birthd,o.gender,o.age,o.nation,o.address,o.type,o.registration" +                ",n.* from t_needassessment n,t_oldpeople o where o.lr_id=n.lr_id";        List list=commonDbUtil.query("SELECT * FROM (SELECT tt.*, ROWNUM ro FROM ("+sql+") tt WHERE ROWNUM &lt;="+(page)*rows+") WHERE ro &gt; "+(page-1)*rows);        int count=commonDbUtil.query(sql).size();        map.put("total",count);        map.put("rows",list);        return JSONObject.fromObject(map).toString();    }    /*审核成功后的回调    * 设置评估信息的状态为有效    * */    @Override    public Long audit(Connection conn,AuditBean auditBean) {        Long pg_id=Long.parseLong(auditBean.getTprkey());        CommonDbUtil dbUtil=new CommonDbUtil(conn);        String sql="update t_needassessment set active='1' where pg_id="+pg_id;        dbUtil.execute(sql);        return null;    }}</pre>
</textarea>
</body>
</html>
