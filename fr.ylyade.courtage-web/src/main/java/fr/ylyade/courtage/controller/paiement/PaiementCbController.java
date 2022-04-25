package fr.ylyade.courtage.controller.paiement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import fr.ylyade.courtage.app.AbstractController;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaReglementPrestation;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.service.TaTReglementService;
import fr.ylyade.courtage.service.interfaces.remote.ILgrStripe;
import fr.ylyade.courtage.service.interfaces.remote.ITaAttestationNominativeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTReglementServiceRemote;

@Named
@ViewScoped  
public class PaiementCbController extends AbstractController implements Serializable {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8342835843291195038L;
	private @EJB ILgrStripe lgrStripe;
	private @EJB ITaTReglementServiceRemote taTReglementService;
	private @EJB ITaAttestationNominativeServiceRemote taAttestationNominativeService;
	private TaEcheance taEcheanceAPayer;
	private TaAttestationNominative taAttestationNominativeAPayer;

	private int[] listeMoisCB = new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
	private int[] listeAnneeCB = new int[]{2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030};

	private BigDecimal totalHT;
	private BigDecimal totalTTC;
	private BigDecimal totalTVA;
	
	private String nomCarte;
	private int moisCarte;
	private int anneeCarte;
	private String cryptoCarte;
	private String numCarte;
	private String typeCarte;
	
	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		taEcheanceAPayer = (TaEcheance) sessionMap.get("echeance");
		taAttestationNominativeAPayer = (TaAttestationNominative) sessionMap.get("attest");
		sessionMap.remove("echeance");
		sessionMap.remove("attest");
		if(taEcheanceAPayer!=null) {
			if(taEcheanceAPayer.getMontantEcheancePlusFrais()!=null) {
				totalTTC = taEcheanceAPayer.getMontantEcheancePlusFrais();
			}else {
				totalTTC = taEcheanceAPayer.getMontantEcheance();
			}
			
		}
		
