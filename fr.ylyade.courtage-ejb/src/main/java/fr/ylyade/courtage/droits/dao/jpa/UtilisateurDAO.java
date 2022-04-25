package fr.ylyade.courtage.droits.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.droits.dao.IUtilisateurDAO;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.model.TaTUtilisateur;


public class UtilisateurDAO implements IUtilisateurDAO {

	private static final Logger logger = Logger.getLogger(UtilisateurDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaUtilisateur p  where p.systeme is null or p.systeme=false order by p.identifiant";
	
	public UtilisateurDAO(){
//		this(null);
	}

//	public TaTaUtilisateurDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaUtilisateur.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaUtilisateur());
//	}


	public void persist(TaUtilisateur transientInstance) {
		logger.debug("persisting TaUtilisateur instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaUtilisateur persistentInstance) {
		logger.debug("removing TaUtilisateur instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdUtilisateur()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaUtilisateur merge(TaUtilisateur detachedInstance) {
		logger.debug("merging TaUtilisateur instance");
		try {
			TaUtilisateur result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaUtilisateur findById(int id) {
		logger.debug("getting TaUtilisateur instance with id: " + id);
		try {
			TaUtilisateur instance = entityManager.find(TaUtilisateur.class, id);
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
	public List<TaUtilisateur> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaUtilisateur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaUtilisateur entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaUtilisateur> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaUtilisateur> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaUtilisateur> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaUtilisateur> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaUtilisateur value) throws Exception {
		BeanValidator<TaUtilisateur> validator = new BeanValidator<TaUtilisateur>(TaUtilisateur.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaUtilisateur value, String propertyName) throws Exception {
		BeanValidator<TaUtilisateur> validator = new BeanValidator<TaUtilisateur>(TaUtilisateur.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaUtilisateur transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaUtilisateur findByCode(String code) {
		logger.debug("getting TaUtilisateur instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaUtilisateur f where f.identifiant='"+code+"'");
				TaUtilisateur instance = (TaUtilisateur)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	
	
	public TaUtilisateur ctrlSaisieEmail(String email) {
		logger.debug("getting TaUtilisateur instance with username: " + email);
		try {
			if(!email.equals("") && email !=null){
				Query query = entityManager.createQuery("select f from TaUtilisateur f where f.identifiant='"+email+"'");
				TaUtilisateur instance = (TaUtilisateur)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			
			return null;
		}
	}
	
//	public String findTypeUtilisateur(String identifiant) {
//		try {
//			if(!identifiant.equals("") && identifiant !=null){
//				Query query = entityManager.createQuery("select c from TaCourtier c where c.taUtilisateur.identifiant='"+identifiant+"'");
//				TaUtilisateur instance = (TaUtilisateur)query.getSingleResult();
//				logger.debug("get successful");
//				return TaTUtilisateur.TYPE_UTILISATEUR_COURTIER;
//			}
//			return null;
//		} catch (RuntimeException re) {
//			
//			return null;
//		}
//	}
	
}

