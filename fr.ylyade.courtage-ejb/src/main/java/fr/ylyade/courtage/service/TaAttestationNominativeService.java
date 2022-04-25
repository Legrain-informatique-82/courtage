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
import fr.ylyade.courtage.dao.ITaAttestationNominativeDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaAttestationNominativeDTO;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.mapper.TaAttestationNominativeMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaAttestationNominativeServiceRemote;


/**
 * Session Bean implementation class TaAttestationNominativeBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaAttestationNominativeService extends AbstractApplicationDAOServer<TaAttestationNominative, TaAttestationNominativeDTO> implements ITaAttestationNominativeServiceRemote {

	static Logger logger = Logger.getLogger(TaAttestationNominativeService.class);

	@Inject private ITaAttestationNominativeDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaAttestationNominativeService() {
		super(TaAttestationNominative.class,TaAttestationNominativeDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaAttestationNominative a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaAttestationNominative transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaAttestationNominative transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaAttestationNominative persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdAttestationNominative()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaAttestationNominative merge(TaAttestationNominative detachedInstance) {
		return merge(detachedInstance, null);
	}
	
	public List<TaAttestationNominative> findAllByIdDossier(Integer idDossierRcd) {
		return dao.findAllByIdDossier(idDossierRcd);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaAttestationNominative merge(TaAttestationNominative detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaAttestationNominative findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaAttestationNominative findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaAttestationNominative> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaAttestationNominativeDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaAttestationNominativeDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaAttestationNominative> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaAttestationNominativeDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaAttestationNominativeDTO entityToDTO(TaAttestationNominative entity) {
//		TaAttestationNominativeDTO dto = new TaAttestationNominativeDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaAttestationNominativeMapper mapper = new TaAttestationNominativeMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaAttestationNominativeDTO> listEntityToListDTO(List<TaAttestationNominative> entity) {
		List<TaAttestationNominativeDTO> l = new ArrayList<TaAttestationNominativeDTO>();

		for (TaAttestationNominative taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaAttestationNominativeDTO> selectAllDTO() {
		System.out.println("List of TaAttestationNominativeDTO EJB :");
		ArrayList<TaAttestationNominativeDTO> liste = new ArrayList<TaAttestationNominativeDTO>();

		List<TaAttestationNominative> projects = selectAll();
		for(TaAttestationNominative project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaAttestationNominativeDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaAttestationNominativeDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaAttestationNominativeDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaAttestationNominativeDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaAttestationNominativeDTO dto, String validationContext) throws EJBException {
		try {
			TaAttestationNominativeMapper mapper = new TaAttestationNominativeMapper();
			TaAttestationNominative entity = null;
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
	
	public void persistDTO(TaAttestationNominativeDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaAttestationNominativeDTO dto, String validationContext) throws CreateException {
		try {
			TaAttestationNominativeMapper mapper = new TaAttestationNominativeMapper();
			TaAttestationNominative entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaAttestationNominativeDTO dto) throws RemoveException {
		try {
			TaAttestationNominativeMapper mapper = new TaAttestationNominativeMapper();
			TaAttestationNominative entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaAttestationNominative refresh(TaAttestationNominative persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaAttestationNominative value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaAttestationNominative value, String propertyName, String validationContext) {
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
	public void validateDTO(TaAttestationNominativeDTO dto, String validationContext) {
		try {
			TaAttestationNominativeMapper mapper = new TaAttestationNominativeMapper();
			TaAttestationNominative entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaAttestationNominativeDTO> validator = new BeanValidator<TaAttestationNominativeDTO>(TaAttestationNominativeDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaAttestationNominativeDTO dto, String propertyName, String validationContext) {
		try {
			TaAttestationNominativeMapper mapper = new TaAttestationNominativeMapper();
			TaAttestationNominative entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaAttestationNominativeDTO> validator = new BeanValidator<TaAttestationNominativeDTO>(TaAttestationNominativeDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaAttestationNominativeDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaAttestationNominativeDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaAttestationNominative value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaAttestationNominative value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
