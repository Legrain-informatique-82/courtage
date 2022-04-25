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
import fr.ylyade.courtage.dao.ITaTFranchiseDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTFranchiseDTO;
import fr.ylyade.courtage.model.TaTFranchise;
import fr.ylyade.courtage.model.mapper.TaTFranchiseMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFranchiseServiceRemote;


/**
 * Session Bean implementation class TaTFranchiseBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTFranchiseService extends AbstractApplicationDAOServer<TaTFranchise, TaTFranchiseDTO> implements ITaTFranchiseServiceRemote {

	static Logger logger = Logger.getLogger(TaTFranchiseService.class);

	@Inject private ITaTFranchiseDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTFranchiseService() {
		super(TaTFranchise.class,TaTFranchiseDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTFranchise a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTFranchise transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTFranchise transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTFranchise persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTFranchise()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTFranchise merge(TaTFranchise detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTFranchise merge(TaTFranchise detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTFranchise findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTFranchise findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTFranchise> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTFranchiseDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTFranchiseDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTFranchise> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTFranchiseDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTFranchiseDTO entityToDTO(TaTFranchise entity) {
//		TaTFranchiseDTO dto = new TaTFranchiseDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTFranchiseMapper mapper = new TaTFranchiseMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTFranchiseDTO> listEntityToListDTO(List<TaTFranchise> entity) {
		List<TaTFranchiseDTO> l = new ArrayList<TaTFranchiseDTO>();

		for (TaTFranchise taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTFranchiseDTO> selectAllDTO() {
		System.out.println("List of TaTFranchiseDTO EJB :");
		ArrayList<TaTFranchiseDTO> liste = new ArrayList<TaTFranchiseDTO>();

		List<TaTFranchise> projects = selectAll();
		for(TaTFranchise project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTFranchiseDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTFranchiseDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTFranchiseDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTFranchiseDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTFranchiseDTO dto, String validationContext) throws EJBException {
		try {
			TaTFranchiseMapper mapper = new TaTFranchiseMapper();
			TaTFranchise entity = null;
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
	
	public void persistDTO(TaTFranchiseDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTFranchiseDTO dto, String validationContext) throws CreateException {
		try {
			TaTFranchiseMapper mapper = new TaTFranchiseMapper();
			TaTFranchise entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTFranchiseDTO dto) throws RemoveException {
		try {
			TaTFranchiseMapper mapper = new TaTFranchiseMapper();
			TaTFranchise entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTFranchise refresh(TaTFranchise persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTFranchise value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTFranchise value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTFranchiseDTO dto, String validationContext) {
		try {
			TaTFranchiseMapper mapper = new TaTFranchiseMapper();
			TaTFranchise entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTFranchiseDTO> validator = new BeanValidator<TaTFranchiseDTO>(TaTFranchiseDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTFranchiseDTO dto, String propertyName, String validationContext) {
		try {
			TaTFranchiseMapper mapper = new TaTFranchiseMapper();
			TaTFranchise entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTFranchiseDTO> validator = new BeanValidator<TaTFranchiseDTO>(TaTFranchiseDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTFranchiseDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTFranchiseDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTFranchise value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTFranchise value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
