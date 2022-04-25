package fr.ylyade.courtage.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.stripe.Stripe;

/**
 * @author nicolas
 */
@Stateless
public class LgrEnvironnementServeur {
	
	
	private boolean modeTestPaiementDossier = false;
	private boolean modeTestPaiementProgramme = false;
	
	private String ip = null;
	
	private static final String IP_LOCALE_DEVELOPPEMENT = "127.0.0.1";
	private static final String IP_DEV_INTERNET = "xx.xx.xx.xx"; //serveur de test DEV-internet ylyadev
	private static final String IP_TEST_PPROD_INTERNET = "xx.xx.xx.xx"; //serveur de test PPROD-internet ylyadepprod
	private static final String IP_TEST_PROD_INTERNET = "xx.xx.xx.xx"; //serveur de test PROD-internet ylyadetestprod
	private static final String IP_PRODUCTION_INTERNET = "xx.xx.xx.xx"; //serveur de PRODUCTION ylyadeprod
	
	@PostConstruct
	private void init() {
		try {
			Properties props = new Properties(); 
			String propertiesFileName = "ylyade.properties";  
			String path = System.getProperty("jboss.server.config.dir")+"/"+propertiesFileName;  
	
			if(new File(path).exists()) { 
				File f = new File(path);
				props.load(new FileInputStream(f));  
				ip = props.getProperty("ip");
			}
			
			if(ip!=null && !ip.equals("") 
					&& !ip.equals(IP_LOCALE_DEVELOPPEMENT)
					&& !ip.equals(IP_DEV_INTERNET) 
					&& !ip.equals(IP_TEST_PPROD_INTERNET) 
					&& !ip.equals(IP_TEST_PROD_INTERNET)
					) { //on est à prioris bien sur internet sur le serveur de production et pas sur un poste de développement ou un serveur de test
	
			} else /*if(ip.equals(IP_PRODUCTION_INTERNET))*/{	
				modeTestPaiementDossier = true;
				modeTestPaiementProgramme = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public String emailDefautServeur() {
		String fromEmail = null;
		if(ip==null || ip.equals("") 
				||  ip.equals(IP_LOCALE_DEVELOPPEMENT)) {
			fromEmail = "legrain@xxxx.biz";
		} else if(ip.equals(IP_DEV_INTERNET)) {
			fromEmail = "noreply@xxxx.work";
		} else if(ip.equals(IP_TEST_PPROD_INTERNET)) {
			fromEmail = "noreply@xxxx.work";
		} else if(ip.equals(IP_TEST_PROD_INTERNET)) {
			fromEmail = "noreply@xxxx.work";
		} else if(ip.equals(IP_PRODUCTION_INTERNET)) {
			fromEmail = "noreply@xxxx.cloud";
		}
		return fromEmail;
	}
	


	public boolean isModeTestPaiementDossier() {
		return modeTestPaiementDossier;
	}

	public boolean isModeTestPaiementProgramme() {
		return modeTestPaiementProgramme;
	}
	
}
