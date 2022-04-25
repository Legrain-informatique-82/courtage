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
import fr.ylyade.courtage.dao.ITaTGedSinistreDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTGedSinistreDTO;
import fr.ylyade.courtage.model.TaTGedSinistre;
import fr.ylyade.courtage.model.mapper.TaTGedSinistreMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTGedSinistreServiceRemote;


/**
 * Session Bean implementation class TaTGedSinistreBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTGedSinistreService extends AbstractApplicationDAOServer<TaTGedSinistre, TaTGedSinistreDTO> implements ITaTGedSinistreServiceRemote {

	static Logger logger = Logger.getLogger(TaTGedSinistreService.class);

	@Inject private ITaTGedSinistreDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTGedSinistreService() {
		super(TaTGedSinistre.class,TaTGedSinistreDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTGedSinistre a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTGedSinistre transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTGedSinistre transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTGedSinistre persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTGedSinistre()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTGedSinistre merge(TaTGedSinistre detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTGedSinistre merge(TaTGedSinistre detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTGedSinistre findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTGedSinistre findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTGedSinistre> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTGedSinistreDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTGedSinistreDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTGedSinistre> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTGedSinistreDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTGedSinistreDTO entityToDTO(TaTGedSinistre entity) {
//		TaTGedSinistreDTO dto = new TaTGedSinistreDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTGedSinistreMapper mapper = new TaTGedSinistreMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTGedSinistreDTO> listEntityToListDTO(List<TaTGedSinistre> entity) {
		List<TaTGedSinistreDTO> l = new ArrayList<TaTGedSinistreDTO>();

		for (TaTGedSinistre taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTGedSinistreDTO> selectAllDTO() {
		System.out.println("List of TaTGedSinistreDTO EJB :");
		ArrayList<TaTGedSinistreDTO> liste = new ArrayList<TaTGedSinistreDTO>();

		List<TaTGedSinistre> projects = selectAll();
		for(TaTGedSinistre project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTGedSinistreDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTGedSinistreDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTGedSinistreDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTGedSinistreDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTGedSinistreDTO dto, String validationContext) throws EJBException {
		try {
			TaTGedSinistreMapper mapper = new TaTGedSinistreMapper();
			TaTGedSinistre entity = null;
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
	
	public void persistDTO(TaTGedSinistreDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTGedSinistreDTO dto, String validationContext) throws CreateException {
		try {
			TaTGedSinistreMapper mapper = new TaTGedSinistreMapper();
			TaTGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTGedSinistreDTO dto) throws RemoveException {
		try {
			TaTGedSinistreMapper mapper = new TaTGedSinistreMapper();
			TaTGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTGedSinistre refresh(TaTGedSinistre persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTGedSinistre value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTGedSinistre value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTGedSinistreDTO dto, String validationContext) {
		try {
			TaTGedSinistreMapper mapper = new TaTGedSinistreMapper();
			TaTGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTGedSinistreDTO> validator = new BeanValidator<TaTGedSinistreDTO>(TaTGedSinistreDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTGedSinistreDTO dto, String propertyName, String validationContext) {
		try {
			TaTGedSinistreMapper mapper = new TaTGedSinistreMapper();
			TaTGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTGedSinistreDTO> validator = new BeanValidator<TaTGedSinistreDTO>(TaTGedSinistreDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTGedSinistreDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTGedSinistreDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTGedSinistre value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTGedSinistre value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
