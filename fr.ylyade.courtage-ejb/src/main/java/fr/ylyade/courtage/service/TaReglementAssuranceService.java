package fr.ylyade.courtage.service;

import java.util.ArrayList;
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

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaReglementAssuranceDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.mapper.TaReglementAssuranceMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaReglementAssuranceServiceRemote;


/**
 * Session Bean implementation class TaReglementAssuranceBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaReglementAssuranceService extends AbstractApplicationDAOServer<TaReglementAssurance, TaReglementAssuranceDTO> implements ITaReglementAssuranceServiceRemote {

	static Logger logger = Logger.getLogger(TaReglementAssuranceService.class);

	@Inject private ITaReglementAssuranceDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaGenCodeExServiceRemote gencode;

	/**
	 * Default constructor. 
	 */
	public TaReglementAssuranceService() {
		super(TaReglementAssurance.class,TaReglementAssuranceDTO.class);
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaReglementAssurance.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaReglementAssurance.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaReglementAssurance.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//	private String defaultJPQLQuery = "select a from TaReglementAssurance a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaReglementAssurance transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaReglementAssurance transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaReglementAssurance persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdReglementAssurance()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaReglementAssurance merge(TaReglementAssurance detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaReglementAssurance merge(TaReglementAssurance detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaReglementAssurance findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaReglementAssurance findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaReglementAssurance> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtierAndByMoisAndYear(Integer idCourtier, String mois, String annee){
		return dao.findAllDTOByIdCourtierAndByMoisAndYear(idCourtier, mois, annee);
	}
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtier(Integer idCourtier){
		return dao.findAllDTOByIdCourtier(idCourtier);
	}
	public List<TaReglementAssuranceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaReglementAssuranceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaReglementAssurance> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaReglementAssuranceDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaReglementAssuranceDTO entityToDTO(TaReglementAssurance entity) {
//		TaReglementAssuranceDTO dto = new TaReglementAssuranceDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaReglementAssuranceMapper mapper = new TaReglementAssuranceMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaReglementAssuranceDTO> listEntityToListDTO(List<TaReglementAssurance> entity) {
		List<TaReglementAssuranceDTO> l = new ArrayList<TaReglementAssuranceDTO>();

		for (TaReglementAssurance taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaReglementAssuranceDTO> selectAllDTO() {
		System.out.println("List of TaReglementAssuranceDTO EJB :");
		ArrayList<TaReglementAssuranceDTO> liste = new ArrayList<TaReglementAssuranceDTO>();

		List<TaReglementAssurance> projects = selectAll();
		for(TaReglementAssurance project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaReglementAssuranceDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaReglementAssuranceDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaReglementAssuranceDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaReglementAssuranceDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaReglementAssuranceDTO dto, String validationContext) throws EJBException {
		try {
			TaReglementAssuranceMapper mapper = new TaReglementAssuranceMapper();
			TaReglementAssurance entity = null;
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
	
	public void persistDTO(TaReglementAssuranceDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaReglementAssuranceDTO dto, String validationContext) throws CreateException {
		try {
			TaReglementAssuranceMapper mapper = new TaReglementAssuranceMapper();
			TaReglementAssurance entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaReglementAssuranceDTO dto) throws RemoveException {
		try {
			TaReglementAssuranceMapper mapper = new TaReglementAssuranceMapper();
			TaReglementAssurance entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaReglementAssurance refresh(TaReglementAssurance persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaReglementAssurance value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaReglementAssurance value, String propertyName, String validationContext) {
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
	public void validateDTO(TaReglementAssuranceDTO dto, String validationContext) {
		try {
			TaReglementAssuranceMapper mapper = new TaReglementAssuranceMapper();
			TaReglementAssurance entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaReglementAssuranceDTO> validator = new BeanValidator<TaReglementAssuranceDTO>(TaReglementAssuranceDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaReglementAssuranceDTO dto, String propertyName, String validationContext) {
		try {
			TaReglementAssuranceMapper mapper = new TaReglementAssuranceMapper();
			TaReglementAssurance entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaReglementAssuranceDTO> validator = new BeanValidator<TaReglementAssuranceDTO>(TaReglementAssuranceDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaReglementAssuranceDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaReglementAssuranceDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaReglementAssurance value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaReglementAssurance value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
