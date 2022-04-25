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
import fr.ylyade.courtage.dao.ITaTalonComptableDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTalonComptableDTO;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaTalonComptable;
import fr.ylyade.courtage.model.TaTalonComptable;
import fr.ylyade.courtage.model.mapper.TaTalonComptableMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTalonComptableServiceRemote;


/**
 * Session Bean implementation class TaTalonComptableBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTalonComptableService extends AbstractApplicationDAOServer<TaTalonComptable, TaTalonComptableDTO> implements ITaTalonComptableServiceRemote {

	static Logger logger = Logger.getLogger(TaTalonComptableService.class);

	@Inject private ITaTalonComptableDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaGenCodeExServiceRemote gencode;

	/**
	 * Default constructor. 
	 */
	public TaTalonComptableService() {
		super(TaTalonComptable.class,TaTalonComptableDTO.class);
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaTalonComptable.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaTalonComptable.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaTalonComptable.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//	private String defaultJPQLQuery = "select a from TaTalonComptable a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTalonComptable transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTalonComptable transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTalonComptable persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTalonComptable()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTalonComptable merge(TaTalonComptable detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTalonComptable merge(TaTalonComptable detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTalonComptable findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTalonComptable findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTalonComptable> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTalonComptableDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTalonComptableDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTalonComptable> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTalonComptableDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTalonComptableDTO entityToDTO(TaTalonComptable entity) {
//		TaTalonComptableDTO dto = new TaTalonComptableDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTalonComptableMapper mapper = new TaTalonComptableMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTalonComptableDTO> listEntityToListDTO(List<TaTalonComptable> entity) {
		List<TaTalonComptableDTO> l = new ArrayList<TaTalonComptableDTO>();

		for (TaTalonComptable taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTalonComptableDTO> selectAllDTO() {
		System.out.println("List of TaTalonComptableDTO EJB :");
		ArrayList<TaTalonComptableDTO> liste = new ArrayList<TaTalonComptableDTO>();

		List<TaTalonComptable> projects = selectAll();
		for(TaTalonComptable project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTalonComptableDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTalonComptableDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTalonComptableDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTalonComptableDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTalonComptableDTO dto, String validationContext) throws EJBException {
		try {
			TaTalonComptableMapper mapper = new TaTalonComptableMapper();
			TaTalonComptable entity = null;
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
	
	public void persistDTO(TaTalonComptableDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTalonComptableDTO dto, String validationContext) throws CreateException {
		try {
			TaTalonComptableMapper mapper = new TaTalonComptableMapper();
			TaTalonComptable entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTalonComptableDTO dto) throws RemoveException {
		try {
			TaTalonComptableMapper mapper = new TaTalonComptableMapper();
			TaTalonComptable entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTalonComptable refresh(TaTalonComptable persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTalonComptable value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTalonComptable value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTalonComptableDTO dto, String validationContext) {
		try {
			TaTalonComptableMapper mapper = new TaTalonComptableMapper();
			TaTalonComptable entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTalonComptableDTO> validator = new BeanValidator<TaTalonComptableDTO>(TaTalonComptableDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTalonComptableDTO dto, String propertyName, String validationContext) {
		try {
			TaTalonComptableMapper mapper = new TaTalonComptableMapper();
			TaTalonComptable entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTalonComptableDTO> validator = new BeanValidator<TaTalonComptableDTO>(TaTalonComptableDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTalonComptableDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTalonComptableDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTalonComptable value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTalonComptable value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
