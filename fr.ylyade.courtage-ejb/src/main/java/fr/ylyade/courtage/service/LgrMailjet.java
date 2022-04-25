package fr.ylyade.courtage.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Contact;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Emailv31;
import com.mailjet.client.resource.Sender;
import com.mailjet.client.resource.Template;

public class LgrMailjet {

/*
 * https://mvnrepository.com/artifact/com.mailjet/mailjet-client/4.1.1
 */

	private String MJ_APIKEY_PUBLIC = "XXXXXX_CLE_MAILJET_XXXXXX";
	private String MJ_APIKEY_PRIVATE = "XXXXXX_CLE_MAILJET_XXXXXX";
	
	private final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();  
	
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, File[] attachment) {
		send(fromEmail, fromName, subject, textPart, htmlPart, destinataires, null, null, attachment);
	}


	/*
	 * Version 3.0
	 */
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, String[] cc, String[] bcc, File[] attachment) {

		try {
			MailjetClient client = new MailjetClient(MJ_APIKEY_PUBLIC, MJ_APIKEY_PRIVATE);
			
			MailjetRequest sender;
			MailjetResponse r;

			sender = new MailjetRequest(Sender.resource)
			            .property(Sender.EMAIL, fromEmail);

			r = client.post(sender);
			
			String base64 = null;
			
			JSONArray recipients = new JSONArray();
			for (String d : destinataires) {
				recipients.put(new JSONObject().put(Contact.EMAIL, d));
			}
			
			MailjetRequest request;
			MailjetResponse response;
			request = new MailjetRequest(Email.resource)
					.property(Email.FROMEMAIL, fromEmail)
					.property(Email.FROMNAME, fromName)
					.property(Email.SUBJECT, subject)
//					.property(Email.TEXTPART, textPart)
//					.property(Email.HTMLPART, htmlPart)
					
					.property(Email.MJTEMPLATELANGUAGE, true)
					.property(Email.MJTEMPLATEID, "1")
					
					.property(Email.RECIPIENTS, recipients) // RECIPIENTS et TO sont différents voir la doc de l'api https://dev.mailjet.com/guides/#send-api-v3-1-beta
//					.property(Email.TO,recipients)
					
					;
			if(cc!=null) {
				JSONArray ccjson = new JSONArray();
//				String ccjson = "";
				boolean premier = true;
				for (String adr : cc) {
					ccjson.put(new JSONObject().put(Contact.EMAIL, adr));
//					if(premier) {
//						ccjson += adr;
//						premier = false;
//					} else {
//						ccjson += ", "+adr;
//					}
				}
				request.property(Email.CC, ccjson);
			}
			
			if(bcc!=null) {
//				String bccjson = "";
				JSONArray bccjson = new JSONArray();
				boolean premier = true;
				for (String adr : bcc) {
					bccjson.put(new JSONObject().put(Contact.EMAIL, adr));
//					if(premier) {
//						bccjson += adr;
//						premier = false;
//					} else {
//						bccjson += ", "+adr;
//					}
				}
				request.property(Email.BCC, bccjson);
			}
			
			if(attachment!=null) {
				JSONArray piecesJointes = new JSONArray();
				for (File file : attachment) {
					base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(Paths.get(file.getPath())));
					String mime = Files.probeContentType(file.toPath());
					if(mime==null || mime.equals("")) {
						fileTypeMap.getContentType(file);
					}
					piecesJointes.put(new JSONObject()
							//.put("Content-type", "application/pdf")
							.put("Content-type", mime)
							.put("Filename", file.getName())
							.put("content", base64));
				}
				
				request.property(Email.ATTACHMENTS, piecesJointes);
			}
			
			response = client.post(request);
			System.out.println(response.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * v3.1
	 */
	public void send_v3_1(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, String[] cc, String[] bcc, File[] attachment, String templateId, Map<String, String> variables) {

		try {
			MailjetClient client;
			MailjetRequest request;
			MailjetResponse response;
			String base64 = null;
			
			client = new MailjetClient(MJ_APIKEY_PUBLIC, MJ_APIKEY_PRIVATE, new ClientOptions("v3.1"));
			
			JSONObject message = new JSONObject();
			
			message.put(Emailv31.Message.FROM, new JSONObject()
							.put(Emailv31.Message.EMAIL, fromEmail)
							.put(Emailv31.Message.NAME, fromName));
			
			JSONObject variablesjson = new JSONObject();
			if(variables!=null) {
				for (String v : variables.keySet()) {
					variablesjson.put(v,variables.get(v));
	//				message.put(Emailv31.Message.VARIABLES, new JSONObject().put(v,variables.get(v)));
				}
			}
			
			message.put(Emailv31.Message.SUBJECT, subject)
//					.put(Emailv31.Message.TEXTPART, textPart)
					.put(Emailv31.Message.HTMLPART, htmlPart);
					
			
			if(variables!=null) {
				message.put(Emailv31.Message.VARIABLES, variablesjson);
			}
			
			if(templateId!=null) {
				message.put(Emailv31.Message.TEMPLATEID, Integer.parseInt(templateId))
				.put(Emailv31.Message.TEMPLATELANGUAGE, true);
			}
			
			
			JSONArray recipients = new JSONArray();
			for (String d : destinataires) {
				recipients.put(new JSONObject().put(Emailv31.Message.EMAIL, d));
			}
			message.put(Emailv31.Message.TO, recipients);
			
			if(cc!=null) {
				JSONArray ccjson = new JSONArray();
				for (String adr : cc) {
					ccjson.put(new JSONObject().put(Emailv31.Message.EMAIL, adr));
				}
				message.put(Emailv31.Message.CC, ccjson);
			}
			
			if(bcc!=null) {
				JSONArray bccjson = new JSONArray();
				for (String adr : bcc) {
					bccjson.put(new JSONObject().put(Emailv31.Message.EMAIL, adr));
				}
				message.put(Emailv31.Message.BCC, bccjson);
			}
					
			if(attachment!=null) {
				JSONArray piecesJointes = new JSONArray();
				for (File file : attachment) {
					base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(Paths.get(file.getPath())));
					String mime = Files.probeContentType(file.toPath());
					if(mime==null || mime.equals("")) {
						fileTypeMap.getContentType(file);
					}
					piecesJointes.put(new JSONObject()
							.put("ContentType", "application/pdf")
							//.put("ContentType", mime)
							.put("Filename", file.getName())
							.put("Base64Content", base64));
				}
				message.put(Emailv31.Message.ATTACHMENTS, piecesJointes);
			}
				
			request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));
//			request = new MailjetRequest(Template.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));
			
//			response = client.get(request);
			
			response = client.post(request);
			System.out.println(response.getStatus());
			System.out.println(response.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
