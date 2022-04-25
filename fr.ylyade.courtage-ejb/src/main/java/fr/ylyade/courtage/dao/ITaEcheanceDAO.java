package fr.ylyade.courtage.dao;

import java.util.Date;
import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.TaEcheance;

//@Remote
public interface ITaEcheanceDAO extends IGenericDAO<TaEcheance> {		
	public List<TaEcheanceDTO> findAllEcheanceRCDDTO();
	public List<TaEcheanceDTO> findAllEcheanceRCDIdDTO(Integer idRCD);
	public TaEcheanceDTO findEcheanceReglementRCDDTO(Integer idEcheanceRcd);
	public TaEcheance findEcheanceNonPayerDepuis20Jours(Integer idDossierRcd, Date todayMoins20);
	public TaEcheance findEcheanceNonPayerDepuis41Jour(Integer idDossierRcd, Date todayMoins41);
	public TaEcheance findFirstEcheanceNonPayer(Integer idDossierRcd);
	public List<TaEcheance> findAllEcheanceByIdDossier(Integer idDossierRcd);
	
	public List<TaEcheanceDTO> findAllEcheancesATermesDansExactementXJoursByIdDossier(Integer idDossierRcd, Date dateX);
	
}

