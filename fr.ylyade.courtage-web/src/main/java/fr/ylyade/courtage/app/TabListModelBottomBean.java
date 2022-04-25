package fr.ylyade.courtage.app;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("tabListModelBottomBean")
//@ViewScoped
@SessionScoped
public class TabListModelBottomBean extends TabListModelBean implements Serializable{

	private static final long serialVersionUID = 6794924723659242849L;

}
