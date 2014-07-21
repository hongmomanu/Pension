package Pension.serverlet;

import Pension.common.Config;
import Pension.common.FileHelper;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class UploadServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> fileitems = null;
        Map<String,Object> result=null;
        try {
            fileitems = upload.parseRequest(req);
            FileHelper fh=new FileHelper();
            ServletContext context = req.getServletContext();
            Config dbconfig = Config.getConfig("config.properties");
            String filedir = dbconfig.getValue("uploaddirname");
            String filepath=context.getRealPath(filedir);
            result=fh.saveUploadFile(fileitems, filepath);
            result.put("filepath",filedir+"/"+result.get("filepath"));
        } catch (FileUploadException e) {
            e.printStackTrace();

        }
        PrintWriter out=resp.getWriter();
        out.print(JSONObject.fromObject(result).toString());
        out.close();*/
    }
}
