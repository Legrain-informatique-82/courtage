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
import fr.ylyade.courtage.dao.ITaTauxRcproPibDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTauxRcproPibDTO;
import fr.ylyade.courtage.model.TaTauxRcproPib;
import fr.ylyade.courtage.model.mapper.TaTauxRcproPibMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxRcproPibServiceRemote;


/**
 * Session Bean implementation class TaTauxRcproPibBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTauxRcproPibService extends AbstractApplicationDAOServer<TaTauxRcproPib, TaTauxRcproPibDTO> implements ITaTauxRcproPibServiceRemote {

	static Logger logger = Logger.getLogger(TaTauxRcproPibService.class);

	@Inject private ITaTauxRcproPibDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTauxRcproPibService() {
		super(TaTauxRcproPib.class,TaTauxRcproPibDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTauxRcproPib a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTauxRcproPib transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTauxRcproPib transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTauxRcproPib persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTauxRcproPib()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTauxRcproPib merge(TaTauxRcproPib detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTauxRcproPib merge(TaTauxRcproPib detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTauxRcproPib findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTauxRcproPib findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTauxRcproPib> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTauxRcproPibDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTauxRcproPibDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTauxRcproPib> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTauxRcproPibDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTauxRcproPibDTO entityToDTO(TaTauxRcproPib entity) {
//		TaTauxRcproPibDTO dto = new TaTauxRcproPibDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTauxRcproPibMapper mapper = new TaTauxRcproPibMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTauxRcproPibDTO> listEntityToListDTO(List<TaTauxRcproPib> entity) {
		List<TaTauxRcproPibDTO> l = new ArrayList<TaTauxRcproPibDTO>();

		for (TaTauxRcproPib taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTauxRcproPibDTO> selectAllDTO() {
		System.out.println("List of TaTauxRcproPibDTO EJB :");
		ArrayList<TaTauxRcproPibDTO> liste = new ArrayList<TaTauxRcproPibDTO>();

		List<TaTauxRcproPib> projects = selectAll();
		for(TaTauxRcproPib project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTauxRcproPibDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTauxRcproPibDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTauxRcproPibDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTauxRcproPibDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTauxRcproPibDTO dto, String validationContext) throws EJBException {
		try {
			TaTauxRcproPibMapper mapper = new TaTauxRcproPibMapper();
			TaTauxRcproPib entity = null;
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
	
	public void persistDTO(TaTauxRcproPibDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTauxRcproPibDTO dto, String validationContext) throws CreateException {
		try {
			TaTauxRcproPibMapper mapper = new TaTauxRcproPibMapper();
			TaTauxRcproPib entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTauxRcproPibDTO dto) throws RemoveException {
		try {
			TaTauxRcproPibMapper mapper = new TaTauxRcproPibMapper();
			TaTauxRcproPib entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTauxRcproPib refresh(TaTauxRcproPib persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTauxRcproPib value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTauxRcproPib value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTauxRcproPibDTO dto, String validationContext) {
		try {
			TaTauxRcproPibMapper mapper = new TaTauxRcproPibMapper();
			TaTauxRcproPib entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTauxRcproPibDTO> validator = new BeanValidator<TaTauxRcproPibDTO>(TaTauxRcproPibDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTauxRcproPibDTO dto, String propertyName, String validationContext) {
		try {
			TaTauxRcproPibMapper mapper = new TaTauxRcproPibMapper();
			TaTauxRcproPib entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTauxRcproPibDTO> validator = new BeanValidator<TaTauxRcproPibDTO>(TaTauxRcproPibDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTauxRcproPibDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTauxRcproPibDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTauxRcproPib value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTauxRcproPib value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
