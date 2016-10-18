package controller;

import entities.Membre;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import model.MembreFacade;

@ManagedBean
@RequestScoped
public class RegisterController {

    @EJB
    private MembreFacade membreFacade;

    private String userNameExists = "";

    private Membre membre = new Membre();

    private Boolean femme;
    private Boolean homme;

    //constructor
    public RegisterController() {
    }

    public MembreFacade getMembreFacade() {
        return membreFacade;
    }

    public void setMembreFacade(MembreFacade membreFacade) {
        this.membreFacade = membreFacade;
    }

    public String getUserNameExists() {
        return userNameExists;
    }

    public void setUserNameExists(String userNameExists) {
        this.userNameExists = userNameExists;
    }

    //getters and setters
    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Boolean getFemme() {
        return femme;
    }

    public void setFemme(Boolean femme) {
        this.femme = femme;
    }

    public Boolean getHomme() {
        return homme;
    }

    public void setHomme(Boolean homme) {
        this.homme = homme;
    }

    public void add(ActionEvent actionevent) {
        if (membreFacade.findByUserName(this.membre.getUserName()) != null) {
            addMessageWarn("this userName already exists");
            userNameExists = "this userName already exists";

        } else {
            if (membreFacade.findByEmail(this.membre.getEmail()) != null) {
                this.addMessageWarn("this email already exists");
                userNameExists = "this email already exists";
            } else {
                if (!VerifierUsername(membre.getUserName())) {
                    addMessageWarn("votre username doit contenir au moins 6 caractères");
                } else {
                    if (!VerifierPassword(membre.getPassword(), membre.getConfirPassword())) {
                        addMessageWarn("le mot de passe doit contenir au moins 6 caractères et doit être égal à sa confirmation");
                    } else {
                        if (femme == false && homme == false) {
                            addMessageWarn("Veuillez choisir votre sex");
                        } else if (femme == true && homme == true) {
                            addMessageWarn("Vous avez droit à seul choix");
                        } else {
                            if (femme == true && homme == false) {
                                membre.setSex("femme");
                            }
                            if (femme == false && homme == true) {
                                membre.setSex("homme");
                            }

                            this.membreFacade.create(this.membre);
                            addMessageInfo("Inscription reussie");
                        }
                    }
                }

            }

        }

    }

    public void addMessageWarn(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void addMessageInfo(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public boolean VerifierUsername(String username) {

        return username.length() >= 6;

    }

    public boolean VerifierPassword(String password, String confpass) {

        if (password.equals(confpass)) {
            if (password.length() >= 6) {
                return true;
            }
        }

        return false;

    }

}
