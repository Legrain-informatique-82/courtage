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
import fr.ylyade.courtage.dao.ITaSinistreDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaSinistreDTO;
import fr.ylyade.courtage.model.TaSinistre;
import fr.ylyade.courtage.model.mapper.TaSinistreMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaSinistreServiceRemote;


/**
 * Session Bean implementation class TaSinistreBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaSinistreService extends AbstractApplicationDAOServer<TaSinistre, TaSinistreDTO> implements ITaSinistreServiceRemote {

	static Logger logger = Logger.getLogger(TaSinistreService.class);

	@Inject private ITaSinistreDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaSinistreService() {
		super(TaSinistre.class,TaSinistreDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaSinistre a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaSinistre transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaSinistre transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaSinistre persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdSinistre()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaSinistre merge(TaSinistre detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaSinistre merge(TaSinistre detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaSinistre findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaSinistre findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaSinistre> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaSinistreDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaSinistreDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaSinistre> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaSinistreDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaSinistreDTO entityToDTO(TaSinistre entity) {
//		TaSinistreDTO dto = new TaSinistreDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaSinistreMapper mapper = new TaSinistreMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaSinistreDTO> listEntityToListDTO(List<TaSinistre> entity) {
		List<TaSinistreDTO> l = new ArrayList<TaSinistreDTO>();

		for (TaSinistre taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaSinistreDTO> selectAllDTO() {
		System.out.println("List of TaSinistreDTO EJB :");
		ArrayList<TaSinistreDTO> liste = new ArrayList<TaSinistreDTO>();

		List<TaSinistre> projects = selectAll();
		for(TaSinistre project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaSinistreDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaSinistreDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaSinistreDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaSinistreDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaSinistreDTO dto, String validationContext) throws EJBException {
		try {
			TaSinistreMapper mapper = new TaSinistreMapper();
			TaSinistre entity = null;
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
	
	public void persistDTO(TaSinistreDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaSinistreDTO dto, String validationContext) throws CreateException {
		try {
			TaSinistreMapper mapper = new TaSinistreMapper();
			TaSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaSinistreDTO dto) throws RemoveException {
		try {
			TaSinistreMapper mapper = new TaSinistreMapper();
			TaSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaSinistre refresh(TaSinistre persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaSinistre value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaSinistre value, String propertyName, String validationContext) {
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
	public void validateDTO(TaSinistreDTO dto, String validationContext) {
		try {
			TaSinistreMapper mapper = new TaSinistreMapper();
			TaSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaSinistreDTO> validator = new BeanValidator<TaSinistreDTO>(TaSinistreDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaSinistreDTO dto, String propertyName, String validationContext) {
		try {
			TaSinistreMapper mapper = new TaSinistreMapper();
			TaSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaSinistreDTO> validator = new BeanValidator<TaSinistreDTO>(TaSinistreDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaSinistreDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaSinistreDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaSinistre value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaSinistre value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
