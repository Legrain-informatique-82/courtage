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
import fr.ylyade.courtage.dao.ITaTDestinationUsageDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTDestinationUsageDTO;
import fr.ylyade.courtage.model.TaTDestinationUsage;
import fr.ylyade.courtage.model.mapper.TaTDestinationUsageMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTDestinationUsageServiceRemote;


/**
 * Session Bean implementation class TaTDestinationUsageBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTDestinationUsageService extends AbstractApplicationDAOServer<TaTDestinationUsage, TaTDestinationUsageDTO> implements ITaTDestinationUsageServiceRemote {

	static Logger logger = Logger.getLogger(TaTDestinationUsageService.class);

	@Inject private ITaTDestinationUsageDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTDestinationUsageService() {
		super(TaTDestinationUsage.class,TaTDestinationUsageDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTDestinationUsage a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTDestinationUsage transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTDestinationUsage transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTDestinationUsage persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTDestinationUsage()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTDestinationUsage merge(TaTDestinationUsage detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTDestinationUsage merge(TaTDestinationUsage detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTDestinationUsage findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTDestinationUsage findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTDestinationUsage> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTDestinationUsageDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTDestinationUsageDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTDestinationUsage> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTDestinationUsageDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTDestinationUsageDTO entityToDTO(TaTDestinationUsage entity) {
//		TaTDestinationUsageDTO dto = new TaTDestinationUsageDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTDestinationUsageMapper mapper = new TaTDestinationUsageMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTDestinationUsageDTO> listEntityToListDTO(List<TaTDestinationUsage> entity) {
		List<TaTDestinationUsageDTO> l = new ArrayList<TaTDestinationUsageDTO>();

		for (TaTDestinationUsage taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTDestinationUsageDTO> selectAllDTO() {
		System.out.println("List of TaTDestinationUsageDTO EJB :");
		ArrayList<TaTDestinationUsageDTO> liste = new ArrayList<TaTDestinationUsageDTO>();

		List<TaTDestinationUsage> projects = selectAll();
		for(TaTDestinationUsage project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTDestinationUsageDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTDestinationUsageDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTDestinationUsageDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTDestinationUsageDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTDestinationUsageDTO dto, String validationContext) throws EJBException {
		try {
			TaTDestinationUsageMapper mapper = new TaTDestinationUsageMapper();
			TaTDestinationUsage entity = null;
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
	
	public void persistDTO(TaTDestinationUsageDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTDestinationUsageDTO dto, String validationContext) throws CreateException {
		try {
			TaTDestinationUsageMapper mapper = new TaTDestinationUsageMapper();
			TaTDestinationUsage entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTDestinationUsageDTO dto) throws RemoveException {
		try {
			TaTDestinationUsageMapper mapper = new TaTDestinationUsageMapper();
			TaTDestinationUsage entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTDestinationUsage refresh(TaTDestinationUsage persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTDestinationUsage value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTDestinationUsage value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTDestinationUsageDTO dto, String validationContext) {
		try {
			TaTDestinationUsageMapper mapper = new TaTDestinationUsageMapper();
			TaTDestinationUsage entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTDestinationUsageDTO> validator = new BeanValidator<TaTDestinationUsageDTO>(TaTDestinationUsageDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTDestinationUsageDTO dto, String propertyName, String validationContext) {
		try {
			TaTDestinationUsageMapper mapper = new TaTDestinationUsageMapper();
			TaTDestinationUsage entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTDestinationUsageDTO> validator = new BeanValidator<TaTDestinationUsageDTO>(TaTDestinationUsageDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTDestinationUsageDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTDestinationUsageDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTDestinationUsage value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTDestinationUsage value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
