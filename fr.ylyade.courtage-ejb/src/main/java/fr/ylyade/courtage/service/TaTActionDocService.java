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
import fr.ylyade.courtage.dao.ITaTActionDocDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTActionDocDTO;
import fr.ylyade.courtage.model.TaTActionDoc;
import fr.ylyade.courtage.model.mapper.TaTActionDocMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTActionDocServiceRemote;


/**
 * Session Bean implementation class TaTActionDocBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTActionDocService extends AbstractApplicationDAOServer<TaTActionDoc, TaTActionDocDTO> implements ITaTActionDocServiceRemote {

	static Logger logger = Logger.getLogger(TaTActionDocService.class);

	@Inject private ITaTActionDocDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTActionDocService() {
		super(TaTActionDoc.class,TaTActionDocDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTActionDoc a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTActionDoc transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTActionDoc transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTActionDoc persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTActionDoc()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTActionDoc merge(TaTActionDoc detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTActionDoc merge(TaTActionDoc detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTActionDoc findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTActionDoc findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTActionDoc> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTActionDocDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTActionDocDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTActionDoc> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTActionDocDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTActionDocDTO entityToDTO(TaTActionDoc entity) {
//		TaTActionDocDTO dto = new TaTActionDocDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTActionDocMapper mapper = new TaTActionDocMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTActionDocDTO> listEntityToListDTO(List<TaTActionDoc> entity) {
		List<TaTActionDocDTO> l = new ArrayList<TaTActionDocDTO>();

		for (TaTActionDoc taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTActionDocDTO> selectAllDTO() {
		System.out.println("List of TaTActionDocDTO EJB :");
		ArrayList<TaTActionDocDTO> liste = new ArrayList<TaTActionDocDTO>();

		List<TaTActionDoc> projects = selectAll();
		for(TaTActionDoc project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTActionDocDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTActionDocDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTActionDocDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTActionDocDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTActionDocDTO dto, String validationContext) throws EJBException {
		try {
			TaTActionDocMapper mapper = new TaTActionDocMapper();
			TaTActionDoc entity = null;
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
	
	public void persistDTO(TaTActionDocDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTActionDocDTO dto, String validationContext) throws CreateException {
		try {
			TaTActionDocMapper mapper = new TaTActionDocMapper();
			TaTActionDoc entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTActionDocDTO dto) throws RemoveException {
		try {
			TaTActionDocMapper mapper = new TaTActionDocMapper();
			TaTActionDoc entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTActionDoc refresh(TaTActionDoc persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTActionDoc value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTActionDoc value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTActionDocDTO dto, String validationContext) {
		try {
			TaTActionDocMapper mapper = new TaTActionDocMapper();
			TaTActionDoc entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTActionDocDTO> validator = new BeanValidator<TaTActionDocDTO>(TaTActionDocDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTActionDocDTO dto, String propertyName, String validationContext) {
		try {
			TaTActionDocMapper mapper = new TaTActionDocMapper();
			TaTActionDoc entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTActionDocDTO> validator = new BeanValidator<TaTActionDocDTO>(TaTActionDocDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTActionDocDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTActionDocDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTActionDoc value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTActionDoc value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
