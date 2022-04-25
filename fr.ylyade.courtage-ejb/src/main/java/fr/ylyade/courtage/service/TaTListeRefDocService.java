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
import fr.ylyade.courtage.dao.ITaTListeRefDocDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTListeRefDocDTO;
import fr.ylyade.courtage.model.TaTListeRefDoc;
import fr.ylyade.courtage.model.mapper.TaTListeRefDocMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTListeRefDocServiceRemote;


/**
 * Session Bean implementation class TaTListeRefDocBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTListeRefDocService extends AbstractApplicationDAOServer<TaTListeRefDoc, TaTListeRefDocDTO> implements ITaTListeRefDocServiceRemote {

	static Logger logger = Logger.getLogger(TaTListeRefDocService.class);

	@Inject private ITaTListeRefDocDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTListeRefDocService() {
		super(TaTListeRefDoc.class,TaTListeRefDocDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTListeRefDoc a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTListeRefDoc transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTListeRefDoc transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTListeRefDoc persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTListeRefDoc()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTListeRefDoc merge(TaTListeRefDoc detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTListeRefDoc merge(TaTListeRefDoc detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTListeRefDoc findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTListeRefDoc findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTListeRefDoc> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTListeRefDocDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTListeRefDocDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTListeRefDoc> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTListeRefDocDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTListeRefDocDTO entityToDTO(TaTListeRefDoc entity) {
//		TaTListeRefDocDTO dto = new TaTListeRefDocDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTListeRefDocMapper mapper = new TaTListeRefDocMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTListeRefDocDTO> listEntityToListDTO(List<TaTListeRefDoc> entity) {
		List<TaTListeRefDocDTO> l = new ArrayList<TaTListeRefDocDTO>();

		for (TaTListeRefDoc taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTListeRefDocDTO> selectAllDTO() {
		System.out.println("List of TaTListeRefDocDTO EJB :");
		ArrayList<TaTListeRefDocDTO> liste = new ArrayList<TaTListeRefDocDTO>();

		List<TaTListeRefDoc> projects = selectAll();
		for(TaTListeRefDoc project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTListeRefDocDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTListeRefDocDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTListeRefDocDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTListeRefDocDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTListeRefDocDTO dto, String validationContext) throws EJBException {
		try {
			TaTListeRefDocMapper mapper = new TaTListeRefDocMapper();
			TaTListeRefDoc entity = null;
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
	
	public void persistDTO(TaTListeRefDocDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTListeRefDocDTO dto, String validationContext) throws CreateException {
		try {
			TaTListeRefDocMapper mapper = new TaTListeRefDocMapper();
			TaTListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTListeRefDocDTO dto) throws RemoveException {
		try {
			TaTListeRefDocMapper mapper = new TaTListeRefDocMapper();
			TaTListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTListeRefDoc refresh(TaTListeRefDoc persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTListeRefDoc value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTListeRefDoc value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTListeRefDocDTO dto, String validationContext) {
		try {
			TaTListeRefDocMapper mapper = new TaTListeRefDocMapper();
			TaTListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTListeRefDocDTO> validator = new BeanValidator<TaTListeRefDocDTO>(TaTListeRefDocDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTListeRefDocDTO dto, String propertyName, String validationContext) {
		try {
			TaTListeRefDocMapper mapper = new TaTListeRefDocMapper();
			TaTListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTListeRefDocDTO> validator = new BeanValidator<TaTListeRefDocDTO>(TaTListeRefDocDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTListeRefDocDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTListeRefDocDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTListeRefDoc value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTListeRefDoc value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
