package fr.ylyade.courtage.app;

import java.io.Serializable;
import java.util.PropertyResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@ViewScoped
public class BundleProducer implements Serializable {

	private static final long serialVersionUID = 141568014564989134L;

	@Produces @Named("aa")
    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{c_langue}", PropertyResourceBundle.class);
    }

}