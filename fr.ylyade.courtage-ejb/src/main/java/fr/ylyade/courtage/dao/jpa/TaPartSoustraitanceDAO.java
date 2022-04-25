//package fr.ylyade.courtage.dao.jpa;
//
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.apache.log4j.Logger;
//
//import fr.legrain.validators.BeanValidator;
//import fr.ylyade.courtage.dao.ITaPartSoustraitanceDAO;
//import fr.ylyade.courtage.model.TaPartSoustraitance;
//
//
//public class TaPartSoustraitanceDAO implements ITaPartSoustraitanceDAO {
//
//	private static final Logger logger = Logger.getLogger(TaPartSoustraitanceDAO.class);
//	
//	@PersistenceContext(unitName = "ylyade")
//	private EntityManager entityManager;
//
//	private String defaultJPQLQuery = "select p from TaPartSoustraitance p";
//	
//	public TaPartSoustraitanceDAO(){
////		this(null);
//	}
//
////	public TaTaPartSoustraitanceDAO(EntityManager em){
////		if(em!=null) {
////			setEm(em);
////		}
////		initChampId(TaPartSoustraitance.class);
////		setJPQLQuery(defaultJPQLQuery);
////		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
////		initNomTable(new TaPartSoustraitance());
////	}
//
//
//	public void persist(TaPartSoustraitance transientInstance) {
//		logger.debug("persisting TaPartSoustraitance instance");
//		try {
//			entityManager.persist(transientInstance);
//			logger.debug("persist successful");
//		} catch (RuntimeException re) {
//			logger.error("persist failed", re);
//			throw re;
//		}
//	}
//
//	public void remove(TaPartSoustraitance persistentInstance) {
//		logger.debug("removing TaPartSoustraitance instance");
//		//boolean estRefPrix=false;
//		try {
//			entityManager.remove(findById(persistentInstance.getIdPartSousTraitance()));
//
//			logger.debug("remove successful");
//		} catch (RuntimeException re) {
//			logger.error("remove failed", re);
//			throw re;
//		}
//	}
//
//	public TaPartSoustraitance merge(TaPartSoustraitance detachedInstance) {
//		logger.debug("merging TaPartSoustraitance instance");
//		try {
//			TaPartSoustraitance result = entityManager.merge(detachedInstance);
//			logger.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			logger.error("merge failed", re);
//			throw re;
//		}
//	}
//
//	public TaPartSoustraitance findById(int id) {
//		logger.debug("getting TaPartSoustraitance instance with id: " + id);
//		try {
//			TaPartSoustraitance instance = entityManager.find(TaPartSoustraitance.class, id);
//			logger.debug("get successful");
//			return instance;
//		} catch (RuntimeException re) {
//			logger.error("get failed", re);
//			throw re;
//		}
//	}
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
//	 */
//	public List<TaPartSoustraitance> selectAll() {
//		logger.debug("selectAll TaArticle");
//		try {
//			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
//			List<TaPartSoustraitance> l = ejbQuery.getResultList();
//			logger.debug("selectAll successful");
//			return l;
//		} catch (RuntimeException re) {
//			logger.error("selectAll failed", re);
//			throw re;
//		}
//	}
//
//	public void ctrlSaisieSpecifique(TaPartSoustraitance entity,String field) throws Exception {	
//		try {
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	
//	@Override
//	public List<TaPartSoustraitance> findWithNamedQuery(String queryName) {
//		try {
//			Query ejbQuery = entityManager.createNamedQuery(queryName);
//			List<TaPartSoustraitance> l = ejbQuery.getResultList();
//			System.out.println("selectAll successful");
//			return l;
//		} catch (RuntimeException re) {
//			System.out.println("selectAll failed");
//			re.printStackTrace();
//			throw re;
//		}
//	}
//
//	@Override
//	public List<TaPartSoustraitance> findWithJPQLQuery(String JPQLquery) {
//		try {
//			Query ejbQuery = entityManager.createQuery(JPQLquery);
//			List<TaPartSoustraitance> l = ejbQuery.getResultList();
//			System.out.println("selectAll successful");
//			return l;
//		} catch (RuntimeException re) {
//			System.out.println("selectAll failed");
//			re.printStackTrace();
//			throw re;
//		}
//	}
//
//	@Override
//	public boolean validate(TaPartSoustraitance value) throws Exception {
//		BeanValidator<TaPartSoustraitance> validator = new BeanValidator<TaPartSoustraitance>(TaPartSoustraitance.class);
//		return validator.validate(value);
//	}
//
//	@Override
//	public boolean validateField(TaPartSoustraitance value, String propertyName) throws Exception {
//		BeanValidator<TaPartSoustraitance> validator = new BeanValidator<TaPartSoustraitance>(TaPartSoustraitance.class);
//		return validator.validateField(value,propertyName);
//	}
//
//	@Override
//	public void detach(TaPartSoustraitance transientInstance) {
//		entityManager.detach(transientInstance);
//	}
//	
//	public TaPartSoustraitance findByCode(String code) {
//		logger.debug("getting TaPartSoustraitance instance with username: " + code);
//		try {
//			if(!code.equals("")){
//				Query query = entityManager.createQuery("select f from TaPartSoustraitance f where f.codePartSousTraitance='"+code+"'");
//				TaPartSoustraitance instance = (TaPartSoustraitance)query.getSingleResult();
//				logger.debug("get successful");
//				return instance;
//			}
//			return null;
//		} catch (RuntimeException re) {
//			//logger.error("get failed", re);
//			return null;
//		}
//	}
//	
//}
//
