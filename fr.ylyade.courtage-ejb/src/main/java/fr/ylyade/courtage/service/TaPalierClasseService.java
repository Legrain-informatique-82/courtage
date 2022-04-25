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
import fr.ylyade.courtage.dao.ITaPalierClasseDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaPalierClasseDTO;
import fr.ylyade.courtage.model.TaPalierClasse;
import fr.ylyade.courtage.model.mapper.TaPalierClasseMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaPalierClasseServiceRemote;


/**
 * Session Bean implementation class TaPalierClasseBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaPalierClasseService extends AbstractApplicationDAOServer<TaPalierClasse, TaPalierClasseDTO> implements ITaPalierClasseServiceRemote {

	static Logger logger = Logger.getLogger(TaPalierClasseService.class);

	@Inject private ITaPalierClasseDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaPalierClasseService() {
		super(TaPalierClasse.class,TaPalierClasseDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaPalierClasse a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaPalierClasse transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaPalierClasse transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaPalierClasse persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdPalierClasse()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaPalierClasse merge(TaPalierClasse detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaPalierClasse merge(TaPalierClasse detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaPalierClasse findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaPalierClasse findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaPalierClasse> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaPalierClasseDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaPalierClasseDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaPalierClasse> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaPalierClasseDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaPalierClasseDTO entityToDTO(TaPalierClasse entity) {
//		TaPalierClasseDTO dto = new TaPalierClasseDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaPalierClasseMapper mapper = new TaPalierClasseMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaPalierClasseDTO> listEntityToListDTO(List<TaPalierClasse> entity) {
		List<TaPalierClasseDTO> l = new ArrayList<TaPalierClasseDTO>();

		for (TaPalierClasse taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaPalierClasseDTO> selectAllDTO() {
		System.out.println("List of TaPalierClasseDTO EJB :");
		ArrayList<TaPalierClasseDTO> liste = new ArrayList<TaPalierClasseDTO>();

		List<TaPalierClasse> projects = selectAll();
		for(TaPalierClasse project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaPalierClasseDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaPalierClasseDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaPalierClasseDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaPalierClasseDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaPalierClasseDTO dto, String validationContext) throws EJBException {
		try {
			TaPalierClasseMapper mapper = new TaPalierClasseMapper();
			TaPalierClasse entity = null;
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
	
	public void persistDTO(TaPalierClasseDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaPalierClasseDTO dto, String validationContext) throws CreateException {
		try {
			TaPalierClasseMapper mapper = new TaPalierClasseMapper();
			TaPalierClasse entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaPalierClasseDTO dto) throws RemoveException {
		try {
			TaPalierClasseMapper mapper = new TaPalierClasseMapper();
			TaPalierClasse entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaPalierClasse refresh(TaPalierClasse persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaPalierClasse value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaPalierClasse value, String propertyName, String validationContext) {
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
	public void validateDTO(TaPalierClasseDTO dto, String validationContext) {
		try {
			TaPalierClasseMapper mapper = new TaPalierClasseMapper();
			TaPalierClasse entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaPalierClasseDTO> validator = new BeanValidator<TaPalierClasseDTO>(TaPalierClasseDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaPalierClasseDTO dto, String propertyName, String validationContext) {
		try {
			TaPalierClasseMapper mapper = new TaPalierClasseMapper();
			TaPalierClasse entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaPalierClasseDTO> validator = new BeanValidator<TaPalierClasseDTO>(TaPalierClasseDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaPalierClasseDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaPalierClasseDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaPalierClasse value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaPalierClasse value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
