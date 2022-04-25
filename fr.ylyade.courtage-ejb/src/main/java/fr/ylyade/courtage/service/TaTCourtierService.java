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
import fr.ylyade.courtage.dao.ITaTCourtierDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.model.TaTCourtier;
import fr.ylyade.courtage.model.mapper.TaTCourtierMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTCourtierServiceRemote;


/**
 * Session Bean implementation class TaTCourtierBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTCourtierService extends AbstractApplicationDAOServer<TaTCourtier, TaTCourtierDTO> implements ITaTCourtierServiceRemote {

	static Logger logger = Logger.getLogger(TaTCourtierService.class);

	@Inject private ITaTCourtierDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTCourtierService() {
		super(TaTCourtier.class,TaTCourtierDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTCourtier a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTCourtier transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTCourtier transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTCourtier persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTCourtier()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTCourtier merge(TaTCourtier detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTCourtier merge(TaTCourtier detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTCourtier findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTCourtier findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTCourtier> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTCourtierDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTCourtierDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTCourtier> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTCourtierDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTCourtierDTO entityToDTO(TaTCourtier entity) {
//		TaTCourtierDTO dto = new TaTCourtierDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTCourtierMapper mapper = new TaTCourtierMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTCourtierDTO> listEntityToListDTO(List<TaTCourtier> entity) {
		List<TaTCourtierDTO> l = new ArrayList<TaTCourtierDTO>();

		for (TaTCourtier taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTCourtierDTO> selectAllDTO() {
		System.out.println("List of TaTCourtierDTO EJB :");
		ArrayList<TaTCourtierDTO> liste = new ArrayList<TaTCourtierDTO>();

		List<TaTCourtier> projects = selectAll();
		for(TaTCourtier project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTCourtierDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTCourtierDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTCourtierDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTCourtierDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTCourtierDTO dto, String validationContext) throws EJBException {
		try {
			TaTCourtierMapper mapper = new TaTCourtierMapper();
			TaTCourtier entity = null;
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
	
	public void persistDTO(TaTCourtierDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTCourtierDTO dto, String validationContext) throws CreateException {
		try {
			TaTCourtierMapper mapper = new TaTCourtierMapper();
			TaTCourtier entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTCourtierDTO dto) throws RemoveException {
		try {
			TaTCourtierMapper mapper = new TaTCourtierMapper();
			TaTCourtier entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTCourtier refresh(TaTCourtier persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTCourtier value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTCourtier value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTCourtierDTO dto, String validationContext) {
		try {
			TaTCourtierMapper mapper = new TaTCourtierMapper();
			TaTCourtier entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTCourtierDTO> validator = new BeanValidator<TaTCourtierDTO>(TaTCourtierDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTCourtierDTO dto, String propertyName, String validationContext) {
		try {
			TaTCourtierMapper mapper = new TaTCourtierMapper();
			TaTCourtier entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTCourtierDTO> validator = new BeanValidator<TaTCourtierDTO>(TaTCourtierDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTCourtierDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTCourtierDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTCourtier value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTCourtier value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
