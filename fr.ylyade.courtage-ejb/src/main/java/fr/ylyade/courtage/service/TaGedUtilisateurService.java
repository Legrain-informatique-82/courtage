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
import fr.ylyade.courtage.dao.ITaGedUtilisateurDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.mapper.TaGedUtilisateurMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;


/**
 * Session Bean implementation class TaGedUtilisateurBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaGedUtilisateurService extends AbstractApplicationDAOServer<TaGedUtilisateur, TaGedUtilisateurDTO> implements ITaGedUtilisateurServiceRemote {

	static Logger logger = Logger.getLogger(TaGedUtilisateurService.class);

	@Inject private ITaGedUtilisateurDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaGedUtilisateurService() {
		super(TaGedUtilisateur.class,TaGedUtilisateurDTO.class);
	}
	
	public List<TaGedUtilisateur> findAllByIdDossier(Integer idDossier){
		return dao.findAllByIdDossier(idDossier);
	}
	@Override
	public TaGedUtilisateurDTO findByIdDocAndByIdCourtierDTO(Integer idDoc, Integer idCourtier) {
		return dao.findByIdDocAndByIdCourtierDTO(idDoc,idCourtier);
	}

	@Override
	public TaGedUtilisateur findByIdDocAndByIdCourtier(Integer idDoc, Integer idCourtier) {
		return dao.findByIdDocAndByIdCourtier(idDoc,idCourtier);
	}
	
	public TaGedUtilisateur findByCodeListeRefAndByIdDossier(String codeDoc, Integer idDossierRcd) {
		return dao.findByCodeListeRefAndByIdDossier(codeDoc, idDossierRcd);
	}
	
	
	@Override
	public TaGedUtilisateurDTO findByIdDocAndByIdDevisRcProDTO(Integer idDoc, Integer idDevisRcPro) {
		return dao.findByIdDocAndByIdDevisRcProDTO(idDoc,idDevisRcPro);
	}

	@Override
	public TaGedUtilisateur findByIdDocAndByIdDevisRcPro(Integer idDoc, Integer idDevisRcPro) {
		return dao.findByIdDocAndByIdDevisRcPro(idDoc,idDevisRcPro);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaGedUtilisateur transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaGedUtilisateur transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaGedUtilisateur persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdGedUtilisateur()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaGedUtilisateur merge(TaGedUtilisateur detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaGedUtilisateur merge(TaGedUtilisateur detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaGedUtilisateur findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaGedUtilisateur findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaGedUtilisateur> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaGedUtilisateurDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaGedUtilisateurDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaGedUtilisateur> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaGedUtilisateurDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaGedUtilisateurDTO entityToDTO(TaGedUtilisateur entity) {
//		TaGedUtilisateurDTO dto = new TaGedUtilisateurDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaGedUtilisateurMapper mapper = new TaGedUtilisateurMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaGedUtilisateurDTO> listEntityToListDTO(List<TaGedUtilisateur> entity) {
		List<TaGedUtilisateurDTO> l = new ArrayList<TaGedUtilisateurDTO>();

		for (TaGedUtilisateur taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaGedUtilisateurDTO> selectAllDTO() {
		System.out.println("List of TaGedUtilisateurDTO EJB :");
		ArrayList<TaGedUtilisateurDTO> liste = new ArrayList<TaGedUtilisateurDTO>();

		List<TaGedUtilisateur> projects = selectAll();
		for(TaGedUtilisateur project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}
	
	

	public TaGedUtilisateurDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaGedUtilisateurDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaGedUtilisateurDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaGedUtilisateurDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaGedUtilisateurDTO dto, String validationContext) throws EJBException {
		try {
			TaGedUtilisateurMapper mapper = new TaGedUtilisateurMapper();
			TaGedUtilisateur entity = null;
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
	
	public void persistDTO(TaGedUtilisateurDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaGedUtilisateurDTO dto, String validationContext) throws CreateException {
		try {
			TaGedUtilisateurMapper mapper = new TaGedUtilisateurMapper();
			TaGedUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaGedUtilisateurDTO dto) throws RemoveException {
		try {
			TaGedUtilisateurMapper mapper = new TaGedUtilisateurMapper();
			TaGedUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaGedUtilisateur refresh(TaGedUtilisateur persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaGedUtilisateur value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaGedUtilisateur value, String propertyName, String validationContext) {
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
	public void validateDTO(TaGedUtilisateurDTO dto, String validationContext) {
		try {
			TaGedUtilisateurMapper mapper = new TaGedUtilisateurMapper();
			TaGedUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaGedUtilisateurDTO> validator = new BeanValidator<TaGedUtilisateurDTO>(TaGedUtilisateurDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaGedUtilisateurDTO dto, String propertyName, String validationContext) {
		try {
			TaGedUtilisateurMapper mapper = new TaGedUtilisateurMapper();
			TaGedUtilisateur entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaGedUtilisateurDTO> validator = new BeanValidator<TaGedUtilisateurDTO>(TaGedUtilisateurDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaGedUtilisateurDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaGedUtilisateurDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaGedUtilisateur value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaGedUtilisateur value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

	@Override
	public long countNbDocAttenduPourCourtier(String codeTListeRefDoc) {
		return dao.countNbDocAttenduPourCourtier(codeTListeRefDoc);
	}

	@Override
	public long countNbDocUploderPourCourtier(Integer idCourtier) {
		return dao.countNbDocUploderPourCourtier(idCourtier);
		
	}

	@Override
	public long countNbDocValiderPourCourtier(Integer idCourtier) {
		return dao.countNbDocValiderPourCourtier(idCourtier);
	}

}
