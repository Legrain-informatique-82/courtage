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
import fr.ylyade.courtage.dao.ITaTUtilisateurDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTUtilisateurDTO;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.mapper.TaTUtilisateurMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;


/**
 * Session Bean implementation class TaTUtilisateurBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTUtilisateurService extends AbstractApplicationDAOServer<TaTUtilisateur, TaTUtilisateurDTO> implements ITaTUtilisateurServiceRemote {

	static Logger logger = Logger.getLogger(TaTUtilisateurService.class);

	@Inject private ITaTUtilisateurDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTUtilisateurService() {
		super(TaTUtilisateur.class,TaTUtilisateurDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTUtilisateur a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTUtilisateur transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTUtilisateur transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTUtilisateur persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTUtilisateur()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTUtilisateur merge(TaTUtilisateur detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTUtilisateur merge(TaTUtilisateur detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTUtilisateur findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTUtilisateur findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTUtilisateur> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTUtilisateurDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTUtilisateurDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTUtilisateur> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTUtilisateurDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTUtilisateurDTO entityToDTO(TaTUtilisateur entity) {
//		TaTUtilisateurDTO dto = new TaTUtilisateurDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTUtilisateurMapper mapper = new TaTUtilisateurMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTUtilisateurDTO> listEntityToListDTO(List<TaTUtilisateur> entity) {
		List<TaTUtilisateurDTO> l = new ArrayList<TaTUtilisateurDTO>();

		for (TaTUtilisateur taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTUtilisateurDTO> selectAllDTO() {
		System.out.println("List of TaTUtilisateurDTO EJB :");
		ArrayList<TaTUtilisateurDTO> liste = new ArrayList<TaTUtilisateurDTO>();

		List<TaTUtilisateur> projects = selectAll();
		for(TaTUtilisateur project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTUtilisateurDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTUtilisateurDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTUtilisateurDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTUtilisateurDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTUtilisateurDTO dto, String validationContext) throws EJBException {
		try {
			TaTUtilisateurMapper mapper = new TaTUtilisateurMapper();
			TaTUtilisateur entity = null;
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
	
	public void persistDTO(TaTUtilisateurDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTUtilisateurDTO dto, String validationContext) throws CreateException {
		try {
			TaTUtilisateurMapper mapper = new TaTUtilisateurMapper();
			TaTUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTUtilisateurDTO dto) throws RemoveException {
		try {
			TaTUtilisateurMapper mapper = new TaTUtilisateurMapper();
			TaTUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTUtilisateur refresh(TaTUtilisateur persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTUtilisateur value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTUtilisateur value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTUtilisateurDTO dto, String validationContext) {
		try {
			TaTUtilisateurMapper mapper = new TaTUtilisateurMapper();
			TaTUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTUtilisateurDTO> validator = new BeanValidator<TaTUtilisateurDTO>(TaTUtilisateurDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTUtilisateurDTO dto, String propertyName, String validationContext) {
		try {
			TaTUtilisateurMapper mapper = new TaTUtilisateurMapper();
			TaTUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTUtilisateurDTO> validator = new BeanValidator<TaTUtilisateurDTO>(TaTUtilisateurDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTUtilisateurDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTUtilisateurDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTUtilisateur value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTUtilisateur value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
