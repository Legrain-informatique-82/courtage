package fr.legrain.data;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public class JPABdLgrServer {
	
	/*
	 * 1 seul EntityManager :
	 * ENTITY_MANAGER_UNIQUE = true;
	 * ThreadLocal
	 * 1ere partie de la classe active (avant les ///////////////////////)
	 * 
	 * Plusieurs EntityManager
	 * ENTITY_MANAGER_UNIQUE = false;
	 * private EntityManager em = null;
	 * 2ème partie de la classe active
	 */

	//private static final Log logger = LogFactory.getLog(JPABdLgr.class);
	static Logger logger = Logger.getLogger(JPABdLgrServer.class);
	
//	private static boolean ENTITY_MANAGER_UNIQUE = false;
//	//private static boolean ENTITY_MANAGER_UNIQUE = true;

//	@PersistenceUnit(unitName = "bdg")
	protected static EntityManagerFactory emf;
	
	//private EntityManager em = null;//getEntityManager();
	@PersistenceContext(unitName = "ylyade")
	static private EntityManager em;
	
	//private static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();
	

	static protected Connection cnx;

	static protected ResourceBundle fListeTitre;

	static protected String fichierListeTitre;
	
	public JPABdLgrServer() {
		
	}
	
	@PostConstruct
	private void init() {
//		if(em==null)
//			em = emf.createEntityManager();
	}
	
//	public static boolean isENTITY_MANAGER_UNIQUE() {
//		return ENTITY_MANAGER_UNIQUE;
//	}
//
//	public static void setENTITY_MANAGER_UNIQUE(boolean eNTITYMANAGERUNIQUE) {
//		ENTITY_MANAGER_UNIQUE = eNTITYMANAGERUNIQUE;
//	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 1 seul EntityManager
	
//	/**
//     * Corresponds to a Hibernate Session.
//     * @return
//     */
//    public static EntityManager getEntityManager() {
//        EntityManager em = entitymanager.get();
//
//        // Create a new EntityManager
//        if (em == null) {
//            em = emf.createEntityManager();
//            //em.setFlushMode(FlushModeType.COMMIT);
//            entitymanager.set(em);
//        }
//		//logger.error("hhhh-------------------------------------------");
//        return em;
//    }
//
//    /**
//     * Close our "session".
//     */
//    public static void closeEntityManager() {
//    	EntityManager em = entitymanager.get();
//    	if (em != null) em.close();
//    	entitymanager.set(null);
//    }
//
//	public DatabaseMetaData getDbMetaData() {
//		try {
//			return cnx.getMetaData();
//		} catch (SQLException e) {
//			getLogger().error("",e);
//		}
//		return null;
//	}
//
//	public void setCnx(Connection cnx) {
//		this.cnx = cnx;
//	}
//
//	public static void fermetureConnection(){
//		closeEntityManager();
//		ConnectionJDBC.closeConnection();
//		if (emf != null) {
//			emf.close();
//			emf=null;
//		}
//	}
//	
//    
//	/**
//	 * Retourne le titre d'un champs tel que celui-ci doit être affiché dans le programme
//	 * @param value String
//	 * @return String
//	 */
//	static public String getTitreChamp(String value){
//		String result = value;
//		if(fListeTitre==null) {
//			if (new File(getFichierListeTitre()).exists()) {
//				try {
//					FileInputStream file = new FileInputStream(getFichierListeTitre());
//					fListeTitre = new PropertyResourceBundle(file);
//					file.close();
//				} catch (FileNotFoundException e) {
//					getLogger().error("", e);
//				} catch (IOException e) {
//					getLogger().error("", e);
//				}
//			}
//			else {
//				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ATTENTION","le fichier "+value+" n'existe pas");
//			}
//		}
//		if(fListeTitre!=null) {
//			try {
//				result = fListeTitre.getString(value);
//			} catch (Exception e) {
//				logger.error("",e);
//			}
//		}
//		return result;
//	}
//
//	static public String getFichierListeTitre() {
//		return fichierListeTitre;
//	}
//	static public void setFichierListeTitre(String fichierListeTitre) {
//		//BdLgr.fichierListeTitre = fichierListeTitre;
//		JPABdLgr.fichierListeTitre = fichierListeTitre;
//	}
//
//	public static Logger getLogger() {
//		return logger;
//	}
//
//	public void setEm(EntityManager em) {
//		//this.em = em;
//	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Plusieur EntityManager

	/**
     * Corresponds to a Hibernate Session.
     * @return
     */
    public /*static*/ EntityManager getEntityManager() {
       // EntityManager em = entitymanager.get();

        // Create a new EntityManager
//EJB
//        if (em == null) {
//            em = emf.createEntityManager();
//            //em.setFlushMode(FlushModeType.COMMIT);
//           // entitymanager.set(em);
//        }
		//logger.error("hhhh-------------------------------------------");
        return em;
    }

    /**
     * Close our "session".
     */
    public /*static*/ void closeEntityManager() {
    	//EntityManager em = entitymanager.get();
    	if (em != null) em.close();
    	//entitymanager.set(null);
    }

	public DatabaseMetaData getDbMetaData() {
		try {
//			Connection connection = em.unwrap(Connection.class);  
//			DatabaseMetaData metaData = connection.getMetaData();
//			return metaData;

			return cnx.getMetaData();
		} catch (SQLException e) {
			getLogger().error("",e);
		}
		return null;
	}
	
	private Connection getJDBCConnectionFromEntityManagerAndHibernate(){
//		Session hibernateSession = em.unwrap(Session.class); // unwraps the Connection class.
//		SessionFactoryImplementor sfi = (SessionFactoryImplementor) hibernateSession.getSessionFactory();
//		ConnectionProvider cp = sfi.getConnectionProvider();
//		try {
//			cnx = cp.getConnection();
//			//return cp.getConnection();
////			if(cnx==null)
////				return dataSource.getConnection();
////			else
//				return cnx;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	public void setCnx(Connection cnx) {
		JPABdLgrServer.cnx = cnx;
	}

	public /*static*/ void fermetureConnection(){
		closeEntityManager();
//		ConnectionJDBC.closeConnection();
//		GestionModif.deConnection();
		if (cnx != null)
			try {
				cnx.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		if (emf != null) {
//			emf.close();
//			emf=null;
//		}
	}
	
    
	/**
	 * Retourne le titre d'un champs tel que celui-ci doit être affiché dans le programme
	 * @param value String
	 * @return String
	 * @throws Exception 
	 */
	static public String getTitreChamp(String value) throws Exception{
		String result = value;
		if(fListeTitre==null) {
			if (new File(getFichierListeTitre()).exists()) {
				try {
					FileInputStream file = new FileInputStream(getFichierListeTitre());
					fListeTitre = new PropertyResourceBundle(file);
					file.close();
				} catch (FileNotFoundException e) {
					getLogger().error("", e);
				} catch (IOException e) {
					getLogger().error("", e);
				}
			}
			else {
				//MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ATTENTION","le fichier "+value+" n'existe pas");
				throw new Exception("le fichier "+value+" n'existe pas");
			}
		}
		if(fListeTitre!=null) {
			try {
				result = fListeTitre.getString(value);
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return result;
	}

	static public String getFichierListeTitre() {
		return fichierListeTitre;
	}
	static public void setFichierListeTitre(String fichierListeTitre) {
		//BdLgr.fichierListeTitre = fichierListeTitre;
		JPABdLgrServer.fichierListeTitre = fichierListeTitre;
	}

	public static Logger getLogger() {
		return logger;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public static Connection getCnx() {
		return cnx;
	}

	


}
