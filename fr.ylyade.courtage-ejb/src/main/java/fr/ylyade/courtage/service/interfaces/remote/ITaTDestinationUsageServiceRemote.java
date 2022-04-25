package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTDestinationUsageDTO;
import fr.ylyade.courtage.model.TaTDestinationUsage;


@Remote
public interface ITaTDestinationUsageServiceRemote extends IGenericCRUDServiceRemote<TaTDestinationUsage,TaTDestinationUsageDTO>,
														IAbstractLgrDAOServer<TaTDestinationUsage>,IAbstractLgrDAOServerDTO<TaTDestinationUsageDTO>{
	public static final String validationContext = "T_DESTINATION_USAGE";

}
