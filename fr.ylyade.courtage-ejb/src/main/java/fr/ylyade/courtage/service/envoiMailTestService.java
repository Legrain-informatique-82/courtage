package fr.ylyade.courtage.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;
import fr.legrain.bdg.lib.osgi.Const;
import fr.legrain.data.YlyadeProperties;
import fr.legrain.lib.data.LibCrypto;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDocumentPdf;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaEmail;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.service.interfaces.remote.IEditionServiceRemonte;
import fr.ylyade.courtage.service.interfaces.remote.ITaDocumentPdfServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;

@Stateless
public class envoiMailTestService implements ITaEnvoiMailTestServiceRemote {
	@EJB private ITaLgrMailjetServiceRemote lgrMailjetService;
	@EJB private ITaDocumentPdfServiceRemote taDocumentPdfService;
	@EJB private ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	@EJB private ITaDossierRcdServiceRemote taDossierRcdService;
	
	@EJB private IEditionServiceRemonte editionService;
	
	private YlyadeProperties ylyadeProperties = new YlyadeProperties();
	
	String codeTemplateBase = "311444";
	
	@EJB private LgrEnvironnementServeur lgrEnvironnementServeur;
	
	String mailDefaut = "yann@legrain.fr";
	
	
	
	@Override
	public void envoiMailCourtierBordereaux(String mail) {
		envoiMailCourtierBordereaux( mail, null);
		
	}
	@Override
	public void envoiMailCourtierBordereaux(TaCourtier courtier) {
		envoiMailCourtierBordereaux( null, courtier);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 */
	@Override
	public void envoiMailCourtierBordereaux(String mail, TaCourtier courtier) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Bordereau disponible ";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que, comme tous les mois, le bordereau de commission est disponible. <br>"
				+ " Vous pouvez le visualiser et l'imprimer dans l'application "
				+ " via le menu \"Mon compte\" , \"Mon compte courtier\" et dans l'onglet \"Bordereaux de commission\". <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	public void envoiMailResiliationEcheance(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailResiliationEcheance(null, courtier, dossier, taEcheance);
	}
	public void envoiMailResiliationEcheance(String mail) {
		envoiMailResiliationEcheance(mail, null, null, null);
	}
	//mail 16
	public void envoiMailResiliationEcheance(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		if(dossier != null) {
			if(taEcheance != null) {
				chemin = editionService.genereAvisResiliationEcheancePDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
			}else {
				chemin = editionService.genereAvisResiliationEcheancePDF(dossier.getIdDossierRcd(),0,null);
				//a tester ci-dessus
			}
			ref = ref(dossier);
			
			file = new File(chemin);
			File[] pjTmp = {file};
			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: R??siliation ?? l'??ch??ance";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est r??sili?? ?? ??ch??ance. <br>"
				+ " Cette r??siliation r??sulte ou de nos services ou d'une demande du client.<br>"
				+ " Veuillez trouver ci-joint la lettre qui sera envoy??e au client par courrier recommand?? avec accus?? de r??ception. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
	}
	
	
	
	public void envoiMailCreationCourtier(TaCourtier courtier) {
		envoiMailCreationCourtier(null, courtier);
	}
	public void envoiMailCreationCourtier(String mail) {
		envoiMailCreationCourtier(mail, null);
	}
	//mail 1
	public void envoiMailCreationCourtier(String mail, TaCourtier courtier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;
		try {
			doc = taDocumentPdfService.findByCode(TaDocumentPdf.CODE_DOC_CO_COURTAGE);
			DocPjByte =  doc.getImgDoc();
			chemin = "/tmp/"+doc.getLiblDoc()+".pdf";
			path = Paths.get(chemin);
			Files.write(path, DocPjByte);
			file = new File(chemin);
			
			
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File[] pj = {file};
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Cr??ation de votre compte courtier";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous avons le plaisir de vous confirmer la cr??ation de votre compte pour acc??der ?? votre espace. <br><br>"
				+ " Vos codes sont les suivants : <br>"
				+ " Identifiant : "+ identifiant+" <br>"
				+ " Mot de passe : (Choisi ?? la cr??ation) <br><br>"
				+ " Une fois connect??, merci de nous envoyer tous les documents n??cessaires ?? la validation de votre compte via votre espace partenaire \"GED\" : <br>"
				+ " - Attestation ORIAS ?? jours <br>"
				+ " - KBIS de moins de trois mois <br>"
				+ "	- Convention de partenariat d??ment sign??e <br>"
				+ "	- Attestation RC PRO ?? jour <br>"
				+ "	- Attestation de garantie financi??re ?? jour <br>"
				+ " - RIB <br><br>"
				+ " Ci-joint votre convention de co-courtage. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
	}
	
	public void envoiMailChangementPass(TaUtilisateur utilisateur) {
		envoiMailChangementPass(null, utilisateur);
	}
	public void envoiMailChangementPass(String mail) {
		envoiMailChangementPass(mail, null);
	}
	public void envoiMailChangementPass(String mail, TaUtilisateur utilisateur) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(utilisateur != null) {
			user =  utilisateur;
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;	
		File[] pj = null;
		
		String identifiant = mail;
		String lienCnx = ylyadeProperties.url("");
		if(user != null) {
			identifiant = user.getIdentifiant();
			String a = identifiant;
			String b = LibCrypto.encrypt(a);
			try {
				b = URLEncoder.encode(b, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lienCnx = ylyadeProperties.url("")+"/login/mdp_validation.xhtml?p="+b+"";
		}
		
		 
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Demande de changement de mot de passe";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous avons re??u une demande de modification de mot passe pour votre Compte Novxxxxxxx underwriting.<br>"
				+"<br>"
				+"Afin de finaliser ce changement de mot de passe, merci de cliquer sur le lien suivant ( ou le copier/coller dans la barre d'adresse de votre navigateur ) :<br>"
				+"<br>"
				+"<b><a href='"+lienCnx+"'>"+lienCnx+"</a></b><br>"
				+"<br>"
				+"Si vous n'??tes pas ?? l'origine de cette demande, ignorez ce message. Vous conserverez vos identifiants habituels pour vous connecter sur votre compte Novxxxxxxx .<br>"
				+"<br>"
				+"Cordialement,<br>";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
	}
	
	public void envoiMailConfirmationChangementPass(TaUtilisateur utilisateur) {
		envoiMailConfirmationChangementPass(null, utilisateur);
	}
	public void envoiMailConfirmationChangementPass(String mail) {
		envoiMailConfirmationChangementPass(mail, null);
	}
	public void envoiMailConfirmationChangementPass(String mail, TaUtilisateur utilisateur) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(utilisateur != null) {
			user =  utilisateur;
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;	
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}

		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Confirmation de modification de votre mot de passe";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous vous confirmons que la modification de votre mot de passe a bien ??t?? prise en compte.<br>"
				+"<br>"
				+"Notez bien que seul votre mot de passe a ??t?? chang?? et que le nom d'utilisateur est toujours le m??me.\n"
				+"<br>"
				+"Important !<br>"
				+"<br>"
				+"Si vous n'??tes pas ?? l'origine de cette modification, veuillez contacter le plus vite possible notre support, soit :\n"
				+"<br>"
				+"Par t??l??phone : XX XX XX XX XX\n"
				+"<br>"
				+"Par Email : contact@novxxxxxx-uxxxxxxxxg.fr\n"
				+"<br>"
				+"En cas de communication par email, n'oubliez pas de rappeler votre nom d'utilisateur et votre code client.\n"
				+"<br>"
				+"Cordialement,<br>"
				+"\n"
				;
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
	}
	
	
	
	
	@Override
	public void envoiMailResiliationAmiable(String mail) {
		envoiMailResiliationAmiable( mail, null, null, null);
		
	}
	@Override
	public void envoiMailResiliationAmiable(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailResiliationAmiable( null, courtier, dossier, taEcheance);
		
	}
	@Override
	//mail ??
	public void envoiMailResiliationAmiable(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		if(dossier != null) {
//			if(taEcheance != null) {
//				chemin = editionService.genereAvisResiliationAmiablePDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
//			}else {
//				chemin = editionService.genereAvisResiliationAmiablePDF(dossier.getIdDossierRcd(),0,null);
//				//a tester ci-dessus
//			}
			ref = ref(dossier);
			
//			file = new File(chemin);
//			File[] pjTmp = {file};
//			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: R??siliation ?? l'amiable";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est r??sili?? ?? l'amiable. <br>"
				+ " Cette r??siliation r??sulte ou de nos services ou d'une demande du client.<br>"
				//+ " Veuillez trouver ci-joint la lettre qui sera envoy??e au client par courrier recommand?? avec accus?? de r??ception. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	@Override
	public void envoiMailResiliationFausseDecla(String mail) {
		envoiMailResiliationFausseDecla( mail, null, null, null);
		
	}
	@Override
	public void envoiMailResiliationFausseDecla(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailResiliationFausseDecla(null,  courtier, dossier, taEcheance);
		
	}
	@Override
	//mail 19
	public void envoiMailResiliationFausseDecla(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		//PJ a mettre des que les editions (lettres) sont pr??tes
		if(dossier != null) {
//			if(taEcheance != null) {
//				chemin = editionService.genereAvisResiliationFausseDeclaPDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
//			}else {
//				chemin = editionService.genereAvisResiliationFausseDeclaPDF(dossier.getIdDossierRcd(),0,null);
//			}
			ref = ref(dossier);
//			
//			file = new File(chemin);
//			File[] pjTmp = {file};
//			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: R??siliation pour fausse d??claration";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est r??sili?? pour fausse d??claration. <br>"
				+ " Veuillez trouver ci-joint la lettre qui sera envoy??e au client par courrier recommand?? avec accus?? de r??ception. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	@Override
	public void envoiMailResiliationCessation(String mail) {
		envoiMailResiliationCessation( mail, null, null, null);
		
	}
	@Override
	public void envoiMailResiliationCessation(TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailResiliationCessation( null, courtier, dossier, null);
		
	}
	@Override
	//mail 17 et lettre 5
	public void envoiMailResiliationCessation(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		//PJ a mettre des que les editions (lettres) sont pr??tes
		if(dossier != null) {
//			if(taEcheance != null) {
//				chemin = editionService.genereAvisResiliationCessationPDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
//			}else {
//				chemin = editionService.genereAvisResiliationCessationPDF(dossier.getIdDossierRcd(),0,null);
//			}
			ref = ref(dossier);
//			
//			file = new File(chemin);
//			File[] pjTmp = {file};
//			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: R??siliation pour c??ssation d'activit?? ou radiation";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est r??sili?? pour c??ssation d'activit?? ou radiation. <br>"
				+ " Cette r??siliation r??sulte ou de nos services ou d'une demande du client.<br>"
				+ " Veuillez trouver ci-joint la lettre qui sera envoy??e au client par courrier recommand?? avec accus?? de r??ception. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	@Override
	public void envoiMailResiliationSansEffetAnnuler(String mail) {
		envoiMailResiliationSansEffetAnnuler( mail, null, null, null);
		
	}
	@Override
	public void envoiMailResiliationSansEffetAnnuler(TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailResiliationSansEffetAnnuler(null,  courtier, dossier,  taEcheance);
		
	}
	@Override
	//mail 18
	public void envoiMailResiliationSansEffetAnnuler(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		if(dossier != null) {
//			if(taEcheance != null) {
//				//chemin = editionService.genereAvisResiliationSansEffetPDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
//			}else {
//				//chemin = editionService.genereAvisResiliationSansEffetPDF(dossier.getIdDossierRcd(),0,null);
//				//a tester ci-dessus
//			}
			ref = ref(dossier);
//			
//			file = new File(chemin);
//			File[] pjTmp = {file};
//			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: R??siliation suite ?? un sans effet";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est r??sili?? suite ?? un sans effet. <br>"
				+ " Veuillez trouver ci-joint la lettre qui sera envoy??e au client par courrier recommand?? avec accus?? de r??ception. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailResiliationImpaye(String mail) {
		envoiMailResiliationImpaye( mail, null, null ,null);
		
	}
	@Override
	public void envoiMailResiliationImpaye(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailResiliationImpaye( null, courtier, dossier, taEcheance);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 */
	@Override
	//Mail 15
	public void envoiMailResiliationImpaye(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
		}else {
			userEmail.add(mail);
		}

		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		if(dossier != null && taEcheance != null) {
					ref = ref(dossier);
					chemin = editionService.genereAvisResiliationImpayePDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
					file = new File(chemin);
					File[] pjTmp = {file};
					pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: R??siliation suite ?? impay??";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est r??sili?? suite ?? impay??. <br>"
				+ " Afin de vous permettre d'effectuer une remise en vigueur en r??gularisant le montant d?? (vous disposez d'un mois maximum apr??s r??siliation),"
				+ " et que vous puissiez informer vos clients, veuillez trouver ci-joint la notification  de r??siliation concern??e.<br>"
				+ " Cette derni??re est envoy??e par courrier recommand?? avec accus?? de r??ception ?? vos clients. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailBientotATerme(String mail) {
		envoiMailBientotATerme( mail, null, null);
		
	}
	@Override
	public void envoiMailBientotATerme(TaCourtier courtier, List<TaDossierRcdDTO> listeDossier) {
		envoiMailBientotATerme( null, courtier, listeDossier);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 */
	@Override
	public void envoiMailBientotATerme(String mail, TaCourtier courtier, List<TaDossierRcdDTO> listeDossier) {
		//rajouter nombre de dossier concern??s
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		
		
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		Integer nbDossier = 0;
		if(listeDossier != null && !listeDossier.isEmpty()) {
			nbDossier = listeDossier.size();
		}
		Integer it = 0;
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Un ou plusieurs contrats arrivent ?? terme";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que <b>"+nbDossier+"</b> de vos contrats arrivent ?? terme."
				+ " Afin que vous puissiez informer vos clients, voici quelques-uns des contrats concern??s :<br><br>";
		if(listeDossier != null && !listeDossier.isEmpty()) {
			for (TaDossierRcdDTO doss : listeDossier) {
				it++;
				if(it <= 10) {
					corpsMail+= " <b>Ref Contrat : </b>"+refDTO(doss)+" <br>";
					corpsMail+= " <b>Client : </b>"+doss.getRaisonSociale()+" <br><br>";
				}
			
			}
		}
		
		corpsMail+= " ... <br><br>";
		corpsMail+= " Pour voir tous les dossiers qui arrivent bient??t ?? terme, rendez-vous sur l'application. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	
	
	@Override
	public void envoiMailMiseEnDemeureImpaye(String mail) {
		envoiMailMiseEnDemeureImpaye( mail, null, null, null);
		
	}
	@Override
	public void envoiMailMiseEnDemeureImpaye(TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailMiseEnDemeureImpaye( null, courtier, dossier, taEcheance);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 */
	@Override
	//mail 13 avec PJ lettre 1 (mise en demeure)
	public void envoiMailMiseEnDemeureImpaye(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		if(dossier != null && taEcheance != null) {
			ref = ref(dossier);
			chemin = editionService.genereAvisMiseEnDemeurePDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
			file = new File(chemin);
			File[] pjTmp = {file};
			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Un contrat mis en demeure pour impay??";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est mis en demeure pour impay??."
				+ " Afin d'??viter la suspension des garanties, et que vous puissiez informer vos clients, veuillez trouver ci-joint l'avis de mise en demeure concern??."
				+ " Ce dernier est envoy?? par courrier recommand?? avec accus?? de r??ception ?? votre client. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailSuspenduNonPaiement(String mail) {
		envoiMailSuspenduNonPaiement( mail, null, null, null);
		
	}
	@Override
	public void envoiMailSuspenduNonPaiement(TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {
		envoiMailSuspenduNonPaiement( null, courtier,dossier, taEcheance);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 * 
	 */
	@Override
	//mail 14
	public void envoiMailSuspenduNonPaiement(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		String ref ="ref0000";
		if(dossier != null && taEcheance != null) {
			ref = ref(dossier);
			chemin = editionService.genereAvisSuspensionGarantiePDF(dossier.getIdDossierRcd(),taEcheance.getIdEcheance(),null);
			file = new File(chemin);
			File[] pjTmp = {file};
			pj = pjTmp;
		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Un contrat suspendu suite ?? un impay??";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que le contrat <b>"+ref+"</b> est suspendu suite ?? un impay??."
				+ " Afin d'??viter la r??siliation, et que vous puissiez informer vos clients, veuillez trouver ci-joint l'avis de suspension concern??."
				+ " Ce dernier est envoy?? par courrier recommand?? avec accus?? de r??ception ?? votre client. <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailValidationSuperAssureur(String mail) {
		envoiMailValidationSuperAssureur( mail, null, null);
		
	}
	@Override
	public void envoiMailValidationSuperAssureur(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailValidationSuperAssureur(null,  courtier, dossier);
		
	}
	@Override
	//mail 6
	public void envoiMailValidationSuperAssureur(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref = ref(dossier);
			//chemin = editionService.genereConditionParticulierePDF(dossier.getIdDossierRcd(),null);
//			chemin = editionService.genereAttestationAssurancePDF(dossier.getIdDossierRcd(),null);
//			file = new File(chemin);
//			File[] pjTmp = {file};
//			pj = pjTmp;
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Affaire valid??e";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous vous informons que l'affaire cit?? ci-dessous est valid??e. <br>"
				+ " Veuillez nous retourner les Conditions particuli??res sign??es via votre espace \"GED \".<br>"
				+ " L'attestation de votre client pour la p??riode pay??e est ??galement disponible dans votre espace. <br><br>"
				+ " Client : "+nomSociete+" <br>"
				+ " Ref : "+ref+" <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailActivationCourtier(String mail) {
		envoiMailActivationCourtier( mail, null);
		
	}
	@Override
	public void envoiMailActivationCourtier(TaCourtier courtier) {
		envoiMailActivationCourtier(null,  courtier);
		
	}
	@Override
	public void envoiMailActivationCourtier(String mail, TaCourtier courtier) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Activation de votre compte";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "F??licitation, votre compte est actif, maintenant vous pouvez souscrire des affaires nouvelles et recevoir vos commissions.<br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
	}
	
	@Override
	public void envoiMailEnregistrementDevis(String mail) {
		envoiMailEnregistrementDevis( mail, null, null);
		
	}
	@Override
	public void envoiMailEnregistrementDevis(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailEnregistrementDevis(null,  courtier, dossier);
		
	}
	@Override
	//mail 4 pas de pj
	public void envoiMailEnregistrementDevis(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		TaGedUtilisateur ged = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;
		File[] pj = null;
		if(dossier != null) {
			chemin = editionService.generePropositionDevisPDF(dossier.getIdDossierRcd(),null);
			file = new File(chemin);
			File[] pjTmp = {file};
			pj = pjTmp;
		}
			
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref=ref(dossier);
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Enregistrement d'un devis";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Vous venez d'enregistrer une proposition sous la r??f??rence <b>"+ref+"</b> pour le prospect <b>"+nomSociete+"</b> et nous vous en remercions."
				+ " Pour souscrire cette affaire merci d'envoyer l'ensemble des pi??ces n??cessaires via votre espace extranet : <br><br>"
				+ " - Relev?? de sinistralit?? (moins de trois)<br>"
				+ " - Derni??re attestation RC D??cennale <br>"
				+ " - Justificatifs d'exp??riences (en rapport avec les activit??s)<br>"
				+ " - KBIS de moins de trois mois<br>"
				+ " - Papier ?? en-t??te ou carte de visite<br>"
				+ " - Proposition sign??e <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailPaiementComptant(String mail) {
		envoiMailPaiementComptant( mail, null, null);
		
	}
	@Override
	public void envoiMailPaiementComptant(TaCourtier courtier,  TaReglementAssurance reglement) {
		envoiMailPaiementComptant(null,  courtier, reglement);
		
	}
	@Override
	//Concerne le 1er paiement
	public void envoiMailPaiementComptant(String mail, TaCourtier courtier, TaReglementAssurance reglement) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String nomClient = "";
		String ref = "0000000";
		String montant = "";
		String modePaiement = "";
		String periode = "";
		
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		
		if(reglement != null) {
			TaDossierRcd dos = reglement.getTaEcheance().getTaDossierRcd();
			nomClient = dos.getTaAssure().getRaisonSociale();
			ref=ref(dos);
			montant = reglement.getMontant().toString();
			modePaiement = reglement.getTaTReglement().getLiblTReglement();
			periode = " Du "+df.format(reglement.getTaEcheance().getDateDebutEcheance()) +" au "+df.format(reglement.getTaEcheance().getDateEcheance());
		}
		
		
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: confirmation de paiement du comptant";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous vous confirmons le r??glement du comptant de votre client ci-dessous :<br><br>"
				+ " Client : "+nomClient+"<br>"
				+ " R??f??rence contrat : "+ref+"<br>"
				+ " Montant r??gl?? : "+montant+"<br>"
				+ " Mode de paiement : "+modePaiement+"<br>"
				+ " P??riode concern??e : "+periode+"<br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
	}
	
	@Override
	public void envoiMailEnregistrementDevisAvenant(String mail) {
		envoiMailEnregistrementDevisAvenant( mail, null, null);
		
	}
	@Override
	public void envoiMailEnregistrementDevisAvenant(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailEnregistrementDevisAvenant(null,  courtier, dossier);
		
	}
	@Override
	//mail 7
	public void envoiMailEnregistrementDevisAvenant(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
//		plus d'envoi de PJ
//		if(dossier != null) {
//			chemin = editionService.generePropositionDevisPDF(dossier.getIdDossierRcd(),null);
//			file = new File(chemin);
//			File[] pjTmp = {file};
//			pj = pjTmp;
//		}
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref=ref(dossier);
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Demande d'avenant";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Vous venez d'enregistrer une proposition d'avenant pour votre client cit?? ci-dessous."
				+ " Pour valider cet avenant, merci d'effectuer le r??glement de prorata demand?? dans votre espace, et de nous envoyer la \" Proposition d'avenant \" d??ment sign??e. <br><br>"
				+ " Client : "+nomSociete+"<br>"
				+ " R??f??rence : "+ref+"<br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailValidationSuperAssureurAvenant(String mail) {
		envoiMailValidationSuperAssureurAvenant( mail, null, null);
		
	}
	@Override
	public void envoiMailValidationSuperAssureurAvenant(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailValidationSuperAssureurAvenant(null,  courtier, dossier);
		
	}
	@Override
	//mail 8
	public void envoiMailValidationSuperAssureurAvenant(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref=ref(dossier);
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Avenant valid??";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous vous informons avoir valid?? l'avenant de votre client cit?? ci-dessous. <br>"
				+ " Les conditions particuli??res sont ?? nous retourner d??ment sign??es via votre espace. <br><br>"
				+ " Client : "+nomSociete+" <br>"
				+ " Ref : "+ref+" <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailRefusSuperAssureurAvenant(String mail) {
		envoiMailRefusSuperAssureurAvenant( mail, null, null);
		
	}
	@Override
	public void envoiMailRefusSuperAssureurAvenant(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailRefusSuperAssureurAvenant(null,  courtier, dossier);
		
	}
	@Override
	//mail 9
	public void envoiMailRefusSuperAssureurAvenant(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref=ref(dossier);
			
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Refus d'Avenant";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous vous informons avoir trait?? l'avenant de votre client cit?? ci-dessous. <br>"
				+ " Suite ?? cela, nous refusons la demande d'avenant car elle est hors cadre de souscription.<br>"
				+ " Si vous souhaitez mettre un terme ?? votre contrat suite ?? ce refus,"
				+ " merci de nous envoyer votre demande de r??siliation par courrier recommand?? avec accus?? de r??ception ?? l'adresse suivante : <br><br>"
				+ " <i>Novxxxxxxx Underwriting</i> <br>"
				+ " <i>21 Avenue Gambetta</i> <br>"
				+ " <i>82000 MONTAUBAN</i> <br><br>"
				+ " Client : "+nomSociete+" <br>"
				+ " Ref : "+ref+" <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailUneSemaineApresTerme(String mail) {
		envoiMailUneSemaineApresTerme( mail, null, null);
		
	}
	@Override
	public void envoiMailUneSemaineApresTerme(TaCourtier courtier,  List<TaDossierRcdDTO> listeDossier) {
		envoiMailUneSemaineApresTerme( null, courtier, listeDossier);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 * (Mail 12)
	 */
	@Override
	public void envoiMailUneSemaineApresTerme(String mail, TaCourtier courtier,  List<TaDossierRcdDTO> listeDossier) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		Integer nbDossier = 0;
		if(listeDossier != null && !listeDossier.isEmpty()) {
			nbDossier = listeDossier.size();
		}
		Integer it = 0;
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Un ou plusieurs contrats ont pass??s leur terme";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous pour vous signifier que <b>"+nbDossier+"</b> de vos contrats ont pass??s leur terme et risque une mise en demeure de payer."
				+ " Afin que vous puissiez informer vos clients, voici quelques-uns des contrats concern??s : <br><br>";
				if(listeDossier != null && !listeDossier.isEmpty()) {
					for (TaDossierRcdDTO doss : listeDossier) {
						it++;
						if(it <= 10) {
							corpsMail+= " <b>Ref Contrat : </b>"+refDTO(doss)+" <br>";
							corpsMail+= " <b>Client : </b>"+doss.getRaisonSociale()+" <br><br>";
						}
					
					}
				}
				
				corpsMail+= " ... <br><br>";
				corpsMail+= " Pour voir tous les dossiers qui ont pass??s leur terme, rendez-vous sur l'application. <br><br>";
				corpsMail+= " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailCourtierInactif(String mail) {
		envoiMailCourtierInactif( mail, null);
		
	}
	@Override
	public void envoiMailCourtierInactif(TaCourtier courtier) {
		envoiMailCourtierInactif( null, courtier);
		
	}
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 * (Mail 2)
	 */
	@Override
	public void envoiMailCourtierInactif(String mail, TaCourtier courtier) {

		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: Compte inactif";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous revenons vers vous pour vous signifier que votre compte est toujours \"inactif\", afin de le rendre \"actif\""
				+ " merci de vous connecter et de nous faire parvenir les pi??ces manquantes ?? votre dossier : <br><br>"
				+ " - Attestation ORIAS ?? jours <br>"
				+ " - KBIS de moins de trois mois <br>"
				+ "	- Convention de partenariat d??ment sign??e <br>"
				+ "	- Attestation RC PRO ?? jour <br>"
				+ "	- Attestation de garantie financi??re ?? jour <br>"
				+ " - RIB <br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailPieceInvalidesBis(String mail) {
		envoiMailPieceInvalidesBis( mail, null, null);
		
	}
	@Override
	public void envoiMailPieceInvalidesBis(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailPieceInvalidesBis(null,  courtier, dossier);
		
	}
	@Override
	/**
	 * @author yann
	 * Attention, cet envoi de mail est d??clench?? par un Timer
	 * (Mail 5 BIS)
	 */
	public void envoiMailPieceInvalidesBis(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		List<TaGedUtilisateur> listeGed = new ArrayList<TaGedUtilisateur>();
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref=ref(dossier);
			
			for (TaGedUtilisateur ged : taGedUtilisateurService.findAllByIdDossier(dossier.getIdDossierRcd())) {
				if(ged.getValidationYlyade() != null && ged.getValidationYlyade() == false) {
					listeGed.add(ged);
				}
			}
			
			
		}else {
			TaGedUtilisateur ged1 = new TaGedUtilisateur();
			TaListeRefDoc listeRef = new TaListeRefDoc();
			listeRef.setLiblDoc("Pi??ce 1");
			ged1.setCommentaireYlyade("Commentaire Novxxxxxxx 1");
			ged1.setTaListeRefDoc(listeRef);
			
			TaGedUtilisateur ged2 = new TaGedUtilisateur();
			TaListeRefDoc listeRef2 = new TaListeRefDoc();
			listeRef2.setLiblDoc("Pi??ce 2");
			ged2.setCommentaireYlyade("Commentaire Novxxxxxxx 2");
			ged2.setTaListeRefDoc(listeRef2);
			
			listeGed.add(ged1);
			listeGed.add(ged2);
		}
		
		
		
	
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: relance pi??ces invalides";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous concernant l'affaire en cour de validation ci-dessous : <br><br>"
				+ " Client : "+nomSociete+" <br>"
				+ " Ref : "+ref+" <br><br>"
				+ " En effet, nous sommes toujours dans l'attente des pi??ces ci-dessous : <br><br>";
				
				for (TaGedUtilisateur taGedUtilisateur : listeGed) {
					corpsMail+= " <b>Nom de la pi??ce :</b> "+taGedUtilisateur.getTaListeRefDoc().getLiblDoc()+"<br>"
							 + " <b>Commentaire : </b><br>";
					if(taGedUtilisateur.getCommentaireYlyade() != null) {
						corpsMail+= "<p style=\"border: solid 1px; padding: 5px;\"> "+taGedUtilisateur.getCommentaireYlyade()+"</p> <br><br>";
					}else {
						corpsMail+= "<p style=\"border: solid 1px; padding: 5px;\"> </p> <br><br>";
					}
					
				}
			
					
				corpsMail+= " Merci de faire le n??cessaire afin que votre affaire soit valid?? par nos services.<br><br>"
				+ " Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	@Override
	public void envoiMailPieceInvalides(String mail) {
		envoiMailPieceInvalides( mail, null, null);
		
	}
	@Override
	public void envoiMailPieceInvalides(TaCourtier courtier, TaDossierRcd dossier) {
		envoiMailPieceInvalides(null,  courtier, dossier);
		
	}
	@Override
	/**
	 * (Mail 5 ) d??clench?? par un bouton sur le devis
	 */
	public void envoiMailPieceInvalides(String mail, TaCourtier courtier, TaDossierRcd dossier) {
		TaUtilisateur user = null;
		List<String> userEmail = new ArrayList<String>();
		
		if(courtier != null) {
			user =  courtier.getTaUtilisateur();
		}
		if(user != null) {
			//si on est pas en prod, tout ces mails sont envoy??s sur une adresse de test (yann@legrain.fr) 
			if(lgrEnvironnementServeur.isModeTestPaiementDossier()) {
				userEmail.add(mailDefaut);
				
			//si on est en prod, on peut envoy?? au courtier
			}else {
				userEmail.add(user.getIdentifiant()); 
			}
			
		}else {
			userEmail.add(mail);
		}
		
		String[] emails = userEmail.toArray(new String[0]);
		
		TaDocumentPdf doc = null;
		byte[] DocPjByte = null;
		String chemin = null;
		Path path = null;
		File file = null;

		
		File[] pj = null;
		
		String identifiant = mail;
		if(user != null) {
			identifiant = user.getIdentifiant();
		}
		String nomSociete = "nomSociete";
		String ref = "0000000";
		List<TaGedUtilisateur> listeGed = new ArrayList<TaGedUtilisateur>();
		if(dossier != null) {
			nomSociete = dossier.getTaAssure().getRaisonSociale();
			ref=ref(dossier);
			
			for (TaGedUtilisateur ged : taGedUtilisateurService.findAllByIdDossier(dossier.getIdDossierRcd())) {
				if(ged.getValidationYlyade() != null && ged.getValidationYlyade() == false) {
					listeGed.add(ged);
				}
			}
			
			
		}else {
			TaGedUtilisateur ged1 = new TaGedUtilisateur();
			TaListeRefDoc listeRef = new TaListeRefDoc();
			listeRef.setLiblDoc("Pi??ce 1");
			ged1.setCommentaireYlyade("Commentaire Novxxxxxxx 1");
			ged1.setTaListeRefDoc(listeRef);
			
			TaGedUtilisateur ged2 = new TaGedUtilisateur();
			TaListeRefDoc listeRef2 = new TaListeRefDoc();
			listeRef2.setLiblDoc("Pi??ce 2");
			ged2.setCommentaireYlyade("Commentaire Novxxxxxxx 2");
			ged2.setTaListeRefDoc(listeRef2);
			
			listeGed.add(ged1);
			listeGed.add(ged2);
		}
		
		
		
	
		
		String objetMail = "NOVX-XXXXXX UXXXXXXXXXXG: pi??ces invalides";
		String titreCorpsMail = "Madame, Monsieur,";
		String corpsMail = "Nous venons vers vous concernant l'affaire en cour de validation ci-dessous : <br><br>"
				+ " Client : "+nomSociete+" <br>"
				+ " Ref : "+ref+" <br><br>"
				+ " En effet, une ou plusieurs pi??ces sont invalide : <br><br>";
				
				for (TaGedUtilisateur taGedUtilisateur : listeGed) {
					corpsMail+= " <b>Nom de la pi??ce :</b> "+taGedUtilisateur.getTaListeRefDoc().getLiblDoc()+"<br>"
							 + " <b>Commentaire : </b><br>";
					if(taGedUtilisateur.getCommentaireYlyade() != null) {
						corpsMail+= "<p style=\"border: solid 1px; padding: 5px;\"> "+taGedUtilisateur.getCommentaireYlyade()+"</p> <br><br>";
					}else {
						corpsMail+= "<p style=\"border: solid 1px; padding: 5px;\"> </p> <br><br>";
					}
					
				}
			
					
				corpsMail+= "Pour acc??der ?? l'application cliquez <a href='https://novxxxxxxt.novxxxxxxs.fr'> ici </a>. <br><br>"
				+ " Nous esp??rons que ce service vous donnera enti??re satisfaction et vous prions d'agr??er, Madame, Monsieur, l'expression de nos sentiments les meilleurs. <br><br>"
				+ " ";
		
		Map<String, String> variables = new HashMap<>();
		variables.put("corpsMail", corpsMail);
		variables.put("titreCorpsMail", titreCorpsMail);
		
		lgrMailjetService.send(null, "NOVX-XXXXXX UXXXXXXXXXXG", objetMail, corpsMail, null, emails,null, null,pj, codeTemplateBase, variables  );
		
	}
	
	private String refDTO(TaDossierRcdDTO doss){
		String ref="";
		if(doss.getLettrePjNumPolice() != null) {
			ref = doss.getNumDossierPolice()+doss.getLettrePjNumPolice();
		}else {
			ref = doss.getNumDossierPolice();
		}
		if(doss.getNumeroAvenant() != null) {
			ref+= " - "+doss.getNumeroAvenant();
		}
		
		return ref;
	}
	private String ref(TaDossierRcd doss) {
		String ref="";
		if(doss.getLettrePjNumPolice() != null) {
			ref = doss.getNumDossierPolice()+doss.getLettrePjNumPolice();
		}else {
			ref = doss.getNumDossierPolice();
		}
		if(doss.getNumeroAvenant() != null) {
			ref+= " - "+doss.getNumeroAvenant();
		}
		
		return ref;
	}
	

}
