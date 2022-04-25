package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaProjetDommageOuvrageDAO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaProjetDommageOuvrage;


public class TaProjetDommageOuvrageDAO implements ITaProjetDommageOuvrageDAO {

	private static final Logger logger = Logger.getLogger(TaProjetDommageOuvrageDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaProjetDommageOuvrage p";
	
	public TaProjetDommageOuvrageDAO(){
//		this(null);
	}

//	public TaTaProjetDommageOuvrageDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaProjetDommageOuvrage.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaProjetDommageOuvrage());
//	}


	public void persist(TaProjetDommageOuvrage transientInstance) {
		logger.debug("persisting TaProjetDommageOuvrage instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaProjetDommageOuvrage persistentInstance) {
		logger.debug("removing TaProjetDommageOuvrage instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdProjetDommageOuvrage()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaProjetDommageOuvrage merge(TaProjetDommageOuvrage detachedInstance) {
		logger.debug("merging TaProjetDommageOuvrage instance");
		try {
			TaProjetDommageOuvrage result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaProjetDommageOuvrage.QN.COUNT_ACTIVE_BY_ID_COURTIER);/*Compte le nombre de ProjetDommageOuvrage actifs par courtier*/
			
			query.setParameter("idCourtier", idCourtier);
			
			Integer instance = (Integer) query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return 0;
		}
	}

	public TaProjetDommageOuvrage findById(int id) {
		logger.debug("getting TaProjetDommageOuvrage instance with id: " + id);
		try {
			TaProjetDommageOuvrage instance = entityManager.find(TaProjetDommageOuvrage.class, id);
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
	public List<TaProjetDommageOuvrage> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaProjetDommageOuvrage> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaProjetDommageOuvrage entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaProjetDommageOuvrage> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaProjetDommageOuvrage> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaProjetDommageOuvrage> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaProjetDommageOuvrage> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaProjetDommageOuvrage value) throws Exception {
		BeanValidator<TaProjetDommageOuvrage> validator = new BeanValidator<TaProjetDommageOuvrage>(TaProjetDommageOuvrage.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaProjetDommageOuvrage value, String propertyName) throws Exception {
		BeanValidator<TaProjetDommageOuvrage> validator = new BeanValidator<TaProjetDommageOuvrage>(TaProjetDommageOuvrage.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaProjetDommageOuvrage transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaProjetDommageOuvrage findByCode(String code) {
		logger.debug("getting TaProjetDommageOuvrage instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaProjetDommageOuvrage f where f.numProjetDommageOuvrage='"+code+"'");
				TaProjetDommageOuvrage instance = (TaProjetDommageOuvrage)query.getSingleResult();
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

