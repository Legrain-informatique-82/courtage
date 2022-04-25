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
import fr.ylyade.courtage.dao.ITaFamilleActiviteDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaFamilleActiviteDTO;
import fr.ylyade.courtage.model.TaFamilleActivite;
import fr.ylyade.courtage.model.mapper.TaFamilleActiviteMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaFamilleActiviteServiceRemote;


/**
 * Session Bean implementation class TaFamilleActiviteBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaFamilleActiviteService extends AbstractApplicationDAOServer<TaFamilleActivite, TaFamilleActiviteDTO> implements ITaFamilleActiviteServiceRemote {

	static Logger logger = Logger.getLogger(TaFamilleActiviteService.class);

	@Inject private ITaFamilleActiviteDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaFamilleActiviteService() {
		super(TaFamilleActivite.class,TaFamilleActiviteDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaFamilleActivite a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaFamilleActivite transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaFamilleActivite transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaFamilleActivite persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdFamilleActivite()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaFamilleActivite merge(TaFamilleActivite detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaFamilleActivite merge(TaFamilleActivite detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaFamilleActivite findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaFamilleActivite findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaFamilleActivite> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaFamilleActiviteDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaFamilleActiviteDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaFamilleActivite> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaFamilleActiviteDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaFamilleActiviteDTO entityToDTO(TaFamilleActivite entity) {
//		TaFamilleActiviteDTO dto = new TaFamilleActiviteDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaFamilleActiviteMapper mapper = new TaFamilleActiviteMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaFamilleActiviteDTO> listEntityToListDTO(List<TaFamilleActivite> entity) {
		List<TaFamilleActiviteDTO> l = new ArrayList<TaFamilleActiviteDTO>();

		for (TaFamilleActivite taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaFamilleActiviteDTO> selectAllDTO() {
		System.out.println("List of TaFamilleActiviteDTO EJB :");
		ArrayList<TaFamilleActiviteDTO> liste = new ArrayList<TaFamilleActiviteDTO>();

		List<TaFamilleActivite> projects = selectAll();
		for(TaFamilleActivite project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaFamilleActiviteDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaFamilleActiviteDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaFamilleActiviteDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaFamilleActiviteDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaFamilleActiviteDTO dto, String validationContext) throws EJBException {
		try {
			TaFamilleActiviteMapper mapper = new TaFamilleActiviteMapper();
			TaFamilleActivite entity = null;
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
	
	public void persistDTO(TaFamilleActiviteDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaFamilleActiviteDTO dto, String validationContext) throws CreateException {
		try {
			TaFamilleActiviteMapper mapper = new TaFamilleActiviteMapper();
			TaFamilleActivite entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaFamilleActiviteDTO dto) throws RemoveException {
		try {
			TaFamilleActiviteMapper mapper = new TaFamilleActiviteMapper();
			TaFamilleActivite entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaFamilleActivite refresh(TaFamilleActivite persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaFamilleActivite value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaFamilleActivite value, String propertyName, String validationContext) {
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
	public void validateDTO(TaFamilleActiviteDTO dto, String validationContext) {
		try {
			TaFamilleActiviteMapper mapper = new TaFamilleActiviteMapper();
			TaFamilleActivite entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaFamilleActiviteDTO> validator = new BeanValidator<TaFamilleActiviteDTO>(TaFamilleActiviteDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaFamilleActiviteDTO dto, String propertyName, String validationContext) {
		try {
			TaFamilleActiviteMapper mapper = new TaFamilleActiviteMapper();
			TaFamilleActivite entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaFamilleActiviteDTO> validator = new BeanValidator<TaFamilleActiviteDTO>(TaFamilleActiviteDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaFamilleActiviteDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaFamilleActiviteDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaFamilleActivite value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaFamilleActivite value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
