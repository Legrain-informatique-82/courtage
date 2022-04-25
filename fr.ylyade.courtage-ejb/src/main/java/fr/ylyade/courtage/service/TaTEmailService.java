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
import fr.ylyade.courtage.dao.ITaTEmailDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTEmailDTO;
import fr.ylyade.courtage.model.TaTEmail;
import fr.ylyade.courtage.model.mapper.TaTEmailMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTEmailServiceRemote;


/**
 * Session Bean implementation class TaTEmailBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTEmailService extends AbstractApplicationDAOServer<TaTEmail, TaTEmailDTO> implements ITaTEmailServiceRemote {

	static Logger logger = Logger.getLogger(TaTEmailService.class);

	@Inject private ITaTEmailDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTEmailService() {
		super(TaTEmail.class,TaTEmailDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTEmail a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTEmail transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTEmail transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTEmail persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTEmail()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTEmail merge(TaTEmail detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTEmail merge(TaTEmail detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTEmail findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTEmail findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTEmail> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTEmailDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTEmailDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTEmail> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTEmailDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTEmailDTO entityToDTO(TaTEmail entity) {
//		TaTEmailDTO dto = new TaTEmailDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTEmailMapper mapper = new TaTEmailMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTEmailDTO> listEntityToListDTO(List<TaTEmail> entity) {
		List<TaTEmailDTO> l = new ArrayList<TaTEmailDTO>();

		for (TaTEmail taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTEmailDTO> selectAllDTO() {
		System.out.println("List of TaTEmailDTO EJB :");
		ArrayList<TaTEmailDTO> liste = new ArrayList<TaTEmailDTO>();

		List<TaTEmail> projects = selectAll();
		for(TaTEmail project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTEmailDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTEmailDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTEmailDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTEmailDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTEmailDTO dto, String validationContext) throws EJBException {
		try {
			TaTEmailMapper mapper = new TaTEmailMapper();
			TaTEmail entity = null;
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
	
	public void persistDTO(TaTEmailDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTEmailDTO dto, String validationContext) throws CreateException {
		try {
			TaTEmailMapper mapper = new TaTEmailMapper();
			TaTEmail entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTEmailDTO dto) throws RemoveException {
		try {
			TaTEmailMapper mapper = new TaTEmailMapper();
			TaTEmail entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTEmail refresh(TaTEmail persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTEmail value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTEmail value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTEmailDTO dto, String validationContext) {
		try {
			TaTEmailMapper mapper = new TaTEmailMapper();
			TaTEmail entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTEmailDTO> validator = new BeanValidator<TaTEmailDTO>(TaTEmailDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTEmailDTO dto, String propertyName, String validationContext) {
		try {
			TaTEmailMapper mapper = new TaTEmailMapper();
			TaTEmail entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTEmailDTO> validator = new BeanValidator<TaTEmailDTO>(TaTEmailDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTEmailDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTEmailDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTEmail value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTEmail value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
