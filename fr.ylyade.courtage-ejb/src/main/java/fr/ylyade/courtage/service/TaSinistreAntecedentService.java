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
import fr.ylyade.courtage.dao.ITaSinistreAntecedentDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaSinistreAntecedentDTO;
import fr.ylyade.courtage.model.TaSinistreAntecedent;
import fr.ylyade.courtage.model.mapper.TaSinistreAntecedentMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaSinistreAntecedentServiceRemote;


/**
 * Session Bean implementation class TaSinistreAntecedentBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaSinistreAntecedentService extends AbstractApplicationDAOServer<TaSinistreAntecedent, TaSinistreAntecedentDTO> implements ITaSinistreAntecedentServiceRemote {

	static Logger logger = Logger.getLogger(TaSinistreAntecedentService.class);

	@Inject private ITaSinistreAntecedentDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaSinistreAntecedentService() {
		super(TaSinistreAntecedent.class,TaSinistreAntecedentDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaSinistreAntecedent a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaSinistreAntecedent transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaSinistreAntecedent transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaSinistreAntecedent persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdSinistreAntecedent()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaSinistreAntecedent merge(TaSinistreAntecedent detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaSinistreAntecedent merge(TaSinistreAntecedent detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaSinistreAntecedent findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaSinistreAntecedent findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaSinistreAntecedent> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaSinistreAntecedentDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaSinistreAntecedentDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaSinistreAntecedent> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaSinistreAntecedentDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaSinistreAntecedentDTO entityToDTO(TaSinistreAntecedent entity) {
//		TaSinistreAntecedentDTO dto = new TaSinistreAntecedentDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaSinistreAntecedentMapper mapper = new TaSinistreAntecedentMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaSinistreAntecedentDTO> listEntityToListDTO(List<TaSinistreAntecedent> entity) {
		List<TaSinistreAntecedentDTO> l = new ArrayList<TaSinistreAntecedentDTO>();

		for (TaSinistreAntecedent taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaSinistreAntecedentDTO> selectAllDTO() {
		System.out.println("List of TaSinistreAntecedentDTO EJB :");
		ArrayList<TaSinistreAntecedentDTO> liste = new ArrayList<TaSinistreAntecedentDTO>();

		List<TaSinistreAntecedent> projects = selectAll();
		for(TaSinistreAntecedent project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaSinistreAntecedentDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaSinistreAntecedentDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaSinistreAntecedentDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaSinistreAntecedentDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaSinistreAntecedentDTO dto, String validationContext) throws EJBException {
		try {
			TaSinistreAntecedentMapper mapper = new TaSinistreAntecedentMapper();
			TaSinistreAntecedent entity = null;
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
	
	public void persistDTO(TaSinistreAntecedentDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaSinistreAntecedentDTO dto, String validationContext) throws CreateException {
		try {
			TaSinistreAntecedentMapper mapper = new TaSinistreAntecedentMapper();
			TaSinistreAntecedent entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaSinistreAntecedentDTO dto) throws RemoveException {
		try {
			TaSinistreAntecedentMapper mapper = new TaSinistreAntecedentMapper();
			TaSinistreAntecedent entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaSinistreAntecedent refresh(TaSinistreAntecedent persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaSinistreAntecedent value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaSinistreAntecedent value, String propertyName, String validationContext) {
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
	public void validateDTO(TaSinistreAntecedentDTO dto, String validationContext) {
		try {
			TaSinistreAntecedentMapper mapper = new TaSinistreAntecedentMapper();
			TaSinistreAntecedent entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaSinistreAntecedentDTO> validator = new BeanValidator<TaSinistreAntecedentDTO>(TaSinistreAntecedentDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaSinistreAntecedentDTO dto, String propertyName, String validationContext) {
		try {
			TaSinistreAntecedentMapper mapper = new TaSinistreAntecedentMapper();
			TaSinistreAntecedent entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaSinistreAntecedentDTO> validator = new BeanValidator<TaSinistreAntecedentDTO>(TaSinistreAntecedentDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaSinistreAntecedentDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaSinistreAntecedentDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaSinistreAntecedent value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaSinistreAntecedent value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
