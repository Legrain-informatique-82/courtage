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
import fr.ylyade.courtage.dao.ITaTGroupeTarifDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTGroupeTarifDTO;
import fr.ylyade.courtage.model.TaTGroupeTarif;
import fr.ylyade.courtage.model.mapper.TaTGroupeTarifMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTGroupeTarifServiceRemote;


/**
 * Session Bean implementation class TaTGroupeTarifBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTGroupeTarifService extends AbstractApplicationDAOServer<TaTGroupeTarif, TaTGroupeTarifDTO> implements ITaTGroupeTarifServiceRemote {

	static Logger logger = Logger.getLogger(TaTGroupeTarifService.class);

	@Inject private ITaTGroupeTarifDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTGroupeTarifService() {
		super(TaTGroupeTarif.class,TaTGroupeTarifDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTGroupeTarif a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTGroupeTarif transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTGroupeTarif transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTGroupeTarif persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTGroupeTarif()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTGroupeTarif merge(TaTGroupeTarif detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTGroupeTarif merge(TaTGroupeTarif detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTGroupeTarif findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTGroupeTarif findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTGroupeTarif> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTGroupeTarifDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTGroupeTarifDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTGroupeTarif> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTGroupeTarifDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTGroupeTarifDTO entityToDTO(TaTGroupeTarif entity) {
//		TaTGroupeTarifDTO dto = new TaTGroupeTarifDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTGroupeTarifMapper mapper = new TaTGroupeTarifMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTGroupeTarifDTO> listEntityToListDTO(List<TaTGroupeTarif> entity) {
		List<TaTGroupeTarifDTO> l = new ArrayList<TaTGroupeTarifDTO>();

		for (TaTGroupeTarif taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTGroupeTarifDTO> selectAllDTO() {
		System.out.println("List of TaTGroupeTarifDTO EJB :");
		ArrayList<TaTGroupeTarifDTO> liste = new ArrayList<TaTGroupeTarifDTO>();

		List<TaTGroupeTarif> projects = selectAll();
		for(TaTGroupeTarif project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTGroupeTarifDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTGroupeTarifDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTGroupeTarifDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTGroupeTarifDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTGroupeTarifDTO dto, String validationContext) throws EJBException {
		try {
			TaTGroupeTarifMapper mapper = new TaTGroupeTarifMapper();
			TaTGroupeTarif entity = null;
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
	
	public void persistDTO(TaTGroupeTarifDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTGroupeTarifDTO dto, String validationContext) throws CreateException {
		try {
			TaTGroupeTarifMapper mapper = new TaTGroupeTarifMapper();
			TaTGroupeTarif entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTGroupeTarifDTO dto) throws RemoveException {
		try {
			TaTGroupeTarifMapper mapper = new TaTGroupeTarifMapper();
			TaTGroupeTarif entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTGroupeTarif refresh(TaTGroupeTarif persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTGroupeTarif value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTGroupeTarif value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTGroupeTarifDTO dto, String validationContext) {
		try {
			TaTGroupeTarifMapper mapper = new TaTGroupeTarifMapper();
			TaTGroupeTarif entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTGroupeTarifDTO> validator = new BeanValidator<TaTGroupeTarifDTO>(TaTGroupeTarifDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTGroupeTarifDTO dto, String propertyName, String validationContext) {
		try {
			TaTGroupeTarifMapper mapper = new TaTGroupeTarifMapper();
			TaTGroupeTarif entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTGroupeTarifDTO> validator = new BeanValidator<TaTGroupeTarifDTO>(TaTGroupeTarifDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTGroupeTarifDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTGroupeTarifDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTGroupeTarif value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTGroupeTarif value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
