package fr.ylyade.courtage.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaTauxAssuranceDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTauxAssuranceDTO;
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.model.mapper.TaTauxAssuranceMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxAssuranceServiceRemote;


/**
 * Session Bean implementation class TaTauxAssuranceBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTauxAssuranceService extends AbstractApplicationDAOServer<TaTauxAssurance, TaTauxAssuranceDTO> implements ITaTauxAssuranceServiceRemote {

	static Logger logger = Logger.getLogger(TaTauxAssuranceService.class);

	@Inject private ITaTauxAssuranceDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTauxAssuranceService() {
		super(TaTauxAssurance.class,TaTauxAssuranceDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTauxAssurance a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTauxAssurance transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTauxAssurance transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTauxAssurance persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTauxAssurance()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTauxAssurance merge(TaTauxAssurance detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTauxAssurance merge(TaTauxAssurance detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTauxAssurance findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTauxAssurance findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTauxAssurance> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTauxAssuranceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTauxAssuranceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTauxAssurance> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTauxAssuranceDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTauxAssuranceDTO entityToDTO(TaTauxAssurance entity) {
//		TaTauxAssuranceDTO dto = new TaTauxAssuranceDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTauxAssuranceMapper mapper = new TaTauxAssuranceMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTauxAssuranceDTO> listEntityToListDTO(List<TaTauxAssurance> entity) {
		List<TaTauxAssuranceDTO> l = new ArrayList<TaTauxAssuranceDTO>();

		for (TaTauxAssurance taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTauxAssuranceDTO> selectAllDTO() {
		System.out.println("List of TaTauxAssuranceDTO EJB :");
		ArrayList<TaTauxAssuranceDTO> liste = new ArrayList<TaTauxAssuranceDTO>();

		List<TaTauxAssurance> projects = selectAll();
		for(TaTauxAssurance project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTauxAssuranceDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTauxAssuranceDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTauxAssuranceDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTauxAssuranceDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTauxAssuranceDTO dto, String validationContext) throws EJBException {
		try {
			TaTauxAssuranceMapper mapper = new TaTauxAssuranceMapper();
			TaTauxAssurance entity = null;
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
	
	public void persistDTO(TaTauxAssuranceDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTauxAssuranceDTO dto, String validationContext) throws CreateException {
		try {
			TaTauxAssuranceMapper mapper = new TaTauxAssuranceMapper();
			TaTauxAssurance entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTauxAssuranceDTO dto) throws RemoveException {
		try {
			TaTauxAssuranceMapper mapper = new TaTauxAssuranceMapper();
			TaTauxAssurance entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTauxAssurance refresh(TaTauxAssurance persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTauxAssurance value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTauxAssurance value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTauxAssuranceDTO dto, String validationContext) {
		try {
			TaTauxAssuranceMapper mapper = new TaTauxAssuranceMapper();
			TaTauxAssurance entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTauxAssuranceDTO> validator = new BeanValidator<TaTauxAssuranceDTO>(TaTauxAssuranceDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTauxAssuranceDTO dto, String propertyName, String validationContext) {
		try {
			TaTauxAssuranceMapper mapper = new TaTauxAssuranceMapper();
			TaTauxAssurance entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTauxAssuranceDTO> validator = new BeanValidator<TaTauxAssuranceDTO>(TaTauxAssuranceDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTauxAssuranceDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTauxAssuranceDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTauxAssurance value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTauxAssurance value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
