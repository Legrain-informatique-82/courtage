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
import fr.ylyade.courtage.dao.ITaCourtierDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.mapper.TaCourtierMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;


/**
 * Session Bean implementation class TaCourtierBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaCourtierService extends AbstractApplicationDAOServer<TaCourtier, TaCourtierDTO> implements ITaCourtierServiceRemote {

	static Logger logger = Logger.getLogger(TaCourtierService.class);

	@Inject private ITaCourtierDAO dao;
	
	@EJB private ITaGenCodeExServiceRemote gencode;
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaCourtierService() {
		super(TaCourtier.class,TaCourtierDTO.class);
	}
	
	public TaCourtier findByIdUtilisateur(int idUtilisateur) {
		return dao.findByIdUtilisateur(idUtilisateur);
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaCourtier.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaCourtier.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaCourtier.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<TaCourtierDTO> findByCodeLight(String code) {
		return dao.findByCodeLight(code);
	}

	public List<TaCourtierDTO> findAllLight() {
		return dao.findAllLight();
	}
	public List<TaCourtierDTO> findAllInactif() {
		return dao.findAllInactif();
	}
	public List<TaCourtierDTO> findAllActif(){
		return dao.findAllActif();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public TaCourtier findByIdentifiantAndByPassword(String identifiant, String password) {
		return dao.findByIdentifiantAndByPassword(identifiant, password);
	}
	public void persist(TaCourtier transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaCourtier transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaCourtier persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdCourtier()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaCourtier merge(TaCourtier detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaCourtier merge(TaCourtier detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaCourtier findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaCourtier findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaCourtier> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaCourtierDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaCourtierDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaCourtier> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaCourtierDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaCourtierDTO entityToDTO(TaCourtier entity) {
//		TaCourtierDTO dto = new TaCourtierDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaCourtierMapper mapper = new TaCourtierMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaCourtierDTO> listEntityToListDTO(List<TaCourtier> entity) {
		List<TaCourtierDTO> l = new ArrayList<TaCourtierDTO>();

		for (TaCourtier taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaCourtierDTO> selectAllDTO() {
		System.out.println("List of TaCourtierDTO EJB :");
		ArrayList<TaCourtierDTO> liste = new ArrayList<TaCourtierDTO>();

		List<TaCourtier> projects = selectAll();
		for(TaCourtier project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaCourtierDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaCourtierDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaCourtierDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaCourtierDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaCourtierDTO dto, String validationContext) throws EJBException {
		try {
			TaCourtierMapper mapper = new TaCourtierMapper();
			TaCourtier entity = null;
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
	
	public void persistDTO(TaCourtierDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaCourtierDTO dto, String validationContext) throws CreateException {
		try {
			TaCourtierMapper mapper = new TaCourtierMapper();
			TaCourtier entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaCourtierDTO dto) throws RemoveException {
		try {
			TaCourtierMapper mapper = new TaCourtierMapper();
			TaCourtier entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaCourtier refresh(TaCourtier persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaCourtier value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaCourtier value, String propertyName, String validationContext) {
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
	public void validateDTO(TaCourtierDTO dto, String validationContext) {
		try {
			TaCourtierMapper mapper = new TaCourtierMapper();
			TaCourtier entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaCourtierDTO> validator = new BeanValidator<TaCourtierDTO>(TaCourtierDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaCourtierDTO dto, String propertyName, String validationContext) {
		try {
			TaCourtierMapper mapper = new TaCourtierMapper();
			TaCourtier entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaCourtierDTO> validator = new BeanValidator<TaCourtierDTO>(TaCourtierDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaCourtierDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaCourtierDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaCourtier value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaCourtier value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
