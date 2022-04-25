package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTFranchiseDTO;
import fr.ylyade.courtage.model.TaTFranchise;


@Remote
public interface ITaTFranchiseServiceRemote extends IGenericCRUDServiceRemote<TaTFranchise,TaTFranchiseDTO>,
														IAbstractLgrDAOServer<TaTFranchise>,IAbstractLgrDAOServerDTO<TaTFranchiseDTO>{
	public static final String validationContext = "T_FRANCHISE";

}
