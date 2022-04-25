package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTUtilisateurDAO;
import fr.ylyade.courtage.model.TaTUtilisateur;


public class TaTUtilisateurDAO implements ITaTUtilisateurDAO {

	private static final Logger logger = Logger.getLogger(TaTUtilisateurDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTUtilisateur p";
	
	public TaTUtilisateurDAO(){
//		this(null);
	}

//	public TaTaTUtilisateurDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTUtilisateur.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTUtilisateur());
//	}


	public void persist(TaTUtilisateur transientInstance) {
		logger.debug("persisting TaTUtilisateur instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTUtilisateur persistentInstance) {
		logger.debug("removing TaTUtilisateur instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTUtilisateur()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTUtilisateur merge(TaTUtilisateur detachedInstance) {
		logger.debug("merging TaTUtilisateur instance");
		try {
			TaTUtilisateur result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTUtilisateur findById(int id) {
		logger.debug("getting TaTUtilisateur instance with id: " + id);
		try {
			TaTUtilisateur instance = entityManager.find(TaTUtilisateur.class, id);
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
	public List<TaTUtilisateur> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTUtilisateur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTUtilisateur entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTUtilisateur> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTUtilisateur> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTUtilisateur> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTUtilisateur> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTUtilisateur value) throws Exception {
		BeanValidator<TaTUtilisateur> validator = new BeanValidator<TaTUtilisateur>(TaTUtilisateur.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTUtilisateur value, String propertyName) throws Exception {
		BeanValidator<TaTUtilisateur> validator = new BeanValidator<TaTUtilisateur>(TaTUtilisateur.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTUtilisateur transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTUtilisateur findByCode(String code) {
		logger.debug("getting TaTUtilisateur instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTUtilisateur f where f.codeTUtilisateur='"+code+"'");
				TaTUtilisateur instance = (TaTUtilisateur)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
}

