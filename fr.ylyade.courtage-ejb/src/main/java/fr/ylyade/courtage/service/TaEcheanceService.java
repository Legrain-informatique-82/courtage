package fr.ylyade.courtage.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaEcheanceDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaFraisImpaye;
import fr.ylyade.courtage.model.TaTFraisImpaye;
import fr.ylyade.courtage.model.mapper.TaEcheanceMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFraisImpayeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFranchiseServiceRemote;


/**
 * Session Bean implementation class TaEcheanceBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaEcheanceService extends AbstractApplicationDAOServer<TaEcheance, TaEcheanceDTO> implements ITaEcheanceServiceRemote {

	static Logger logger = Logger.getLogger(TaEcheanceService.class);

	@Inject private ITaEcheanceDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaGenCodeExServiceRemote gencode;
	@EJB private ITaTFraisImpayeServiceRemote taTFraisImpaye;

	/**
	 * Default constructor. 
	 */
	public TaEcheanceService() {
		super(TaEcheance.class,TaEcheanceDTO.class);
	}
	
	public List<TaEcheanceDTO> findAllEcheanceRCDDTO() {
		return dao.findAllEcheanceRCDDTO();
	}
	
	public List<TaEcheanceDTO> findAllEcheanceRCDIdDTO(Integer idRCD) {
		return dao.findAllEcheanceRCDIdDTO(idRCD);
	}
	
	public TaEcheanceDTO findEcheanceReglementRCDDTO(Integer idEcheanceRcd) {
		return dao.findEcheanceReglementRCDDTO(idEcheanceRcd);
	}
	public List<TaEcheanceDTO> findAllEcheancesATermesDansExactementXJoursByIdDossier(Integer idDossierRcd, Date dateX){
		return dao.findAllEcheancesATermesDansExactementXJoursByIdDossier( idDossierRcd,  dateX);
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaEcheance.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaEcheance.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaEcheance.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//	private String defaultJPQLQuery = "select a from TaEcheance a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaEcheance transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaEcheance transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaEcheance persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdEcheance()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaEcheance merge(TaEcheance detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaEcheance merge(TaEcheance detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaEcheance findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaEcheance findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaEcheance> selectAll() {
		return dao.selectAll();
	}
	
	public TaEcheance findEcheanceNonPayerDepuis20Jours(Integer idDossierRcd, Date todayMoins20) {
		return dao.findEcheanceNonPayerDepuis20Jours(idDossierRcd, todayMoins20);
	}
	public TaEcheance findEcheanceNonPayerDepuis41Jour(Integer idDossierRcd, Date todayMoins41) {
		return dao.findEcheanceNonPayerDepuis41Jour(idDossierRcd, todayMoins41);
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public TaEcheance donneFraisMiseEnDemeure(TaEcheance taEcheance) {
		Date now = new Date();
		TaFraisImpaye taFraisImpaye = new TaFraisImpaye();
		try {
			taEcheance = findById(taEcheance.getIdEcheance());
		} catch (FinderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TaTFraisImpaye taTFraisMisDemeure;
		try {
			taTFraisMisDemeure = taTFraisImpaye.findByCode(TaTFraisImpaye.FRAIS_MIS_DEMEURE);
			taFraisImpaye.setTaTFraisImpaye(taTFraisMisDemeure);
			taFraisImpaye.setMontantFrais(taTFraisMisDemeure.getMontant());
		} catch (FinderException e) {
			e.printStackTrace();
			System.out.println("erreur dans taTFraisImpaye.findByCode");
		}
		taFraisImpaye.setDateFrais(now);
		taFraisImpaye.setTaEcheance(taEcheance);
		
		taEcheance.getTaFraisImpaye().add(taFraisImpaye);
		
		Double montantFrais = 0.00;
		for (TaFraisImpaye f : taEcheance.getTaFraisImpaye()) {
//			montantFrais = montantFrais.add(f.getMontantFrais());
			montantFrais = montantFrais + (f.getMontantFrais().doubleValue());
		}
		BigDecimal montant = new BigDecimal(montantFrais.toString());
		BigDecimal montantEcheancePlusFrais = taEcheance.getMontantEcheance().add(montant);	
		taEcheance.setMontantEcheancePlusFrais(montantEcheancePlusFrais);
		
		try {
			taEcheance = dao.merge(taEcheance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taEcheance;
		
		
		
	}
	@Transactional(value=TxType.REQUIRES_NEW)
	public TaEcheance donneFraisResilieNonPaiement(TaEcheance taEcheance) {
		Date now = new Date();
		TaFraisImpaye taFraisImpaye = new TaFraisImpaye();
		try {
			taEcheance = findById(taEcheance.getIdEcheance());
		} catch (FinderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TaTFraisImpaye taTFraisResilieNonPaiement;
		try {
			taTFraisResilieNonPaiement = taTFraisImpaye.findByCode(TaTFraisImpaye.FRAIS_RESILIATION);
			taFraisImpaye.setTaTFraisImpaye(taTFraisResilieNonPaiement);
			taFraisImpaye.setMontantFrais(taTFraisResilieNonPaiement.getMontant());
		} catch (FinderException e) {
			e.printStackTrace();
			System.out.println("erreur dans taTFraisImpaye.findByCode");
		}
		taFraisImpaye.setDateFrais(now);
		taFraisImpaye.setTaEcheance(taEcheance);
		
		taEcheance.getTaFraisImpaye().add(taFraisImpaye);
		
		Double montantFrais = 0.00;
		for (TaFraisImpaye f : taEcheance.getTaFraisImpaye()) {
//			montantFrais = montantFrais.add(f.getMontantFrais());
			montantFrais = montantFrais + (f.getMontantFrais().doubleValue());
		}
		BigDecimal montant = new BigDecimal(montantFrais.toString());
		BigDecimal montantEcheancePlusFrais = taEcheance.getMontantEcheance().add(montant);	
		taEcheance.setMontantEcheancePlusFrais(montantEcheancePlusFrais);
		
		try {
			taEcheance = dao.merge(taEcheance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taEcheance;
		
		
		
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public TaEcheance donneFraisResilieSansEffet(TaEcheance taEcheance) {
		Date now = new Date();
		Boolean dejaFraisResiliation = false;
		
		TaFraisImpaye taFraisImpaye = new TaFraisImpaye();
		try {
			taEcheance = findById(taEcheance.getIdEcheance());
		} catch (FinderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (TaFraisImpaye frais : taEcheance.getTaFraisImpaye()) {
			if(frais.getTaTFraisImpaye().getCodeTFraisImpaye().equals(TaTFraisImpaye.FRAIS_RESILIATION)) {
				dejaFraisResiliation = true;
			}
		}
		
		if(dejaFraisResiliation == false) {
			
		
			TaTFraisImpaye taTFraisResilieNonPaiement;
			try {
				taTFraisResilieNonPaiement = taTFraisImpaye.findByCode(TaTFraisImpaye.FRAIS_RESILIATION);
				taFraisImpaye.setTaTFraisImpaye(taTFraisResilieNonPaiement);
				taFraisImpaye.setMontantFrais(taTFraisResilieNonPaiement.getMontant());
			} catch (FinderException e) {
				e.printStackTrace();
				System.out.println("erreur dans taTFraisImpaye.findByCode");
			}
			taFraisImpaye.setDateFrais(now);
			taFraisImpaye.setTaEcheance(taEcheance);
			
			taEcheance.getTaFraisImpaye().add(taFraisImpaye);
			
			
			
			Double montantFrais = 0.00;
			for (TaFraisImpaye f : taEcheance.getTaFraisImpaye()) {
	//			montantFrais = montantFrais.add(f.getMontantFrais());
				montantFrais = montantFrais + (f.getMontantFrais().doubleValue());
			}
			BigDecimal montant = new BigDecimal(montantFrais.toString());
			BigDecimal montantEcheancePlusFrais = taEcheance.getMontantEcheance().add(montant);	
			taEcheance.setMontantEcheancePlusFrais(montantEcheancePlusFrais);
			
			try {
				taEcheance = dao.merge(taEcheance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return taEcheance;
		
		
		
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public TaEcheance donneFraisAvenant(TaEcheance taEcheance) {
		Date now = new Date();
		TaFraisImpaye taFraisImpaye = new TaFraisImpaye();
		try {
		TaEcheance	e = findById(taEcheance.getIdEcheance());
			if(e!= null) {
				taEcheance = e;
			}
		} catch (FinderException e1) {
			System.out.println("erreur dans taEcheance.findById");
			e1.printStackTrace();
		}
		TaTFraisImpaye taTFraisAvenant;
		try {
			taTFraisAvenant = taTFraisImpaye.findByCode(TaTFraisImpaye.FRAIS_AVENANT);
			taFraisImpaye.setTaTFraisImpaye(taTFraisAvenant);
			taFraisImpaye.setMontantFrais(taTFraisAvenant.getMontant());
		} catch (FinderException e) {
			e.printStackTrace();
			System.out.println("erreur dans taTFraisImpaye.findByCode");
		}
		taFraisImpaye.setDateFrais(now);
		taFraisImpaye.setTaEcheance(taEcheance);
		if(taEcheance.getTaFraisImpaye() == null) {
			taEcheance.setTaFraisImpaye( new ArrayList<TaFraisImpaye>());
		}
		taEcheance.getTaFraisImpaye().add(taFraisImpaye);
		
		Double montantFrais = 0.00;
		for (TaFraisImpaye f : taEcheance.getTaFraisImpaye()) {
//			montantFrais = montantFrais.add(f.getMontantFrais());
			montantFrais = montantFrais + (f.getMontantFrais().doubleValue());
		}
		BigDecimal montant = new BigDecimal(montantFrais.toString());
		BigDecimal montantEcheancePlusFrais = taEcheance.getMontantEcheance().add(montant);	
		taEcheance.setMontantEcheancePlusFrais(montantEcheancePlusFrais);
		
		try {
			taEcheance = dao.merge(taEcheance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taEcheance;
		
		
		
	}
	
	
	public List<TaEcheance> findAllEcheanceByIdDossier(Integer idDossierRcd){
		return dao.findAllEcheanceByIdDossier(idDossierRcd);
	}
	
	public TaEcheance findFirstEcheanceNonPayer(Integer idDossierRcd) {
		return dao.findFirstEcheanceNonPayer(idDossierRcd);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaEcheanceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaEcheanceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaEcheance> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaEcheanceDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaEcheanceDTO entityToDTO(TaEcheance entity) {
//		TaEcheanceDTO dto = new TaEcheanceDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaEcheanceMapper mapper = new TaEcheanceMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaEcheanceDTO> listEntityToListDTO(List<TaEcheance> entity) {
		List<TaEcheanceDTO> l = new ArrayList<TaEcheanceDTO>();

		for (TaEcheance taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaEcheanceDTO> selectAllDTO() {
		System.out.println("List of TaEcheanceDTO EJB :");
		ArrayList<TaEcheanceDTO> liste = new ArrayList<TaEcheanceDTO>();

		List<TaEcheance> projects = selectAll();
		for(TaEcheance project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaEcheanceDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaEcheanceDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaEcheanceDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaEcheanceDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaEcheanceDTO dto, String validationContext) throws EJBException {
		try {
			TaEcheanceMapper mapper = new TaEcheanceMapper();
			TaEcheance entity = null;
			if(dto.getId()!=null) {
				entity = dao.findById(dto.getId());
				if(dto.getVersionObj()!=entity.getVersionObj()) {
					throw new OptimisticLockException(entity,
							"L'objet à été modifié depuis le dernier accés. Client ID : "+dto.getId()+" - Client Version objet : "+dto.getVersionObj()+" -Serveur Version Objet : "+entity.getVersionObj());
				} else {
					 entity = mapper.mapDtoToEntity(dto,entity);
				}
			}
			
			//dao.merge(entity);
			dao.detach(entity); //pour passer les controles
			enregistrerMerge(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new CreateException(e.getMessage());
			throw new EJBException(e.getMessage());
		}
	}
	
	public void persistDTO(TaEcheanceDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaEcheanceDTO dto, String validationContext) throws CreateException {
		try {
			TaEcheanceMapper mapper = new TaEcheanceMapper();
			TaEcheance entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaEcheanceDTO dto) throws RemoveException {
		try {
			TaEcheanceMapper mapper = new TaEcheanceMapper();
			TaEcheance entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaEcheance refresh(TaEcheance persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaEcheance value, String validationContext) /*throws ExceptLgr*/ {
		try {
			if(validationContext==null) validationContext="";
			validateAll(value,validationContext,false); //ancienne validation, extraction de l'annotation et appel
			//dao.validate(value); //validation automatique via la JSR bean validation
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateEntityPropertyValidationContext")
	public void validateEntityProperty(TaEcheance value, String propertyName, String validationContext) {
		try {
			if(validationContext==null) validationContext="";
			validate(value, propertyName, validationContext);
			//dao.validateField(value,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOValidationContext")
	public void validateDTO(TaEcheanceDTO dto, String validationContext) {
		try {
			TaEcheanceMapper mapper = new TaEcheanceMapper();
			TaEcheance entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaEcheanceDTO> validator = new BeanValidator<TaEcheanceDTO>(TaEcheanceDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaEcheanceDTO dto, String propertyName, String validationContext) {
		try {
			TaEcheanceMapper mapper = new TaEcheanceMapper();
			TaEcheance entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaEcheanceDTO> validator = new BeanValidator<TaEcheanceDTO>(TaEcheanceDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaEcheanceDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaEcheanceDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaEcheance value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaEcheance value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
