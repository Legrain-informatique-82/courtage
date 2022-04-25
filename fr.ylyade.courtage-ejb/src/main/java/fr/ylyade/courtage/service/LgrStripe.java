package fr.ylyade.courtage.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.Stateless;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import fr.ylyade.courtage.service.interfaces.remote.ILgrStripe;


@Stateless
public class LgrStripe implements ILgrStripe {
	
	private boolean modeTest = false;
	
	private void initCleStripe() throws Exception {
		
		Properties props = new Properties(); 
		String propertiesFileName = "ylyade.properties";  
		String path = System.getProperty("jboss.server.config.dir")+"/"+propertiesFileName;  

		String ip = null;
		if(new File(path).exists()) { 
			File f = new File(path);
			props.load(new FileInputStream(f));  
			ip = props.getProperty("ip");
		}
		
		//92.243.2.185 -- serveur PROD intranet.ylyade.com
		if(ip!=null && !ip.equals("") 
				&& !ip.equals("127.0.0.1")
				&& !ip.equals("xx.xx.xx.xx") //serveur de test DEV-internet
				&& !ip.equals("xx.xx.xx.xx") //serveur de test PPROD-internet
				&& !ip.equals("xx.xx.xx.xx") //serveur de test PROD-internet
				) { //on est à prioris bien sur internet et pas sur un poste de développement
			Stripe.apiKey = "XXXXXX_CLE_STRIPE_XXXXXX"; //clé compte prod ylyade
		} else {	
			modeTest = true;
			Stripe.apiKey = "XXXXXX_CLE_STRIPE_XXXXXX"; //clé compte test ylyade
		}

	}
	
	@Override
	public String payer(BigDecimal montant, String numeroCarte, int moisCarte, int anneeCarte, String cryptogrammeCarte, String description) throws EJBException{
		return payer(montant, numeroCarte,moisCarte,anneeCarte,cryptogrammeCarte,null,description);
	}
	
	@Override
	public String payer(BigDecimal montant, String numeroCarte, int moisCarte, int anneeCarte, String cryptogrammeCarte, String nomPorteurCarte, String description) throws EJBException{
		try {
			initCleStripe();
			
			if(modeTest) {
				System.out.println("** LgrStripe ** mode TEST activé, utilisation d'information de paiement de TEST.");
				montant = new BigDecimal(2000);
				numeroCarte = "5555555555554444";
				moisCarte = 8;
				anneeCarte = 2020;
				cryptogrammeCarte = "314";
				nomPorteurCarte = "YLYADE - mode TEST";
				description = "Paiement Stripe Ylayde - mode TEST";
			}
			
			int amount = montant.multiply(BigDecimal.valueOf(100)).intValue();
			
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", amount);
			chargeParams.put("currency", "eur");
			chargeParams.put("description", description);
			
			Map<String, Object> sourceParams = new HashMap<String, Object>();
			sourceParams.put("number", numeroCarte);
			sourceParams.put("exp_month", moisCarte);
			sourceParams.put("exp_year", anneeCarte);
			sourceParams.put("cvc", cryptogrammeCarte);
			sourceParams.put("name", nomPorteurCarte);
			
			chargeParams.put("source", sourceParams);
			
		
			
			System.out.println("LgrStripe ** paiment Stripe - montant : "+amount);
			Charge c = Charge.create(chargeParams);
			
			if(!c.getStatus().equals("succeeded")) {
				throw new EJBException("Une erreur est survenue, rééssayez ultérieurement");
			}
			if(!c.getPaid()) {
				throw new EJBException("Une erreur est survenue, rééssayez ultérieurement");
			}

			return c.getId();
		} catch (AuthenticationException e) {
			// Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
			e.printStackTrace();
			throw new EJBException("Une erreur est survenue, rééssayez ultérieurement",e);
		} catch (InvalidRequestException e) {
			// Invalid parameters were supplied to Stripe's API
			e.printStackTrace();
			throw new EJBException("Une erreur est survenue, rééssayez ultérieurement",e);
		} catch (APIConnectionException e) {
			// Network communication with Stripe failed
			e.printStackTrace();
			throw new EJBException("Une erreur est survenue, rééssayez ultérieurement",e);
		} catch (CardException e) {
			// Since it's a decline, \Stripe\Error\Card will be caught
			if(e.getCode()!=null && e.getCode().equals("incorrect_cvc")) {
				throw new EJBException("le code de sécurité est incorrect",e);
			} else if(e.getCode()!=null && e.getCode().equals("expired_card")) {
				throw new EJBException("La carte a expiré",e);
			} else if(e.getCode()!=null && e.getCode().equals("invalid_cvc")) {
				throw new EJBException("Le code de sécurité de la carte est invalide",e);
			} else if(e.getCode()!=null && e.getCode().equals("invalid_expiry_month")) {
				throw new EJBException("Le mois d'expiration de la carte est invalide",e);
			} else if(e.getCode()!=null && e.getCode().equals("invalid_expiry_year")) {
				throw new EJBException("L'année d'expiration de la carte est invalide",e);
			} else if(e.getCode()!=null && (e.getCode().equals("invalid_number") || e.getCode().equals("incorrect_number"))) {
				throw new EJBException("Le numéro de carte saisie est invalide",e);
			} else {
				e.printStackTrace();
				throw new EJBException("Une erreur est survenue, rééssayez ultérieurement",e);
			}
		} catch (APIException e) {
			e.printStackTrace();
			throw new EJBException("Une erreur est survenue, rééssayez ultérieurement",e);
		} catch (Exception e) {
			// Something else happened, completely unrelated to Stripe
			e.printStackTrace();
			throw new EJBException("Une erreur est survenue, rééssayez ultérieurement",e);
		}
	}

