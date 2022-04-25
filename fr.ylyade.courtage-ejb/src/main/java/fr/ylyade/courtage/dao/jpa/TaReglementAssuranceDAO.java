package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaReglementAssuranceDAO;
import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.model.TaReglementAssurance;


public class TaReglementAssuranceDAO implements ITaReglementAssuranceDAO {

	private static final Logger logger = Logger.getLogger(TaReglementAssuranceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaReglementAssurance p";
	
	public TaReglementAssuranceDAO(){
//		this(null);
	}

//	public TaTaReglementAssuranceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaReglementAssurance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaReglementAssurance());
//	}


	public void persist(TaReglementAssurance transientInstance) {
		logger.debug("persisting TaReglementAssurance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaReglementAssurance persistentInstance) {
		logger.debug("removing TaReglementAssurance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdReglementAssurance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaReglementAssurance merge(TaReglementAssurance detachedInstance) {
		logger.debug("merging TaReglementAssurance instance");
		try {
			TaReglementAssurance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaReglementAssurance findById(int id) {
		logger.debug("getting TaReglementAssurance instance with id: " + id);
		try {
			TaReglementAssurance instance = entityManager.find(TaReglementAssurance.class, id);
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
	public List<TaReglementAssurance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaReglementAssurance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaReglementAssurance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaReglementAssurance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaReglementAssurance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaReglementAssurance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaReglementAssurance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaReglementAssurance value) throws Exception {
		BeanValidator<TaReglementAssurance> validator = new BeanValidator<TaReglementAssurance>(TaReglementAssurance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaReglementAssurance value, String propertyName) throws Exception {
		BeanValidator<TaReglementAssurance> validator = new BeanValidator<TaReglementAssurance>(TaReglementAssurance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaReglementAssurance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaReglementAssurance findByCode(String code) {
		logger.debug("getting TaReglementAssurance instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaReglementAssurance f where f.refReglement='"+code+"'");
				TaReglementAssurance instance = (TaReglementAssurance)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtier(Integer idCourtier) {
		logger.debug("getting TaReglementAssuranceDTO instance with id courtier: " + idCourtier);
		try {

			if(idCourtier != null){
				Query query = entityManager.createQuery("select new fr.ylyade.courtage.dto.TaReglementAssuranceDTO( "
						+ "f.refReglement, f.defautPaiement, f.codeTReglement, f.dateReglement, f.dateVirementEffectue,"
						+ " f.dateVirementRecu, f.montant,doss.numDossierPolice, doss.lettrePjNumPolice, doss.numeroAvenant, ech)"
						+ " from TaReglementAssurance f"
						+ " join f.taEcheance ech"
						+ " join ech.taDossierRcd doss"
						+ " where doss.taCourtier.idCourtier= :idCourtier");
				query.setParameter("idCourtier", idCourtier);
				List<TaReglementAssuranceDTO> instance = (List<TaReglementAssuranceDTO>)query.getResultList();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			return null;
		}
	}
	
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtierAndByMoisAndYear(Integer idCourtier, String mois, String annee) {
		logger.debug("getting TaReglementAssuranceDTO instance with id courtier: " + idCourtier);
		try {

			if(idCourtier != null && mois != null && annee !=null){
				Integer nbMois = Integer.valueOf(mois);
				Integer nbAnnee = Integer.valueOf(annee);
				Query query = entityManager.createQuery("select new fr.ylyade.courtage.dto.TaReglementAssuranceDTO( "
						+ "f.refReglement, f.defautPaiement, f.codeTReglement, f.dateReglement, f.dateVirementEffectue,"
						+ " f.dateVirementRecu, f.montant,doss.numDossierPolice, doss.lettrePjNumPolice, doss.numeroAvenant, ech)"
						+ " from TaReglementAssurance f"
						+ " join f.taEcheance ech"
						+ " join ech.taDossierRcd doss"
						+ " where doss.taCourtier.idCourtier= :idCourtier"
						+ " and month(f.dateReglement) = :mois"
						+ " and year(f.dateReglement) = :annee");
				query.setParameter("idCourtier", idCourtier);
				query.setParameter("mois", nbMois);
				query.setParameter("annee", nbAnnee);
				List<TaReglementAssuranceDTO> instance = (List<TaReglementAssuranceDTO>)query.getResultList();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			return null;
		}
	}
	
}

