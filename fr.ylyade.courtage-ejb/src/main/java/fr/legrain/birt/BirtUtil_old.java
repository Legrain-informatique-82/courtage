//package fr.legrain.birt;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.net.URL;
//import java.net.URLConnection;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//import org.apache.log4j.Logger;
////import org.eclipse.birt.data.oda.util.manifest.DtpManifestExplorer;
//import org.eclipse.birt.report.engine.api.EngineConfig;
//import org.eclipse.birt.report.engine.api.EngineConstants;
//import org.eclipse.birt.report.engine.api.EngineException;
//import org.eclipse.birt.report.engine.api.HTMLRenderContext;
//import org.eclipse.birt.report.engine.api.HTMLRenderOption;
//import org.eclipse.birt.report.engine.api.IReportRunnable;
//import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
//import org.eclipse.birt.report.engine.api.ReportEngine;
//
//
//
//
//public class BirtUtil_old {
//	
//	static Logger logger = Logger.getLogger(BirtUtil_old.class.getName());
//	
//	static private ReportEngine engine = null;
//	static public final int PDF_FORMAT = 1;
//	static public final int HTML_FORMAT = 2;
//	static private HashMap appContextEdition = null;
//	
//	static public void startReportEngine() {
//		if(engine==null) {
//			EngineConfig config = new EngineConfig();
//			//config.setEngineHome( "C:/birt-runtime-2_0_1/birt-runtime-2_0_1/Report Engine" );
//			
////			System.setProperty( "RUN_UNDER_ECLIPSE", "true" );
//			//config.setConfigurationVariable("RUN_UNDER_ECLIPSE", "true");
////			config.setProperty("RUN_UNDER_ECLIPSE", "true");
//			//DtpManifestExplorer.getInstance( ).getExtensionManifests( );
//			
////			config.setResourceLocator(new ResourceLocator());
//			
//			//Create the report engine
//			engine = new ReportEngine(config);
//			
//			
//			
//		}
//		if(appContextEdition!=null) {
//			engine.getConfig().getAppContext().putAll(appContextEdition);
//		}
//	}
//	
//	static public void destroyReportEngine() {
//		engine.destroy();
//		engine = null;
//	}
//	
//	static public OutputStream renderReportToStream(String reportFile, Map reportParameters, int format) {
//		
//		HTMLRenderOption options;
//		OutputStream os = null;
//		try {
//			//Set rendering options - such as file or stream output, 
//			//output format, whether it is embeddable, etc
//			options = new HTMLRenderOption();
//			
//			os = new ByteArrayOutputStream();
//			options.setOutputStream(os);
//			
//			//run the report and destroy the engine
//			IRunAndRenderTask task = prepareReport(reportFile,reportParameters,format);
//			
//			if(format == PDF_FORMAT)
//				options.setOutputFormat("pdf");
//			else if(format == HTML_FORMAT)
//				options.setOutputFormat("html");
//			
//			task.setRenderOption(options);
//			
//			task.run();
//			
//			return os;
//			
//		} catch (EngineException e) {
//			logger.error("",e);
//			return null;
//		} finally{
//			try {
//				os.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public static InputStream getContent(final String[] args) throws IOException, NoSuchAlgorithmException,
//	    KeyManagementException {
//		
//		// Create a trust manager that does not validate certificate chains
//		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
//	
//			@Override
//			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//				// TODO Auto-generated method stub
//				
//			}
//	
//			@Override
//			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//				// TODO Auto-generated method stub
//				
//			}
//	
//			@Override
//			public X509Certificate[] getAcceptedIssuers() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		    
//		} };
//		
//		// Install the all-trusting trust manager
//		final SSLContext sslContext = SSLContext.getInstance("SSL");
//		sslContext.init(null, trustAllCerts, null);
//		// Create an ssl socket factory with our all-trusting manager
//		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//		    public boolean verify(String urlHostName, SSLSession session) {
//		        return true;
//		    }
//		});
//		// be authentic
//		Authenticator.setDefault(new Authenticator() {
//		    @Override
//		    protected PasswordAuthentication getPasswordAuthentication() {
//		        return new PasswordAuthentication(args[0], args[1].toCharArray());
//		    }
//		});
//	
//		// All set up, we can get a resource through https now:
//		final URL url = new URL(args[0]);
//		
//		URLConnection connection = url.openConnection();
//		
//		return (InputStream) connection.getContent();
//	}
//
//	
//	static public String renderReportToFile(String reportFile, String outputFileName, Map reportParameters, int format) throws EngineException {
//		try {
//			if(reportFile.startsWith("https")) {
//				InputStream input = getContent(new String[]{reportFile});
//				return renderReportToFile(input,outputFileName,reportParameters,format);
//			} else if(reportFile.startsWith("META-INF")) {
//				return renderReportToFile(EditionProgrammeDefaut.class.getClassLoader().getResourceAsStream(reportFile),outputFileName,reportParameters,format);
//			}
//			return renderReportToFile(new FileInputStream(reportFile),outputFileName,reportParameters,format);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	static public String renderReportToFile(InputStream reportFile, String outputFileName, Map reportParameters, int format) throws EngineException {
//		try {
//			//Set rendering options - such as file or stream output, 
//			//output format, whether it is embeddable, etc
//			HTMLRenderOption options = new HTMLRenderOption();
//			
//			OutputStream os = new ByteArrayOutputStream();
//			options.setOutputStream(os);
//			
//			//run the report and destroy the engine
//			IRunAndRenderTask task = prepareReport(reportFile,reportParameters,format);
//			
//			if(format == PDF_FORMAT)
//				options.setOutputFormat("pdf");
//			else if(format == HTML_FORMAT)
//				options.setOutputFormat("html");
//			
//			options.setOutputFileName(outputFileName);
//			
//			task.setRenderOption(options);
//			
//			task.run();
//			
//			return outputFileName;
//			
//		} catch (EngineException e) {
//			logger.error("",e);
//			return null;
//		}
//	}
//	
//	private static IRunAndRenderTask prepareReport(String reportFile, Map<String,String> reportParameters, int format) throws EngineException {
//		try {
//			return prepareReport(new FileInputStream(reportFile),reportParameters,format);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	private static IRunAndRenderTask prepareReport(InputStream reportFile, Map<String,String> reportParameters, int format) throws EngineException {
//		//Open a report design - use design to modify design, retrieve embedded images etc. 
//		IReportRunnable design = engine.openReportDesign(reportFile);
//		
//		//Create task to run the report - use the task to execute and run the report,
//		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
//		
//		
//		for (String reportParam : reportParameters.keySet()) {
//			task.setParameterValue(reportParam,reportParameters.get(reportParam));
//		}
//		
//		//Set Render context to handle url and image locataions
//		HTMLRenderContext renderContext = new HTMLRenderContext();
//		renderContext.setImageDirectory("image");
//		HashMap contextMap = new HashMap();
//		contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
//		task.setAppContext( contextMap );
//		
//		return task;
//	}
//	
////	//EXEMPLE
////	private static void executeReport() throws EngineException {
////		//Engine Configuration - set and get temp dir, BIRT home, Servlet context
////		EngineConfig config = new EngineConfig();
////		//config.setEngineHome( "C:/birt-runtime-2_0_1/birt-runtime-2_0_1/Report Engine" );
////		
//////		System.setProperty( "RUN_UNDER_ECLIPSE", "true" );
////		//config.setConfigurationVariable("RUN_UNDER_ECLIPSE", "true");
////		config.setProperty("RUN_UNDER_ECLIPSE", "true");
////		//DtpManifestExplorer.getInstance( ).getExtensionManifests( );
////		
////		//Create the report engine
////		ReportEngine engine = new ReportEngine( config );
////		
////		//Open a report design - use design to modify design, retrieve embedded images etc. 
////		IReportRunnable design = engine.openReportDesign("C:/Projet/Java/Eclipse/GestionCommerciale/LibLgrBirt/src/libLgrBirt/test/test.rptdesign");
////		
////		//Create task to run the report - use the task to execute and run the report,
////		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
////		
////		task.setParameterValue("paramID_FACTURE","1");
////		task.setParameterValue("paramUrlJDBC","jdbc:firebirdsql://localhost/C:/Projet/Java/Eclipse/GestionCommerciale/@Run/lgrWorkspace/dossier/Bd/GEST_COM.FDB");
////		
////		//Set Render context to handle url and image locataions
////		HTMLRenderContext renderContext = new HTMLRenderContext();
////		renderContext.setImageDirectory("image");
////		HashMap contextMap = new HashMap();
////		contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
////		task.setAppContext( contextMap );
////		
////		//Set rendering options - such as file or stream output, 
////		//output format, whether it is embeddable, etc
////		HTMLRenderOption options = new HTMLRenderOption();
////		
////		//options.setOutputFileName("c:/aaaaaaaaaa.html");
////		//options.setOutputFormat("html");
////		
////		//options.setOutputFileName("c:/aaaaaaaaaa.pdf");
////		
////		OutputStream os = new ByteArrayOutputStream();
////		options.setOutputStream(os);
////		
////		options.setOutputFormat("pdf");
////		task.setRenderOption(options);
////		
////		//run the report and destroy the engine
////		task.run();
////		
////		engine.destroy();
////		
////		LgrSpooler spooler = LgrSpooler.getInstance();
////		//spooler.add("c:/aaaaaaaaaa.pdf");
////		spooler.add(os);
////		//spooler.add("c:/run.pdf");
////		//spooler.add("c:/a.pdf");
////		//spooler.print();
////		spooler.print(false);
////		
////	}
//
//	static public HashMap getAppContextEdition() {
//		return appContextEdition;
//	}
//
//	static public void setAppContextEdition(HashMap appContextEdition) {
//		BirtUtil_old.appContextEdition = appContextEdition;
//	}
//}
