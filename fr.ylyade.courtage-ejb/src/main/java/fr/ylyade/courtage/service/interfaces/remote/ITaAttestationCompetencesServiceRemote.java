package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaAttestationCompetencesDTO;
import fr.ylyade.courtage.model.TaAttestationCompetences;


@Remote
public interface ITaAttestationCompetencesServiceRemote extends IGenericCRUDServiceRemote<TaAttestationCompetences,TaAttestationCompetencesDTO>,
														IAbstractLgrDAOServer<TaAttestationCompetences>,IAbstractLgrDAOServerDTO<TaAttestationCompetencesDTO>{
	public static final String validationContext = "T_ATTESTATION_COMPETENCES";

}
