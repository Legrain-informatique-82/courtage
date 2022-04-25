package fr.ylyade.courtage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaFraisImpayeDAO;
import fr.ylyade.courtage.dto.TaFraisImpayeDTO;
import fr.ylyade.courtage.model.TaFraisImpaye;

public class TaFraisImpayeDAO implements ITaFraisImpayeDAO {
	
	
private static final Logger logger = Logger.getLogger(TaFraisImpayeDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select f from TaFraisImpaye f";
	
	public TaFraisImpayeDAO(){

	}




	public void persist(TaFraisImpaye transientInstance) {
		logger.debug("persisting TaFraisImpaye instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaFraisImpaye persistentInstance) {
		logger.debug("removing TaFraisImpaye instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdFraisImpaye()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaFraisImpaye merge(TaFraisImpaye detachedInstance) {
		logger.debug("merging TaFraisImpaye instance");
		try {
			TaFraisImpaye result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaFraisImpaye findById(int id) {
		logger.debug("getting TaFraisImpaye instance with id: " + id);
		try {
			TaFraisImpaye instance = entityManager.find(TaFraisImpaye.class, id);
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
	public List<TaFraisImpaye> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaFraisImpaye> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaFraisImpaye entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaFraisImpaye> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaFraisImpaye> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaFraisImpaye> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaFraisImpaye> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaFraisImpaye value) throws Exception {
		BeanValidator<TaFraisImpaye> validator = new BeanValidator<TaFraisImpaye>(TaFraisImpaye.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaFraisImpaye value, String propertyName) throws Exception {
		BeanValidator<TaFraisImpaye> validator = new BeanValidator<TaFraisImpaye>(TaFraisImpaye.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaFraisImpaye transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaFraisImpaye findByCode(String code) {
		logger.debug("getting TaFraisImpaye instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaFraisImpaye f where f.identifiant='"+code+"'");
				TaFraisImpaye instance = (TaFraisImpaye)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}




	@Override
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDDTO() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDIdDTO(Integer idRCD) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public TaFraisImpayeDTO findFraisImpayeReglementRCDDTO(Integer idFraisImpayeRcd) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
