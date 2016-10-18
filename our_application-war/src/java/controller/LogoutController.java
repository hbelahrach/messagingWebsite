package controller;

import entities.Membre;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.MembreFacade;

@ManagedBean
public class LogoutController {

    @EJB
    private MembreFacade membreFacade;

    public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        HttpSession httpSes = request.getSession(false);
        Membre membre = (Membre) httpSes.getAttribute("membre");
        Long idMembre = membre.getId();
        membreFacade.setIdCanalMembreNull(idMembre);
        membreFacade.idRoomNumberNull(idMembre);
        ec.invalidateSession();

        try {
            ExternalContext contextt = FacesContext.getCurrentInstance().getExternalContext();
            contextt.redirect("welcomePrimefaces.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logoutAndMessage() {
        try {
            this.logout();
            addMessage("Success", "vous êtes déconnecté");
        } catch (IOException ex) {
        }

    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