		if(taAttestationNominativeAPayer!=null) {
			if(taAttestationNominativeAPayer.getTaTarifPrestation()!=null) {
				totalTTC = taAttestationNominativeAPayer.getTaTarifPrestation().getMontantPrestation();
			}
			
		}
	}
	
	public void actPayer(ActionEvent actionEvent) {
		System.out.println("PaiementCbController.actPayer()");
		
		String descriptionClient = "Ylyade - "+taEcheanceAPayer.getTaDossierRcd().getNumDossierPolice()+"/"+taEcheanceAPayer.getIdEcheance();
		
		String idPaiement = lgrStripe.payer(
					totalTTC, 
					getNumCarte(), 
					getMoisCarte(), 
					getAnneeCarte(), 
					getCryptoCarte(), 
					getNomCarte(), 
					descriptionClient
					);
		
		if(idPaiement!=null) { //Paiement OK
			
			Date now = new Date();
			String nowString;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
			nowString = dateFormat.format(now);
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
			
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			try {
				System.out.println("******PAIEMENT CB OK*********** PAIEMENT CB OK***********PAIEMENT CB OK*********** DOSSIER id:"+taEcheanceAPayer.getTaDossierRcd().getIdDossierRcd()+" numPolice : "+taEcheanceAPayer.getTaDossierRcd().getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", Courtier du dossier :"+taEcheanceAPayer.getTaDossierRcd().getTaCourtier().getCodeCourtier()+", Pour l'assuré "+taEcheanceAPayer.getTaDossierRcd().getTaAssure().getCodeAssure()+" "+taEcheanceAPayer.getTaDossierRcd().getTaAssure().getRaisonSociale()+"****************");

			} catch (Exception e) {
				System.out.println("******PAIEMENT CB OK*********** PAIEMENT CB OK***********PAIEMENT CB OK***********");
			}
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			
			
			
			TaReglementAssurance reg = new TaReglementAssurance();
			
			reg.setRefExtReglement(idPaiement);
			reg.setDateReglement(new Date());
			reg.setMontant(totalTTC);
			reg.setTaTReglement(taEcheanceAPayer.getTaTReglement());
			reg.setTaEcheance(taEcheanceAPayer);
//			reg.setMontant(taEcheanceAPayer.getMontantEcheance());
			reg.setValidationAutomatiquePaiement(true);
			taEcheanceAPayer.setTaReglementAssurance(reg);
			
			RequestContext.getCurrentInstance().closeDialog(taEcheanceAPayer);
		}
		
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void actPayerAttestationNominative(ActionEvent actionEvent) {
		System.out.println("PaiementCbController.actPayerAttestationNominative()");
		
		String descriptionClient = "Ylyade - "+taAttestationNominativeAPayer.getTaDossierRcd().getNumDossierPolice()+"/"+taAttestationNominativeAPayer.getIdAttestationNominative();
		
		String idPaiement = lgrStripe.payer(
					totalTTC, 
					getNumCarte(), 
					getMoisCarte(), 
					getAnneeCarte(), 
					getCryptoCarte(), 
					getNomCarte(), 
					descriptionClient
					);
		
		if(idPaiement!=null) { //Paiement OK
//			TaReglementPrestation reg = new TaReglementPrestation();
			TaReglementPrestation reg = taAttestationNominativeAPayer.getTaReglementPrestation();
			
			reg.setRefExtReglement(idPaiement);
			reg.setDateReglement(new Date());
			reg.setMontant(totalTTC);
			reg.setValidationAutomatiquePaiement(true);
			

			taAttestationNominativeAPayer.setMontantPaye(reg.getMontant());
			
			try {
				reg.setTaTReglement(taTReglementService.findByCode(TaTReglement.CB));
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			taAttestationNominativeAPayer.setTaReglementPrestation(reg);
			taAttestationNominativeAPayer.setMontantPaye(reg.getMontant());
			taAttestationNominativeService.merge(taAttestationNominativeAPayer);
//			listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
			
			RequestContext.getCurrentInstance().closeDialog(taAttestationNominativeAPayer);
		}
		
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void actAnnuler(ActionEvent actionEvent) {
		System.out.println("PaiementCbController.actAnnuler()");
		
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public int[] getListeMoisCB() {
		return listeMoisCB;
	}
	public void setListeMoisCB(int[] listeMoisCB) {
		this.listeMoisCB = listeMoisCB;
	}
	public int[] getListeAnneeCB() {
		return listeAnneeCB;
	}
	public void setListeAnneeCB(int[] listeAnneeCB) {
		this.listeAnneeCB = listeAnneeCB;
	}
	public BigDecimal getTotalHT() {
		return totalHT;
	}
	public void setTotalHT(BigDecimal totalHT) {
		this.totalHT = totalHT;
	}
	public BigDecimal getTotalTTC() {
		return totalTTC;
	}
	public void setTotalTTC(BigDecimal totalTTC) {
		this.totalTTC = totalTTC;
	}
	public BigDecimal getTotalTVA() {
		return totalTVA;
	}
	public void setTotalTVA(BigDecimal totalTVA) {
		this.totalTVA = totalTVA;
	}
	public String getNomCarte() {
		return nomCarte;
	}
	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}
	public int getMoisCarte() {
		return moisCarte;
	}
	public void setMoisCarte(int moisCarte) {
		this.moisCarte = moisCarte;
	}
	public int getAnneeCarte() {
		return anneeCarte;
	}
	public void setAnneeCarte(int anneeCarte) {
		this.anneeCarte = anneeCarte;
	}
	public String getCryptoCarte() {
		return cryptoCarte;
	}
	public void setCryptoCarte(String cryptoCarte) {
		this.cryptoCarte = cryptoCarte;
	}
	public String getNumCarte() {
		return numCarte;
	}
	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}
	public String getTypeCarte() {
		return typeCarte;
	}
	public void setTypeCarte(String typeCarte) {
		this.typeCarte = typeCarte;
	}

	public TaAttestationNominative getTaAttestationNominativeAPayer() {
		return taAttestationNominativeAPayer;
	}

	public void setTaAttestationNominativeAPayer(TaAttestationNominative taAttestationNominativeAPayer) {
		this.taAttestationNominativeAPayer = taAttestationNominativeAPayer;
	}
}  
