package fr.legrain.data;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class YlyadeProperties {
	
	private static final String propertiesFileName = "ylyade.properties";  
	
	private Properties props = new Properties();  
	
	public static final String KEY_JDBC_USER_NAME = "jdbc.username";
	public static final String KEY_JDBC_PASSWORD = "jdbc.password";
	public static final String KEY_JDBC_URL = "jdbc.url";
	public static final String KEY_JDBC_DRIVER = "jdbc.driver";
	
	public static final String KEY_BDG_FILESYSTEM_PATH = "ylyade.filesystem.path";
	
	public static final String KEY_DEFAULT_TENANT = "defaultTenant";
	
	public static final String KEY_PREFIXE_SOUS_DOMAINE = "prefixe_sous_domaine";
	public static final String KEY_NOM_DOMAINE = "nom_domaine";
	
	public static final String KEY_PORT_HTTP = "port_http";
	public static final String KEY_PORT_HTTPS = "port_https";
	
	public static final String KEY_WS_MONCOMPTE_VERIF_MAJ_AUTO = "webservice_moncompte.verif_maj_auto";
	
	public static final String KEY_WS_AUTORISATION_PORT = "webservice_autorisation.wsdl.port";
	public static final String KEY_WS_AUTORISATION_HOST = "webservice_autorisation.wsdl.host";
	public static final String KEY_WS_AUTORISATION_URL = "webservice_autorisation.wsdl.url";
	
	public static final String KEY_WS_MONCOMPTE_PORT = "webservice_moncompte.wsdl.port";
	public static final String KEY_WS_MONCOMPTE_HOST = "webservice_moncompte.wsdl.host";
	public static final String KEY_WS_MONCOMPTE_URL = "webservice_moncompte.wsdl.url";
	
	public static final String KEY_WS_MONCOMPTE_GANDI_PORT = "webservice_moncompte.gandi.wsdl.port";
	public static final String KEY_WS_MONCOMPTE_GANDI_HOST = "webservice_moncompte.gandi.wsdl.host";
	public static final String KEY_WS_MONCOMPTE_GANDI_URL = "webservice_moncompte.gandi.wsdl.url";
	
	public static final String KEY_WS_BDG_PORT = "webservice_bdg.wsdl.port";
	public static final String KEY_WS_BDG_HOST = "webservice_bdg.wsdl.host";
	public static final String KEY_WS_BDG_URL = "webservice_bdg.wsdl.url";
	
	public static final String KEY_SMTP_HOSTNAME = "smtp.hostname";
	public static final String KEY_SMTP_PORT = "smtp.port";
	public static final String KEY_SMTP_SSL = "smtp.ssl";
	public static final String KEY_SMTP_LOGIN = "smtp.login";
	public static final String KEY_SMTP_PASSWORD = "smtp.password";

	public YlyadeProperties() {
		this(System.getProperty("jboss.server.config.dir")+"/"+propertiesFileName);
	}
	
	public YlyadeProperties(String cheminFichierProperties) {
		try {
	
			String path = cheminFichierProperties;
	
			if(new File(path).exists()) { 
				File f = new File(path);
				props.load(new FileInputStream(f));  
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key) {
		return getProperty(key, null);
	}
	
	public String getProperty(String key, String def) {
		if(props!=null) {
			return props.getProperty(key,def);
		} else {
			return def;
		}
	}
	
	public String urlDemo() {
		return url("demo",false);
	}
	
	public String urlDemoHttps() {
		return url("demo",true);
	}
	
	public String url(String nomDossier) {
		return url(nomDossier,false);
	}
	
	
//	public String url() {
//		return url();
//	}
//	
	
	
	
	
	
	public String url(String nomDossier, boolean https) {
		String url = "";
		if(https) {
			url += "https://";
		} else {
			url += "http://";
		}
		
		url += getProperty(YlyadeProperties.KEY_PREFIXE_SOUS_DOMAINE,"")+getProperty(YlyadeProperties.KEY_NOM_DOMAINE);
		
		if(https) {
			url += ":"+getProperty(YlyadeProperties.KEY_PORT_HTTPS,"")+"";
		} else {
			url += ":"+getProperty(YlyadeProperties.KEY_PORT_HTTP,"")+"";
		}
		
		return url;
	}
	
	public String osTempDir() {
		return osTempDir(false);
	}
	
	public String osTempDir(boolean fileProtocol) {
		String osTempDir = System.getProperty("java.io.tmpdir")+"/ylyade";
		File f = new File(osTempDir);
		if(!f.exists()) {
			f.mkdirs();
		}
		if(fileProtocol) {
			osTempDir = osTempDir.replaceAll("\\", "/");
			osTempDir = osTempDir.replaceAll("//", "/");
			osTempDir = "file:///"+osTempDir;
		}
		return osTempDir;
	}
	
	public String osTempDirDossier(String codeDossier) {
		return osTempDirDossier(codeDossier,false);
	}
	
	public String osTempDirDossier(String codeDossier,boolean fileProtocol) {
		String osTempDir = osTempDir(fileProtocol);
		//String osTempDirDossier = osTempDir+"/"+codeDossier;
		String osTempDirDossier = osTempDir;
		File f = new File(osTempDirDossier);
		if(!f.exists()) {
			f.mkdirs();
		}
		return osTempDirDossier;
	}
	
	public String tmpFileName(String fileName) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_ms").format(new Date());
		String tmpFileName = timeStamp+"_"+fileName;
		return tmpFileName;
	}
}
