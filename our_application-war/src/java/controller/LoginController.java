package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.MembreFacade;

@ManagedBean
@SessionScoped
public class LoginController {

    @EJB
    private MembreFacade membreFacade;

    private String userName;
    private String password;
    private boolean isConnected = false;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public LoginController() {
    }

    public void methodeSeConnecter(ActionEvent actionEvent) {
        if (!isConnected) {
            if ((this.membreFacade.findByUserNameAndPass(userName, password) != null)) {
                isConnected = true;
                Long idMembre = this.membreFacade.findByUserName(userName).getId();
                membreFacade.setIdCanalMembreNull(idMembre);
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSes = request.getSession(false);
                httpSes.setAttribute("membre", this.membreFacade.findByUserName(userName));
                httpSes.setAttribute("isConnected", isConnected);

                try {
                    ExternalContext contextt = FacesContext.getCurrentInstance().getExternalContext();
                    contextt.redirect("pageChoixCanal.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                addMessageError("Vous n'êtes pas inscrit, veuillez vous inscrire svp");

            }
        } 
    }

    public void addMessageError(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

}
