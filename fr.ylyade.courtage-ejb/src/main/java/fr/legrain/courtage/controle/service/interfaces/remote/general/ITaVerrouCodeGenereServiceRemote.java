package fr.legrain.courtage.controle.service.interfaces.remote.general;

import javax.ejb.Remote;

import fr.legrain.controle.dto.TaVerrouCodeGenereDTO;
import fr.legrain.controle.model.TaVerrouCodeGenere;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;

@Remote
public interface ITaVerrouCodeGenereServiceRemote extends IGenericCRUDServiceRemote<TaVerrouCodeGenere,TaVerrouCodeGenereDTO>,
														IAbstractLgrDAOServer<TaVerrouCodeGenere>,IAbstractLgrDAOServerDTO<TaVerrouCodeGenereDTO>{
	public static final String validationContext = "VERROU_CODE_GENERE";
}
