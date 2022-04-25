package fr.ylyade.courtage.app;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;


@WebServlet(
        name = "BdgEditionServlet",
        urlPatterns = {"/edition"}
)
public class EditionServlet extends HttpServlet {

	private static final long serialVersionUID = -9075718678533434258L;
	

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	Integer id = Integer.valueOf(request.getPathInfo().substring(1));
//        TaServiceWebExterne l;
		try {
//			String f = getServletConfig().getInitParameter("fichier");
			String f = request.getParameter("fichier");
			f = URLDecoder.decode(f, "UTF-8");
			 
//			l = service.findById(id);
			byte[] a = IOUtils.toByteArray(new FileInputStream(f));
		
	       // response.setContentType(getServletContext().getMimeType(l.getCodeServiceWebExterne()));
	        response.setContentLength(a.length);
	        response.getOutputStream().write(a);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}