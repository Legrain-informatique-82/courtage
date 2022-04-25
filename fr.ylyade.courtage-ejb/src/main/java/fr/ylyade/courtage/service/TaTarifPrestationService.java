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
import fr.ylyade.courtage.dao.ITaTarifPrestationDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTarifPrestationDTO;
import fr.ylyade.courtage.model.TaTarifPrestation;
import fr.ylyade.courtage.model.mapper.TaTarifPrestationMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTarifPrestationServiceRemote;


/**
 * Session Bean implementation class TaTarifPrestationBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTarifPrestationService extends AbstractApplicationDAOServer<TaTarifPrestation, TaTarifPrestationDTO> implements ITaTarifPrestationServiceRemote {

	static Logger logger = Logger.getLogger(TaTarifPrestationService.class);

	@Inject private ITaTarifPrestationDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTarifPrestationService() {
		super(TaTarifPrestation.class,TaTarifPrestationDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTarifPrestation a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTarifPrestation transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTarifPrestation transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTarifPrestation persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTarifPrestation()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTarifPrestation merge(TaTarifPrestation detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTarifPrestation merge(TaTarifPrestation detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTarifPrestation findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTarifPrestation findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTarifPrestation> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTarifPrestationDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTarifPrestationDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTarifPrestation> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTarifPrestationDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTarifPrestationDTO entityToDTO(TaTarifPrestation entity) {
//		TaTarifPrestationDTO dto = new TaTarifPrestationDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTarifPrestationMapper mapper = new TaTarifPrestationMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTarifPrestationDTO> listEntityToListDTO(List<TaTarifPrestation> entity) {
		List<TaTarifPrestationDTO> l = new ArrayList<TaTarifPrestationDTO>();

		for (TaTarifPrestation taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTarifPrestationDTO> selectAllDTO() {
		System.out.println("List of TaTarifPrestationDTO EJB :");
		ArrayList<TaTarifPrestationDTO> liste = new ArrayList<TaTarifPrestationDTO>();

		List<TaTarifPrestation> projects = selectAll();
		for(TaTarifPrestation project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTarifPrestationDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTarifPrestationDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTarifPrestationDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTarifPrestationDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTarifPrestationDTO dto, String validationContext) throws EJBException {
		try {
			TaTarifPrestationMapper mapper = new TaTarifPrestationMapper();
			TaTarifPrestation entity = null;
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
	
	public void persistDTO(TaTarifPrestationDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTarifPrestationDTO dto, String validationContext) throws CreateException {
		try {
			TaTarifPrestationMapper mapper = new TaTarifPrestationMapper();
			TaTarifPrestation entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTarifPrestationDTO dto) throws RemoveException {
		try {
			TaTarifPrestationMapper mapper = new TaTarifPrestationMapper();
			TaTarifPrestation entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTarifPrestation refresh(TaTarifPrestation persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTarifPrestation value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTarifPrestation value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTarifPrestationDTO dto, String validationContext) {
		try {
			TaTarifPrestationMapper mapper = new TaTarifPrestationMapper();
			TaTarifPrestation entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTarifPrestationDTO> validator = new BeanValidator<TaTarifPrestationDTO>(TaTarifPrestationDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTarifPrestationDTO dto, String propertyName, String validationContext) {
		try {
			TaTarifPrestationMapper mapper = new TaTarifPrestationMapper();
			TaTarifPrestation entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTarifPrestationDTO> validator = new BeanValidator<TaTarifPrestationDTO>(TaTarifPrestationDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTarifPrestationDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTarifPrestationDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTarifPrestation value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTarifPrestation value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
