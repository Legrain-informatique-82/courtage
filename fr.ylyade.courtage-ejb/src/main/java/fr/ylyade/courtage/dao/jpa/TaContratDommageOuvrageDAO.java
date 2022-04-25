package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaContratDommageOuvrageDAO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaContratDommageOuvrage;


public class TaContratDommageOuvrageDAO implements ITaContratDommageOuvrageDAO {

	private static final Logger logger = Logger.getLogger(TaContratDommageOuvrageDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaContratDommageOuvrage p";
	
	public TaContratDommageOuvrageDAO(){
//		this(null);
	}

//	public TaTaContratDommageOuvrageDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaContratDommageOuvrage.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaContratDommageOuvrage());
//	}


	public void persist(TaContratDommageOuvrage transientInstance) {
		logger.debug("persisting TaContratDommageOuvrage instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaContratDommageOuvrage persistentInstance) {
		logger.debug("removing TaContratDommageOuvrage instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdContratDommageOuvrage()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaContratDommageOuvrage merge(TaContratDommageOuvrage detachedInstance) {
		logger.debug("merging TaContratDommageOuvrage instance");
		try {
			TaContratDommageOuvrage result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaContratDommageOuvrage findById(int id) {
		logger.debug("getting TaContratDommageOuvrage instance with id: " + id);
		try {
			TaContratDommageOuvrage instance = entityManager.find(TaContratDommageOuvrage.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaContratDommageOuvrage.QN.COUNT_ACTIVE_BY_ID_COURTIER);/*Compte le nombre de ContratDommageOuvrage par courtier*/
			
			query.setParameter("idCourtier", idCourtier);
			
			Integer instance = (Integer) query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return 0;
		}
	}
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaContratDommageOuvrage> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaContratDommageOuvrage> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaContratDommageOuvrage entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaContratDommageOuvrage> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaContratDommageOuvrage> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaContratDommageOuvrage> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaContratDommageOuvrage> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaContratDommageOuvrage value) throws Exception {
		BeanValidator<TaContratDommageOuvrage> validator = new BeanValidator<TaContratDommageOuvrage>(TaContratDommageOuvrage.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaContratDommageOuvrage value, String propertyName) throws Exception {
		BeanValidator<TaContratDommageOuvrage> validator = new BeanValidator<TaContratDommageOuvrage>(TaContratDommageOuvrage.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaContratDommageOuvrage transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaContratDommageOuvrage findByCode(String code) {
		logger.debug("getting TaContratDommageOuvrage instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaContratDommageOuvrage f where f.codeContratDommageOuvrage='"+code+"'");
				TaContratDommageOuvrage instance = (TaContratDommageOuvrage)query.getSingleResult();
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

