package fr.ylyade.courtage.dao.jpa;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaCourtierDAO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaCourtier;
import sun.misc.BASE64Encoder;


public class TaCourtierDAO implements ITaCourtierDAO {

	private static final Logger logger = Logger.getLogger(TaCourtierDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaCourtier p";
	
	public TaCourtierDAO(){
//		this(null);
	}

	public List<TaCourtierDTO> findAllLight() {
		try {
			Query query = entityManager.createNamedQuery(TaCourtier.QN.FIND_ALL_LIGHT_PLUS);
			@SuppressWarnings("unchecked")
			List<TaCourtierDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaCourtierDTO> findAllInactif() {
		try {
			Query query = entityManager.createNamedQuery(TaCourtier.QN.FIND_ALL_INACTIF);
			@SuppressWarnings("unchecked")
			List<TaCourtierDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaCourtierDTO> findAllActif() {
		try {
			Query query = entityManager.createNamedQuery(TaCourtier.QN.FIND_ALL_ACTIF);
			@SuppressWarnings("unchecked")
			List<TaCourtierDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaCourtierDTO> findByCodeLight(String code) {
		logger.debug("getting TaCourtierDTO instance");
		try {
			Query query = null;
			if(code!=null && !code.equals("") && !code.equals("*")) {
				query = entityManager.createNamedQuery(TaCourtier.QN.FIND_BY_CODE_LIGHT);
				query.setParameter("codeCourtier", "%"+code+"%");
			} else {
				query = entityManager.createNamedQuery(TaCourtier.QN.FIND_ALL_LIGHT_ACTIF);
			}

			List<TaCourtierDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public void persist(TaCourtier transientInstance) {
		logger.debug("persisting TaCourtier instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaCourtier persistentInstance) {
		logger.debug("removing TaCourtier instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdCourtier()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaCourtier merge(TaCourtier detachedInstance) {
		logger.debug("merging TaCourtier instance");
		try {
			TaCourtier result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCourtier findById(int id) {
		logger.debug("getting TaCourtier instance with id: " + id);
		try {
			TaCourtier instance = entityManager.find(TaCourtier.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaCourtier> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaCourtier> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaCourtier entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaCourtier> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaCourtier> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaCourtier> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaCourtier> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaCourtier value) throws Exception {
		BeanValidator<TaCourtier> validator = new BeanValidator<TaCourtier>(TaCourtier.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaCourtier value, String propertyName) throws Exception {
		BeanValidator<TaCourtier> validator = new BeanValidator<TaCourtier>(TaCourtier.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaCourtier transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaCourtier findByCode(String code) {
		logger.debug("getting TaCourtier instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaCourtier f where f.codeCourtier='"+code+"'");
				TaCourtier instance = (TaCourtier)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public TaCourtier findByIdentifiantAndByPassword(String identifiant, String password) {
//		logger.debug("getting TaCourtier instance with username: " + code);
		try {
			MessageDigest md;
			if(!identifiant.equals("") && !password.equals("")){
				md = MessageDigest.getInstance("SHA-256");
				BASE64Encoder enc = new sun.misc.BASE64Encoder();
				byte[] digest = md.digest(password.getBytes()); // Missing charset
				String passwordDecode  = enc.encode(digest);
				
				System.out.println(passwordDecode);
				Query query = entityManager.createQuery("select f from TaCourtier f where f.taUtilisateur.identifiant='"+identifiant+"' and f.taUtilisateur.password='"+passwordDecode+"'");
				TaCourtier instance = (TaCourtier)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public TaCourtier findByIdUtilisateur(int idUtilisateur) {
		try {
			Query query = entityManager.createQuery("select f from TaCourtier f where f.taUtilisateur.idUtilisateur="+idUtilisateur);
			TaCourtier instance = (TaCourtier)query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return null;
		}
	}
	
}

