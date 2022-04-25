package fr.ylyade.courtage.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

//import com.stripe.Stripe;

//import fr.legrain.bdg.email.service.remote.ITaMessageEmailServiceRemote;
import fr.legrain.bdg.lib.osgi.Const;
import fr.legrain.data.YlyadeProperties;
//import fr.legrain.droits.model.TaUtilisateur;
//import fr.legrain.email.dao.jpa.PiecesJointesEmailDAO;
//import fr.legrain.email.model.TaContactMessageEmail;
//import fr.legrain.email.model.TaMessageEmail;
//import fr.legrain.email.model.TaPieceJointeEmail;
//import fr.legrain.hibernate.multitenant.SchemaResolver;
//import fr.legrain.lib.mail.LgrMailjet;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.model.TaEmail;
//import fr.legrain.tiers.model.TaEmail;
//import fr.legrain.tiers.model.TaTiers;
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;

@Stateless
public class LgrMailjetService implements ITaLgrMailjetServiceRemote {

	private LgrMailjet lgrMailjet = new LgrMailjet();
	protected YlyadeProperties ylyadeProperties = null;
	
//	private @EJB ITaMessageEmailServiceRemote taMessageEmailService;
	
	private final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();  
	
	
//	public void send(String fromName, String subject, String htmlpart, String[] destinataires) {
//		send(null, fromName, subject, null, htmlpart, destinataires, null, null, null);
//	}
	
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, File[] attachment) {
		send(fromEmail, fromName, subject, textPart, htmlPart, destinataires, null, null, attachment);
	}

	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, String[] cc, String[] bcc, File[] attachment) {

			send(fromEmail, fromName, subject, textPart, htmlPart, destinataires, cc, bcc, attachment, null, null);
	}
	
	
	
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, String[] cc, String[] bcc, File[] attachment, String templateId, Map<String, String> variables) {

		ylyadeProperties = new YlyadeProperties();
//		SchemaResolver sr = new SchemaResolver();
		try {
			if(fromEmail==null) {
				fromEmail = "noreply@ylydev.work";
				
				String ip = ylyadeProperties.getProperty("ip");
				if(ip==null || ip.equals("") ||  ip.equals("127.0.0.1")) {
					fromEmail = "noreply@ylydev.work";
				} else if(ip.equals("92.243.3.94")) {
					//serveur de test DEV-internet
					fromEmail = "noreply@ylydev.work";
				} else if(ip.equals("92.243.6.42")) {
					//serveur de test PPROD-internet
					fromEmail = "noreply@ylypprod.work";
				} else if(ip.equals("217.70.188.210")) {
					//serveur de test PROD-internet
					fromEmail = "noreply@ylypprod.work";
				} else if(ip.equals("92.243.2.185")) {
					//rajout yann
					//serveur de PROD-internet
					fromEmail = "production@novarisks-underwriting.fr";
				}
				
//				String localPath = ylyadeProperties.osTempDirDossier(sr.resolveCurrentTenantIdentifier())+"/"+ylyadeProperties.tmpFileName("facture.pdf");
			}
			lgrMailjet.send_v3_1(fromEmail, fromName, subject, textPart, htmlPart, destinataires, cc, bcc, attachment, templateId, variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	
//	public void sendAndLog(String fromEmail, String fromName, String subject, String textPart, String htmlPart, 
//			List<String> adressesDestinataire, List<TaEmail> emailTiersDestinataire, List<TaContactMessageEmail> contactMessageEmailDestinataire,
//			List<String> adressesCc, List<TaEmail> emailTiersCc, List<TaContactMessageEmail> contactMessageEmailCc,
//			List<String> adressesBcc, List<TaEmail> emailTiersBcc, List<TaContactMessageEmail> contactMessageEmailBcc,
//			File[] attachment, 
//			TaUtilisateur taUtilisateur
//			) {
//		
//		try {
//	
//			TaMessageEmail taMessageEmail = buildTaMessageEmail(fromEmail, fromName, subject, textPart, htmlPart, 
//					adressesDestinataire, emailTiersDestinataire, contactMessageEmailDestinataire,
//					adressesCc, emailTiersCc, contactMessageEmailCc,
//					adressesBcc, emailTiersBcc, contactMessageEmailBcc,
//					attachment,
//					taUtilisateur);
//			
//			sendAndLog(taMessageEmail,attachment);
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 
	 * @param taMessageEmail
	 * @param attachment - dans un TaMessageEmail les pieces jointes en byte[] en mémoire, 
	 * 		pour éviter une nouvelle conversion on peu passer la liste de fichier directement 
	 * 		MAIS en cas de mauvaise manipulation la liste de fichier enregistrés dans le TaMessageEmail pourait différait de celle envoyé dans le mail
	 */
//	private void sendAndLog(TaMessageEmail taMessageEmail, File[] attachment) {
//		try {
//			/*
//			 * destinatires,cc,bcc,contact et expéditeur
//			 */
//			//DESTINATAIRE
//			int nbDest = (taMessageEmail.getDestinataires()!=null?taMessageEmail.getDestinataires().size():0);
//			String[] dest = new String[nbDest];
//			int i = 0;
//			if(taMessageEmail.getDestinataires()!=null) {
//				for (TaContactMessageEmail c : taMessageEmail.getDestinataires()) {
//					c.setTaMessageEmail(taMessageEmail);
//					dest[i] = c.getAdresseEmail();
//					i++;
//				}
//			}
//			
//			//CC
//			int nbCc = (taMessageEmail.getCc()!=null?taMessageEmail.getCc().size():0);
//			String[] cc = new String[nbCc];
//			i = 0;
//			if(taMessageEmail.getCc()!=null) {
//				for (TaContactMessageEmail c : taMessageEmail.getCc()) {
//					c.setTaMessageEmailCc(taMessageEmail);
//					cc[i] = c.getAdresseEmail();
//					i++;
//				}
//			}
//			
//			//BCC
//			int nbBcc = (taMessageEmail.getBcc()!=null?taMessageEmail.getBcc().size():0);
//			String[] bcc = new String[nbBcc];
//			i = 0;
//			if(taMessageEmail.getBcc()!=null) {
//				for (TaContactMessageEmail c : taMessageEmail.getBcc()) {
//					c.setTaMessageEmailBcc(taMessageEmail);
//					bcc[i] = c.getAdresseEmail();
//					i++;
//				}
//			}
//			
//			File[] pj = null;
//			if(attachment!=null) {
//				pj = attachment;
//			} else {
//				int nbPJ = (taMessageEmail.getPiecesJointes()!=null?taMessageEmail.getPiecesJointes().size():0);
//				pj = new File[nbPJ];
//				i = 0;
//				if(taMessageEmail.getPiecesJointes()!=null) {
//					for (TaPieceJointeEmail c : taMessageEmail.getPiecesJointes()) {
//						File f = new File(c.getNomFichier());
//						Path path = Paths.get(f.getAbsolutePath());
//						Files.write(path, c.getFichier());
//						pj[i] = f;
//						i++;
//					}
//				}
//			}
//			
//			send(
//				taMessageEmail.getAdresseExpediteur(),//null, 
//				taMessageEmail.getNomExpediteur(),//taInfoEntrepriseService.findInstance().getNomInfoEntreprise(), 
//				taMessageEmail.getSubject(), 
//				taMessageEmail.getBodyPlain(), 
//				taMessageEmail.getBodyHtml(),
//				dest,
//				cc,
//				bcc,
//				pj
//				);
//
//			taMessageEmail = taMessageEmailService.merge(taMessageEmail);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	private TaMessageEmail buildTaMessageEmail(String fromEmail, String fromName, String subject, String textPart, String htmlPart, 
//			List<String> adressesDestinataire, List<TaEmail> emailTiersDestinataire, List<TaContactMessageEmail> contactMessageEmailDestinataire,
//			List<String> adressesCc, List<TaEmail> emailTiersCc, List<TaContactMessageEmail> contactMessageEmailCc,
//			List<String> adressesBcc, List<TaEmail> emailTiersBcc, List<TaContactMessageEmail> contactMessageEmailBcc,
//			File[] attachment,
//			TaUtilisateur taUtilisateur) {
//		
//		try {
//			/*
//			 * Corps et option du mail
//			 */		
//			TaMessageEmail taMessageEmail = new TaMessageEmail();
//			
//			Date now = new Date();
//			taMessageEmail.setDateCreation(now);
//			taMessageEmail.setDateEnvoi(now);
//			
//			taMessageEmail.setAdresseExpediteur(fromEmail);
//			taMessageEmail.setNomExpediteur(fromName);
//			
//			taMessageEmail.setSubject(subject);
//			taMessageEmail.setBodyPlain(textPart);
//			taMessageEmail.setBodyHtml(htmlPart); //on ne gère pas le contenu html pour l'instant
//			
//			/*
//			 * destinatires,cc,bcc,contact et expéditeur
//			 */
//			TaContactMessageEmail taContactMessageEmail = null;
//			//DESTINATAIRE
//			int nbDest = (emailTiersDestinataire!=null?emailTiersDestinataire.size():0)+(adressesDestinataire!=null?adressesDestinataire.size():0);
//			String[] dest = new String[nbDest];
//			int i = 0;
//			if(contactMessageEmailDestinataire!=null) {
//				for (TaContactMessageEmail c : contactMessageEmailDestinataire) {
//					if(taMessageEmail.getDestinataires()==null) {
//						taMessageEmail.setDestinataires(new HashSet<>());
//					}
//					taMessageEmail.getDestinataires().add(c);
//					c.setTaMessageEmail(taMessageEmail);
//				}
//			}
//			if(emailTiersDestinataire!=null) {
//				for (TaEmail c : emailTiersDestinataire) {
//					
//					taContactMessageEmail = new TaContactMessageEmail();
//					taContactMessageEmail.setAdresseEmail(c.getAdresseEmail());
//					taContactMessageEmail.setTaTiers(c.getTaTiers());
//					taContactMessageEmail.setTaMessageEmail(taMessageEmail);
//					if(taMessageEmail.getDestinataires()==null) {
//						taMessageEmail.setDestinataires(new HashSet<>());
//					}
//					taMessageEmail.getDestinataires().add(taContactMessageEmail);
//						
//					dest[i] = c.getAdresseEmail();
//					i++;
//				}
//			}
//			if(adressesDestinataire!=null) {
//				
//				if(taMessageEmail.getDestinataires()==null) {
//					taMessageEmail.setDestinataires(new HashSet<>());
//				}
//				for (String c : adressesDestinataire) {
//					dest[i] = c;
//					
//					taContactMessageEmail = new TaContactMessageEmail();
//					taContactMessageEmail.setAdresseEmail(c);
//					taContactMessageEmail.setTaMessageEmail(taMessageEmail);
//					taMessageEmail.getDestinataires().add(taContactMessageEmail);
//					
//					i++;
//				}
//			}
//			
//			//CC
//			int nbCc = (emailTiersCc!=null?emailTiersCc.size():0)+(adressesCc!=null?adressesCc.size():0);
//			String[] cc = new String[nbCc];
//			i = 0;
//			if(contactMessageEmailCc!=null) {
//				for (TaContactMessageEmail c : contactMessageEmailCc) {
//					if(taMessageEmail.getCc()==null) {
//						taMessageEmail.setCc(new HashSet<>());
//					}
//					taMessageEmail.getCc().add(c);
//					c.setTaMessageEmail(taMessageEmail);
//				}
//			}
//			if(emailTiersCc!=null) {
//				for (TaEmail c : emailTiersCc) {
//					
//					taContactMessageEmail = new TaContactMessageEmail();
//					taContactMessageEmail.setAdresseEmail(c.getAdresseEmail());
//					taContactMessageEmail.setTaTiers(c.getTaTiers());
//					taContactMessageEmail.setTaMessageEmail(taMessageEmail);
//					if(taMessageEmail.getCc()==null) {
//						taMessageEmail.setCc(new HashSet<>());
//					}
//					taMessageEmail.getCc().add(taContactMessageEmail);
//					
//					dest[i] = c.getAdresseEmail();
//					i++;
//				}
//			}
//			if(adressesCc!=null) {
//				
//				if(taMessageEmail.getCc()==null) {
//					taMessageEmail.setCc(new HashSet<>());
//				}
//				for (String c : adressesCc) {
//					dest[i] = c;
//					
//					taContactMessageEmail = new TaContactMessageEmail();
//					taContactMessageEmail.setAdresseEmail(c);
//					taContactMessageEmail.setTaMessageEmailCc(taMessageEmail);
//					taMessageEmail.getCc().add(taContactMessageEmail);
//					
//					i++;
//				}
//			}
//			
//			//BCC
//			int nbBcc = (emailTiersBcc!=null?emailTiersBcc.size():0)+(adressesBcc!=null?adressesBcc.size():0);
//			String[] bcc = new String[nbBcc];
//			i = 0;
//			if(contactMessageEmailBcc!=null) {
//				for (TaContactMessageEmail c : contactMessageEmailBcc) {
//					if(taMessageEmail.getBcc()==null) {
//						taMessageEmail.setBcc(new HashSet<>());
//					}
//					taMessageEmail.getBcc().add(c);
//					c.setTaMessageEmail(taMessageEmail);
//				}
//			}
//			if(emailTiersBcc!=null) {
//				for (TaEmail c : emailTiersBcc) {
//					
//					taContactMessageEmail = new TaContactMessageEmail();
//					taContactMessageEmail.setAdresseEmail(c.getAdresseEmail());
//					taContactMessageEmail.setTaTiers(c.getTaTiers());
//					taContactMessageEmail.setTaMessageEmail(taMessageEmail);
//					if(taMessageEmail.getBcc()==null) {
//						taMessageEmail.setBcc(new HashSet<>());
//					}
//					taMessageEmail.getBcc().add(taContactMessageEmail);
//					
//					dest[i] = c.getAdresseEmail();
//					i++;
//				}
//			}
//			if(adressesBcc!=null) {
//				
//				if(taMessageEmail.getBcc()==null) {
//					taMessageEmail.setBcc(new HashSet<>());
//				}
//				for (String c : adressesBcc) {
//					dest[i] = c;
//					
//					taContactMessageEmail = new TaContactMessageEmail();
//					taContactMessageEmail.setAdresseEmail(c);
//					taContactMessageEmail.setTaMessageEmailBcc(taMessageEmail);
//					taMessageEmail.getBcc().add(taContactMessageEmail);
//					
//					i++;
//				}
//			}
//			
//			/*
//			 * pièces jointes
//			 */
//			File[] pj = null;
//			if(attachment!=null && attachment.length>0) {
//				pj = new File[attachment.length];
//				for (int j = 0; j < attachment.length; j++) {
//					pj[j] = attachment[j];					
//				}
//			}
//						
//			/*
//			 * Enregistrement du mail dans l'historique
//			 */
//			taMessageEmail.setTaUtilisateur(taUtilisateur);
//			
//			if(attachment!=null && attachment.length>0) {
//				pj = new File[attachment.length];
//				for (int j = 0; j < attachment.length; j++) {
//					pj[j] = attachment[j];					
//				}
//			}
//			
//			if(attachment!=null && attachment.length>0) {
//				TaPieceJointeEmail taPieceJointeEmail = null;
//				for (int j = 0; j < attachment.length; j++) {
//					taPieceJointeEmail = new TaPieceJointeEmail();	
//					taPieceJointeEmail.setFichier(IOUtils.toByteArray(new FileInputStream(attachment[j])));
//					taPieceJointeEmail.setNomFichier(attachment[j].getName());
//					String mime = Files.probeContentType(attachment[j].toPath());
//					if(mime==null || mime.equals("")) {
//						fileTypeMap.getContentType(attachment[j]);
//					}
//					taPieceJointeEmail.setTypeMime(mime);
//					taPieceJointeEmail.setTaMessageEmail(taMessageEmail);
//					if(taMessageEmail.getPiecesJointes()==null) {
//						taMessageEmail.setPiecesJointes(new HashSet<>());
//					}
//					taMessageEmail.getPiecesJointes().add(taPieceJointeEmail);
//				}
//			}
//			
//			return taMessageEmail;
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
