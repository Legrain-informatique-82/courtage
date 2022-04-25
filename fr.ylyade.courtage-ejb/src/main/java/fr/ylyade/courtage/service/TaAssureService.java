package fr.ylyade.courtage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaAssureDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.mapper.TaAssureMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaAssureServiceRemote;


/**
 * Session Bean implementation class TaAssureBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaAssureService extends AbstractApplicationDAOServer<TaAssure, TaAssureDTO> implements ITaAssureServiceRemote {

	static Logger logger = Logger.getLogger(TaAssureService.class);

	@Inject private ITaAssureDAO dao;
	
	@EJB private ITaGenCodeExServiceRemote gencode;
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaAssureService() {
		super(TaAssure.class,TaAssureDTO.class);
	}
	
	public TaAssure findByIdUtilisateur(int idUtilisateur) {
		return dao.findByIdUtilisateur(idUtilisateur);
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaAssure.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaAssure.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaAssure.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<TaAssureDTO> findByCodeLight(String code) {
		return dao.findByCodeLight(code);
	}

	public List<TaAssureDTO> findAllLight() {
		return dao.findAllLight();
	}
	public List<TaAssureDTO> findAllLightPlusExtraction() {
		return dao.findAllLightPlusExtraction();
	}
	
	public List<TaAssureDTO> findAllByIdCourtier(int idCourtier) {
		return dao.findAllByIdCourtier(idCourtier);
	}
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		return dao.countActiveByIdCourtier(idCourtier);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaAssure transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaAssure transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaAssure persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdAssure()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaAssure merge(TaAssure detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaAssure merge(TaAssure detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaAssure findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaAssure findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaAssure> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaAssureDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaAssureDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaAssure> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaAssureDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaAssureDTO entityToDTO(TaAssure entity) {
//		TaAssureDTO dto = new TaAssureDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaAssureMapper mapper = new TaAssureMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaAssureDTO> listEntityToListDTO(List<TaAssure> entity) {
		List<TaAssureDTO> l = new ArrayList<TaAssureDTO>();

		for (TaAssure taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaAssureDTO> selectAllDTO() {
		System.out.println("List of TaAssureDTO EJB :");
		ArrayList<TaAssureDTO> liste = new ArrayList<TaAssureDTO>();

		List<TaAssure> projects = selectAll();
		for(TaAssure project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaAssureDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaAssureDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaAssureDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaAssureDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaAssureDTO dto, String validationContext) throws EJBException {
		try {
			TaAssureMapper mapper = new TaAssureMapper();
			TaAssure entity = null;
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
	
	public void persistDTO(TaAssureDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaAssureDTO dto, String validationContext) throws CreateException {
		try {
			TaAssureMapper mapper = new TaAssureMapper();
			TaAssure entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaAssureDTO dto) throws RemoveException {
		try {
			TaAssureMapper mapper = new TaAssureMapper();
			TaAssure entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaAssure refresh(TaAssure persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaAssure value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaAssure value, String propertyName, String validationContext) {
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
	public void validateDTO(TaAssureDTO dto, String validationContext) {
		try {
			TaAssureMapper mapper = new TaAssureMapper();
			TaAssure entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaAssureDTO> validator = new BeanValidator<TaAssureDTO>(TaAssureDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaAssureDTO dto, String propertyName, String validationContext) {
		try {
			TaAssureMapper mapper = new TaAssureMapper();
			TaAssure entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaAssureDTO> validator = new BeanValidator<TaAssureDTO>(TaAssureDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaAssureDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaAssureDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaAssure value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaAssure value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
