package fr.ylyade.courtage.service.interfaces.remote;

import java.io.File;
import java.util.Map;

public interface ITaLgrMailjetServiceRemote {
	
//	public void send(String fromName, String subject, String textPart, String[] destinataires);
	
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, File[] attachment);
	
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, String[] cc, String[] bcc, File[] attachment);
	
	public void send(String fromEmail, String fromName, String subject, String textPart, String htmlPart, String[] destinataires, String[] cc, String[] bcc, File[] attachment, String templateId, Map<String, String> variables);

}
