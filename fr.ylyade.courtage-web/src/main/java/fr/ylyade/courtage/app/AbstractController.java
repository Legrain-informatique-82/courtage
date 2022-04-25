package fr.ylyade.courtage.app;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.ejb.FinderException;

import org.primefaces.context.RequestContext;

import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.service.interfaces.remote.ITaTStatutServiceRemote;


public abstract class AbstractController {
	protected ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();
	
	protected @EJB ITaTStatutServiceRemote taTStatutService;	
	

	protected String nomDocument;
	

	public void scrollToTop() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("window.scrollTo(0,0);");
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}
	public String getNomDocument() {
		return nomDocument;
	}

	public void setNomDocument(String nomDocument) {
		this.nomDocument = nomDocument;
	}
	public static String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;

	}
	

}
