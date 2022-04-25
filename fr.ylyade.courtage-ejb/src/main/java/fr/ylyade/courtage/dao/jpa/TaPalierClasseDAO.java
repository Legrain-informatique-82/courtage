package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaPalierClasseDAO;
import fr.ylyade.courtage.model.TaPalierClasse;


public class TaPalierClasseDAO implements ITaPalierClasseDAO {

	private static final Logger logger = Logger.getLogger(TaPalierClasseDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaPalierClasse p";
	
	public TaPalierClasseDAO(){
//		this(null);
	}

//	public TaTaPalierClasseDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaPalierClasse.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaPalierClasse());
//	}


	public void persist(TaPalierClasse transientInstance) {
		logger.debug("persisting TaPalierClasse instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaPalierClasse persistentInstance) {
		logger.debug("removing TaPalierClasse instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdPalierClasse()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaPalierClasse merge(TaPalierClasse detachedInstance) {
		logger.debug("merging TaPalierClasse instance");
		try {
			TaPalierClasse result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaPalierClasse findById(int id) {
		logger.debug("getting TaPalierClasse instance with id: " + id);
		try {
			TaPalierClasse instance = entityManager.find(TaPalierClasse.class, id);
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
	public List<TaPalierClasse> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaPalierClasse> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaPalierClasse entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaPalierClasse> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaPalierClasse> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaPalierClasse> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaPalierClasse> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaPalierClasse value) throws Exception {
		BeanValidator<TaPalierClasse> validator = new BeanValidator<TaPalierClasse>(TaPalierClasse.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaPalierClasse value, String propertyName) throws Exception {
		BeanValidator<TaPalierClasse> validator = new BeanValidator<TaPalierClasse>(TaPalierClasse.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaPalierClasse transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaPalierClasse findByCode(String code) {
		logger.debug("getting TaPalierClasse instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaPalierClasse f where f.codePalierClasse='"+code+"'");
				TaPalierClasse instance = (TaPalierClasse)query.getSingleResult();
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

