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
import fr.ylyade.courtage.dao.ITaTMaitriseOeuvreDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTMaitriseOeuvreDTO;
import fr.ylyade.courtage.model.TaTMaitriseOeuvre;
import fr.ylyade.courtage.model.mapper.TaTMaitriseOeuvreMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTMaitriseOeuvreServiceRemote;


/**
 * Session Bean implementation class TaTMaitriseOeuvreBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTMaitriseOeuvreService extends AbstractApplicationDAOServer<TaTMaitriseOeuvre, TaTMaitriseOeuvreDTO> implements ITaTMaitriseOeuvreServiceRemote {

	static Logger logger = Logger.getLogger(TaTMaitriseOeuvreService.class);

	@Inject private ITaTMaitriseOeuvreDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTMaitriseOeuvreService() {
		super(TaTMaitriseOeuvre.class,TaTMaitriseOeuvreDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTMaitriseOeuvre a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTMaitriseOeuvre transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTMaitriseOeuvre transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTMaitriseOeuvre persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTMaitriseOeuvre()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTMaitriseOeuvre merge(TaTMaitriseOeuvre detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTMaitriseOeuvre merge(TaTMaitriseOeuvre detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTMaitriseOeuvre findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTMaitriseOeuvre findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTMaitriseOeuvre> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTMaitriseOeuvreDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTMaitriseOeuvreDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTMaitriseOeuvre> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTMaitriseOeuvreDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTMaitriseOeuvreDTO entityToDTO(TaTMaitriseOeuvre entity) {
//		TaTMaitriseOeuvreDTO dto = new TaTMaitriseOeuvreDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTMaitriseOeuvreMapper mapper = new TaTMaitriseOeuvreMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTMaitriseOeuvreDTO> listEntityToListDTO(List<TaTMaitriseOeuvre> entity) {
		List<TaTMaitriseOeuvreDTO> l = new ArrayList<TaTMaitriseOeuvreDTO>();

		for (TaTMaitriseOeuvre taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTMaitriseOeuvreDTO> selectAllDTO() {
		System.out.println("List of TaTMaitriseOeuvreDTO EJB :");
		ArrayList<TaTMaitriseOeuvreDTO> liste = new ArrayList<TaTMaitriseOeuvreDTO>();

		List<TaTMaitriseOeuvre> projects = selectAll();
		for(TaTMaitriseOeuvre project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTMaitriseOeuvreDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTMaitriseOeuvreDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTMaitriseOeuvreDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTMaitriseOeuvreDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTMaitriseOeuvreDTO dto, String validationContext) throws EJBException {
		try {
			TaTMaitriseOeuvreMapper mapper = new TaTMaitriseOeuvreMapper();
			TaTMaitriseOeuvre entity = null;
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
	
	public void persistDTO(TaTMaitriseOeuvreDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTMaitriseOeuvreDTO dto, String validationContext) throws CreateException {
		try {
			TaTMaitriseOeuvreMapper mapper = new TaTMaitriseOeuvreMapper();
			TaTMaitriseOeuvre entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTMaitriseOeuvreDTO dto) throws RemoveException {
		try {
			TaTMaitriseOeuvreMapper mapper = new TaTMaitriseOeuvreMapper();
			TaTMaitriseOeuvre entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTMaitriseOeuvre refresh(TaTMaitriseOeuvre persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTMaitriseOeuvre value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTMaitriseOeuvre value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTMaitriseOeuvreDTO dto, String validationContext) {
		try {
			TaTMaitriseOeuvreMapper mapper = new TaTMaitriseOeuvreMapper();
			TaTMaitriseOeuvre entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTMaitriseOeuvreDTO> validator = new BeanValidator<TaTMaitriseOeuvreDTO>(TaTMaitriseOeuvreDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTMaitriseOeuvreDTO dto, String propertyName, String validationContext) {
		try {
			TaTMaitriseOeuvreMapper mapper = new TaTMaitriseOeuvreMapper();
			TaTMaitriseOeuvre entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTMaitriseOeuvreDTO> validator = new BeanValidator<TaTMaitriseOeuvreDTO>(TaTMaitriseOeuvreDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTMaitriseOeuvreDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTMaitriseOeuvreDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTMaitriseOeuvre value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTMaitriseOeuvre value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
