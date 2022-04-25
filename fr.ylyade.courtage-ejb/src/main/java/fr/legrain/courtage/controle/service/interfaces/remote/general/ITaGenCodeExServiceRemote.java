package fr.legrain.courtage.controle.service.interfaces.remote.general;

import java.util.List;

import javax.ejb.Remote;

import fr.legrain.controle.dto.TaGenCodeExDTO;
import fr.legrain.controle.model.TaGenCodeEx;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.legrain.courtage.service.interfaces.remote.general.ITaGenCodeControlServiceRemote;

@Remote
public interface ITaGenCodeExServiceRemote extends IGenericCRUDServiceRemote<TaGenCodeEx,TaGenCodeExDTO>,
														IAbstractLgrDAOServer<TaGenCodeEx>,IAbstractLgrDAOServerDTO<TaGenCodeExDTO>,ITaGenCodeControlServiceRemote{
	public static final String validationContext = "GEN_CODE_EX";
	
	public String genereCodeExJPA(TaGenCodeEx genCodeEx, int rajoutCompteur, String section, String exo) throws Exception;
	public String genereCodeExJPA(int rajoutCompteur, String section, String exo) throws Exception;
	public void libereVerrouTout();
	public void libereVerrouTout(List<String> listeSessionID);
	public boolean rentreCodeGenere(TaGenCodeEx genCode, String code, String sessionID) throws Exception;
	public void annulerCodeGenere(TaGenCodeEx genCode, String code) throws Exception;
	public void annulerCodeGenere(TaGenCodeEx genCode, String code, boolean tous) throws Exception;
}
