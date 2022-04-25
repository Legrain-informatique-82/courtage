package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaContactEntrepriseDAO;
import fr.ylyade.courtage.model.TaContactEntreprise;


public class TaContactEntrepriseDAO implements ITaContactEntrepriseDAO {

	private static final Logger logger = Logger.getLogger(TaContactEntrepriseDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaContactEntreprise p";
	
	public TaContactEntrepriseDAO(){
//		this(null);
	}

//	public TaTaContactEntrepriseDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaContactEntreprise.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaContactEntreprise());
//	}


	public void persist(TaContactEntreprise transientInstance) {
		logger.debug("persisting TaContactEntreprise instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaContactEntreprise persistentInstance) {
		logger.debug("removing TaContactEntreprise instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdContactEntreprise()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaContactEntreprise merge(TaContactEntreprise detachedInstance) {
		logger.debug("merging TaContactEntreprise instance");
		try {
			TaContactEntreprise result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaContactEntreprise findById(int id) {
		logger.debug("getting TaContactEntreprise instance with id: " + id);
		try {
			TaContactEntreprise instance = entityManager.find(TaContactEntreprise.class, id);
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
	public List<TaContactEntreprise> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaContactEntreprise> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaContactEntreprise entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaContactEntreprise> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaContactEntreprise> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaContactEntreprise> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaContactEntreprise> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaContactEntreprise value) throws Exception {
		BeanValidator<TaContactEntreprise> validator = new BeanValidator<TaContactEntreprise>(TaContactEntreprise.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaContactEntreprise value, String propertyName) throws Exception {
		BeanValidator<TaContactEntreprise> validator = new BeanValidator<TaContactEntreprise>(TaContactEntreprise.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaContactEntreprise transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaContactEntreprise findByCode(String code) {
		logger.debug("getting TaContactEntreprise instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaContactEntreprise f where f.codeContact='"+code+"'");
				TaContactEntreprise instance = (TaContactEntreprise)query.getSingleResult();
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

