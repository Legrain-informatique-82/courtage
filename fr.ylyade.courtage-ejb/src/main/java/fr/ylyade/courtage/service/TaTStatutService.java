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
import fr.ylyade.courtage.dao.ITaTStatutDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTStatutDTO;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.model.mapper.TaTStatutMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTStatutServiceRemote;


/**
 * Session Bean implementation class TaTStatutBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTStatutService extends AbstractApplicationDAOServer<TaTStatut, TaTStatutDTO> implements ITaTStatutServiceRemote {

	static Logger logger = Logger.getLogger(TaTStatutService.class);

	@Inject private ITaTStatutDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTStatutService() {
		super(TaTStatut.class,TaTStatutDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTStatut a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTStatut transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTStatut transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTStatut persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTStatut()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTStatut merge(TaTStatut detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTStatut merge(TaTStatut detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTStatut findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTStatut findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTStatut> selectAll() {
		return dao.selectAll();
	}
	
	public List<TaTStatut> findAllStatutByIdDossier(Integer idDossierRcd) {
		return dao.findAllStatutByIdDossier(idDossierRcd);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTStatutDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTStatutDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTStatut> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTStatutDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTStatutDTO entityToDTO(TaTStatut entity) {
//		TaTStatutDTO dto = new TaTStatutDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTStatutMapper mapper = new TaTStatutMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTStatutDTO> listEntityToListDTO(List<TaTStatut> entity) {
		List<TaTStatutDTO> l = new ArrayList<TaTStatutDTO>();

		for (TaTStatut taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTStatutDTO> selectAllDTO() {
		System.out.println("List of TaTStatutDTO EJB :");
		ArrayList<TaTStatutDTO> liste = new ArrayList<TaTStatutDTO>();

		List<TaTStatut> projects = selectAll();
		for(TaTStatut project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTStatutDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTStatutDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTStatutDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTStatutDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTStatutDTO dto, String validationContext) throws EJBException {
		try {
			TaTStatutMapper mapper = new TaTStatutMapper();
			TaTStatut entity = null;
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
	
	public void persistDTO(TaTStatutDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTStatutDTO dto, String validationContext) throws CreateException {
		try {
			TaTStatutMapper mapper = new TaTStatutMapper();
			TaTStatut entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTStatutDTO dto) throws RemoveException {
		try {
			TaTStatutMapper mapper = new TaTStatutMapper();
			TaTStatut entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTStatut refresh(TaTStatut persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTStatut value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTStatut value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTStatutDTO dto, String validationContext) {
		try {
			TaTStatutMapper mapper = new TaTStatutMapper();
			TaTStatut entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTStatutDTO> validator = new BeanValidator<TaTStatutDTO>(TaTStatutDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTStatutDTO dto, String propertyName, String validationContext) {
		try {
			TaTStatutMapper mapper = new TaTStatutMapper();
			TaTStatut entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTStatutDTO> validator = new BeanValidator<TaTStatutDTO>(TaTStatutDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTStatutDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTStatutDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTStatut value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTStatut value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
