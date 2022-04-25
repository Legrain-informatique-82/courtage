package fr.ylyade.courtage.service;

import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;



import fr.legrain.data.YlyadeProperties;
//import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibCrypto;
//import fr.legrain.lib.mail.LgrEmailUtil;
import fr.ylyade.courtage.service.LgrEmailUtil;
import fr.ylyade.courtage.service.LibConversion;
import fr.ylyade.courtage.droits.model.TaUtilisateur;

@Stateless
public class LgrEmail {
	
	private LgrEmailUtil util;
	
	private YlyadeProperties ylyadeProperties;
	private String separateurChaineCryptee = "~";
	
	@PostConstruct
	private void init() {
		ylyadeProperties = new YlyadeProperties();
		util = new LgrEmailUtil(
				ylyadeProperties.getProperty(YlyadeProperties.KEY_SMTP_HOSTNAME),
				LibConversion.stringToInteger(ylyadeProperties.getProperty(YlyadeProperties.KEY_SMTP_PORT)),
				LibConversion.StringToBoolean(ylyadeProperties.getProperty(YlyadeProperties.KEY_SMTP_SSL)),
				ylyadeProperties.getProperty(YlyadeProperties.KEY_SMTP_LOGIN),
				ylyadeProperties.getProperty(YlyadeProperties.KEY_SMTP_PASSWORD)
				); //STARTTLS
	}
	
	public void sendEmail(String subject, String msgTxt, String fromEmail, String fromName, String[][] toEmailandName) {
		util.sendEmail(subject,msgTxt,fromEmail,fromName,toEmailandName);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//A faire plus tard		
	public void emailPourLaValidationDeCreationDunCompteClient(TaUtilisateur utilisateur){ 
////	Sujet : Confirmation de la création de votre compte sur espaceclient.bdg.cloud
////	Bonjour,
////	Nous avons bien reçu votre demande de création d'un compte sur espaceclient.bdg.cloud.
////	Afin de confirmer cette création, merci de bien vouloir cliquer sur le lien ci-dessous pour finaliser l'actvation de votre espace client.
////	Pour toutes questions sur l'activation de votre espace client, vous pouvez contacter l'éditeur du logiciel Legrain Informatique au 05.63.30.31.44.
////	Très cordialement,
////	[NomDeLaSociete] 
//		
//		String subject = bdgProperties.getProperty(YlyadeProperties.KEY_NOM_DOMAINE)+" - Confirmation de la création de votre compte sur espaceclient."+bdgProperties.getProperty(YlyadeProperties.KEY_NOM_DOMAINE);
//		
//		//String a = utilisateur.getNom()+separateurChaineCryptee+utilisateur.getPrenom()+separateurChaineCryptee+utilisateur.getEmail()+separateurChaineCryptee+utilisateur.getPasswd();
//		String b = LibCrypto.encrypt(a);
//		
//		String lienCnx = "";
//		try {
//			lienCnx = bdgProperties.url("espaceclient")+"/validation_creation_compte.xhtml?p="+URLEncoder.encode(b, "UTF-8")+"";
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		String msgTxt = "Bonjour,\n"
//				+"\n"
//				+"Nous avons bien reçu votre demande de création d'un compte sur espaceclient."+bdgProperties.getProperty(YlyadeProperties.KEY_NOM_DOMAINE)+" .\n"
//				+"\n"
//				+"Afin de confirmer cette création, merci de bien vouloir cliquer sur le lien ci-dessous pour finaliser l'activation de votre espace client.\n"
//				+"\n"
//				+""+lienCnx+"\n"
//				+"\n"
//				+"Pour toutes questions sur l'activation de votre espace client, vous pouvez contacter l'éditeur du logiciel Legrain Informatique au 05.63.30.31.44.\n"
//				+"\n"
//				+"Email généré automatiquement par le programme Bureau de Gestion.\n"
//				+"\n"
//				;
//		
//		String fromEmail = bdgProperties.getProperty(YlyadeProperties.KEY_SMTP_LOGIN);
//		String fromName = "Bureau de Gestion";
//		String[][] toEmailandName = new String[][]{ {utilisateur.getEmail(),utilisateur.getNom()}};
//		
//		sendEmail(subject, msgTxt, fromEmail, fromName, toEmailandName);
//		
//		//envoie d'une copie à legrain
//		toEmailandName = new String[][]{ {"notifications@bdg.cloud",null}};
//		sendEmail(subject, msgTxt, fromEmail, fromName, toEmailandName);
	}
			
}
