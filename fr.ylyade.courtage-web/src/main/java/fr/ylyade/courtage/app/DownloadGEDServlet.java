package fr.ylyade.courtage.app;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;



@WebServlet("/GEDServlet/*")
public class DownloadGEDServlet extends HttpServlet {

	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3964991306018131904L;
	
	@EJB ITaGedUtilisateurServiceRemote service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer id = Integer.valueOf(request.getPathInfo().substring(1));

        TaGedUtilisateurDTO l ;
		try {
			l = service.findByIdDTO(id);
		
//	        response.setContentType(getServletContext().getMimeType(l.getTypeMime()));
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition","attachment;filename=" + l.getNomFichier());
	        
	        response.setContentLength(l.getFichierDoc().length);
	        response.getOutputStream().write(l.getFichierDoc());
        
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}