	public void test() {
		try {
			
			initCleStripe();
		    
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", 2000);
			chargeParams.put("currency", "eur");
			Map<String, Object> sourceParams = new HashMap<String, Object>();
			sourceParams.put("number", "5555555555554444");
			sourceParams.put("exp_month", 8);
			sourceParams.put("exp_year", 2020);
			sourceParams.put("cvc", "314");
			chargeParams.put("source", sourceParams);
			chargeParams.put("description", "Charge for emma.brown@example.com");
	
		
			Charge.create(chargeParams);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Override
	public Charge remontePaiement(String idPaiement) {
	
		try {
			initCleStripe();
			return Charge.retrieve(idPaiement);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * api.gwi-hosting.fr/src/Legrain/ApiBundle/Services/GwiHostingService.php
	 * 
	 * 
	 * 
	     case 'card':
                // Si gestionnaire ou si l'agence ne facture pas
                if ($userConnected->getParent() == null || $userConnected->getAgency()->getFacturationBylegrain()) {

                    // On loade l'agence Legrain (1)
                    $agency = $agencyRepository->find(1);
                } else {
                    $agency = $userConnected->getAgency();
                }
                if ($agency->getStripeKey() == null) throw new \SoapFault('error', 'Aucun compte stripe n\'est parametré');
                $description = 'Rechargement compte pré payé';
                // Appel stripe ( clef publique à parametrer)
                // \Stripe\Stripe::setApiKey('sk_test_gSTXDQxFFZiiBdKOGlrcLZWh');
                try {
                    \Stripe\Stripe::setApiKey($agency->getStripeKey());
                    $myCard = array('number' => $cardNumber, 'exp_month' => $cardExpirationMonth, 'exp_year' => $cardExpirationYear, 'cvc' => $cvc, 'name' => $cardFullName);
                    // Amount en centime
                    $charge = \Stripe\Charge::create(array('card' => $myCard, 'amount' => ($amount * 100), 'currency' => 'eur'));
                } catch (\Stripe\Error\Card $e) {
                    // Since it's a decline, \Stripe\Error\Card will be caught
                    $body = $e->getJsonBody();
                    $err = $body['error'];
                    switch ($err['code']) {

                        case 'incorrect_cvc':
                            $error = "le code de sécurité est incorrect";
                            break;
                        case 'expired_card':
                            $error = "la carte a expiré";
                            break;
                        case 'invalid_cvc':
                            $error = "Le code de sécurité de la carte est invalide";
                            break;
                        case 'invalid_expiry_month':
                            $error = "Le mois d'expiration de la carte est invalide";
                            break;
                        case 'invalid_expiry_year':
                            $error = "L'année d'expiration de la carte est invalide";
                            break;
                        case 'invalid_number':
                        case 'incorrect_number':
                            $error = 'Le numéro de carte saisie est invalide';
                            break;
                        default:
                            $error = 'Une erreur est survenue, rééssayez ultérieurement';
                            break;
                    }
                    throw new \SoapFault('server', $error);
                } catch (\Stripe\Error\InvalidRequest $e) {
                    // Invalid parameters were supplied to Stripe's API
                    throw new \SoapFault('server', 'Une erreur est survenue, merci de contacter votre agence');

                } catch (\Stripe\Error\Authentication $e) {
                    // Authentication with Stripe's API failed
                    // (maybe you changed API keys recently)
                    throw new \SoapFault('server', 'Une erreur est survenue, merci de contacter votre agence');

                } catch (\Stripe\Error\ApiConnection $e) {
                    // Network communication with Stripe failed
                    throw new \SoapFault('server', 'Une erreur est survenue, merci de contacter votre agence');

                } catch (\Stripe\Error\Base $e) {
                    // Display a very generic error to the user, and maybe send
                    // yourself an email
                    throw new \SoapFault('server', 'Une erreur est survenue, merci de contacter votre agence');

                } catch (\Exception $e) {
                    // Something else happened, completely unrelated to Stripe
                    throw new \SoapFault('server', 'Une erreur est survenue, merci de contacter votre agence');

                }
                if ($charge->status != "succeeded") throw new \SoapFault('error', 'Erreur au moment du paiement');
                if (!$charge->paid) throw new \SoapFault('error', 'Erreur au moment du paiement');
                $idTransactionExterne = $charge->id;
	 */
}
