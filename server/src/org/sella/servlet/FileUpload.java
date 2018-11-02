package org.sella.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * Servlet implementation class FileUpload
 */ 
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

    
   
    
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcessUpload(request, response);
	}
	
	private void doProcessUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/plain;charset=UTF-8"); 
        PrintWriter out = response.getWriter();
        try {
            // first check if the upload request coming in is a multipart request
            boolean isMultipart = org.apache.tomcat.util.http.fileupload.FileUpload.isMultipartContent(request);
            log("content-length: " + request.getContentLength());
            log("method: " + request.getMethod());
            log("character encoding: " + request.getCharacterEncoding());
 
            if (isMultipart) {
                DiskFileUpload upload = new DiskFileUpload();
                List items = null;
 
                try {
                    // parse this request by the handler
                    // this gives us a list of items from the request
                    items = upload.parseRequest(request);
                    log("items: " + items.toString());
                } catch (FileUploadException ex) {
                    log("Failed to parse request", ex);
                }
                Iterator itr = items.iterator();
 
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
 
                    // check if the current item is a form field or an uploaded file
                    if (item.isFormField()) {
 
                        // get the name of the field
                        String fieldName = item.getFieldName();
 
                        // if it is name, we can set it in request to thank the user
                        if (fieldName.equals("name")) {
                            out.print("Thank You: " + item.getString());
                        }
 
                    } else {
 
                        // the item must be an uploaded file save it to disk. Note that there
                        // seems to be a bug in item.getName() as it returns the full path on
                        // the client's machine for the uploaded file name, instead of the file
                        // name only. To overcome that, I have used a workaround using
                        // fullFile.getName().
                        File fullFile = new File(item.getName());
                        File savedFile = new File(getServletContext().getRealPath("/"), fullFile.getName());
                        try {
                            item.write(savedFile);
                        } catch (Exception ex) {
                            log("Failed to save file", ex);
                        }
                    }
                }
 
            }
        } finally {
            out.close();
        }
	}

}
