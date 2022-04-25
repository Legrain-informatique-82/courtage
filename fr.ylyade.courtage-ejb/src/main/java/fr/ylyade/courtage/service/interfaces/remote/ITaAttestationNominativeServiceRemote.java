package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaAttestationNominativeDTO;
import fr.ylyade.courtage.model.TaAttestationNominative;


@Remote
public interface ITaAttestationNominativeServiceRemote extends IGenericCRUDServiceRemote<TaAttestationNominative,TaAttestationNominativeDTO>,
														IAbstractLgrDAOServer<TaAttestationNominative>,IAbstractLgrDAOServerDTO<TaAttestationNominativeDTO>{
	public static final String validationContext = "T_ATTESTAION_NOMINATIVE";
	
	public List<TaAttestationNominative> findAllByIdDossier(Integer idDossierRcd);

}
