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
import fr.ylyade.courtage.dao.ITaTFraisImpayeDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTFraisImpayeDTO;
import fr.ylyade.courtage.model.TaTFraisImpaye;
import fr.ylyade.courtage.model.mapper.TaTFraisImpayeMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFraisImpayeServiceRemote;


/**
 * Session Bean implementation class TaTFraisImpayeBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTFraisImpayeService extends AbstractApplicationDAOServer<TaTFraisImpaye, TaTFraisImpayeDTO> implements ITaTFraisImpayeServiceRemote {

	static Logger logger = Logger.getLogger(TaTFraisImpayeService.class);

	@Inject private ITaTFraisImpayeDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTFraisImpayeService() {
		super(TaTFraisImpaye.class,TaTFraisImpayeDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTFraisImpaye a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTFraisImpaye transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTFraisImpaye transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTFraisImpaye persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTFraisImpaye()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTFraisImpaye merge(TaTFraisImpaye detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTFraisImpaye merge(TaTFraisImpaye detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTFraisImpaye findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTFraisImpaye findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTFraisImpaye> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTFraisImpayeDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTFraisImpayeDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTFraisImpaye> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTFraisImpayeDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTFraisImpayeDTO entityToDTO(TaTFraisImpaye entity) {
//		TaTFraisImpayeDTO dto = new TaTFraisImpayeDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTFraisImpayeMapper mapper = new TaTFraisImpayeMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTFraisImpayeDTO> listEntityToListDTO(List<TaTFraisImpaye> entity) {
		List<TaTFraisImpayeDTO> l = new ArrayList<TaTFraisImpayeDTO>();

		for (TaTFraisImpaye taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTFraisImpayeDTO> selectAllDTO() {
		System.out.println("List of TaTFraisImpayeDTO EJB :");
		ArrayList<TaTFraisImpayeDTO> liste = new ArrayList<TaTFraisImpayeDTO>();

		List<TaTFraisImpaye> projects = selectAll();
		for(TaTFraisImpaye project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTFraisImpayeDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTFraisImpayeDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTFraisImpayeDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTFraisImpayeDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTFraisImpayeDTO dto, String validationContext) throws EJBException {
		try {
			TaTFraisImpayeMapper mapper = new TaTFraisImpayeMapper();
			TaTFraisImpaye entity = null;
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
	
	public void persistDTO(TaTFraisImpayeDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTFraisImpayeDTO dto, String validationContext) throws CreateException {
		try {
			TaTFraisImpayeMapper mapper = new TaTFraisImpayeMapper();
			TaTFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTFraisImpayeDTO dto) throws RemoveException {
		try {
			TaTFraisImpayeMapper mapper = new TaTFraisImpayeMapper();
			TaTFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTFraisImpaye refresh(TaTFraisImpaye persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTFraisImpaye value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTFraisImpaye value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTFraisImpayeDTO dto, String validationContext) {
		try {
			TaTFraisImpayeMapper mapper = new TaTFraisImpayeMapper();
			TaTFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTFraisImpayeDTO> validator = new BeanValidator<TaTFraisImpayeDTO>(TaTFraisImpayeDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTFraisImpayeDTO dto, String propertyName, String validationContext) {
		try {
			TaTFraisImpayeMapper mapper = new TaTFraisImpayeMapper();
			TaTFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTFraisImpayeDTO> validator = new BeanValidator<TaTFraisImpayeDTO>(TaTFraisImpayeDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTFraisImpayeDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTFraisImpayeDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTFraisImpaye value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTFraisImpaye value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
