package controller;

import entities.Canal;
import entities.Membre;
import entities.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import listeners.HttpSessionManager;
import model.CanalFacade;
import model.MembreFacade;
import model.MessageFacade;

@ManagedBean
@SessionScoped
public class ControllerCanal {

    @EJB
    private MessageFacade messageFacade;

    public ControllerCanal() {
    }

    @EJB
    private CanalFacade canalFacade;

    @EJB
    private MembreFacade membreFacade;

    private List<Membre> membres;

    private Canal selectedCanal;

    private List<Canal> canaux;

    private List<Message> messagesEnvoyes;

    private List<Message> messagesRecus;

    private Membre SelectedMembreFromOrderList;

    private String messageAEnvoyer;

    private Message selectedMessageFromEnvoyes;

    private Message selectedMessageFromRecus;

    private Membre membrePossesseurDeLeSessionHttp;

    private String roomNumber;

    private Membre membreARejoindrePourChatVideo;

    @PostConstruct
    public void init() {
        canaux = new ArrayList<>();
        canaux = null;
        try {
            canaux = canalFacade.findAll();

        } catch (NullPointerException e) {
        }

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        membrePossesseurDeLeSessionHttp = (Membre) httpSes.getAttribute("membre");

    }

    public List<Membre> getMembres() {
        return membres;
    }

    public void setMembres(List<Membre> membres) {
        this.membres = membres;
    }

    public Canal getSelectedCanal() {
        return selectedCanal;
    }

    public void setSelectedCanal(Canal selectedCanal) {
        this.selectedCanal = selectedCanal;
    }

    public List<Canal> getCanaux() {
        return canaux;
    }

    public void setCanaux(List<Canal> canals) {
        this.canaux = canals;
    }

    public Membre getSelectedMembreFromOrderList() {
        return SelectedMembreFromOrderList;
    }

    public void setSelectedMembreFromOrderList(Membre SelectedMembreFromOrderList) {
        this.SelectedMembreFromOrderList = SelectedMembreFromOrderList;
    }

    public String getMessageAEnvoyer() {
        return messageAEnvoyer;
    }

    public void setMessageAEnvoyer(String messageAEnvoyer) {
        this.messageAEnvoyer = messageAEnvoyer;
    }

    public List<Message> getMessagesEnvoyes() {
        return messagesEnvoyes;
    }

    public void setMessagesEnvoyes(List<Message> messagesEnvoyes) {
        this.messagesEnvoyes = messagesEnvoyes;
    }

    public List<Message> getMessagesRecus() {
        return messagesRecus;
    }

    public void setMessagesRecus(List<Message> messagesRecus) {
        this.messagesRecus = messagesRecus;
    }

    public Message getSelectedMessageFromEnvoyes() {
        return selectedMessageFromEnvoyes;
    }

    public void setSelectedMessageFromEnvoyes(Message selectedMessageFromEnvoyes) {
        this.selectedMessageFromEnvoyes = selectedMessageFromEnvoyes;
    }

    public Message getSelectedMessageFromRecus() {
        return selectedMessageFromRecus;
    }

    public void setSelectedMessageFromRecus(Message selectedMessageFromRecus) {
        this.selectedMessageFromRecus = selectedMessageFromRecus;
    }

    public Membre getMembrePossesseurDeLeSessionHttp() {
        return membrePossesseurDeLeSessionHttp;
    }

    public void setMembrePossesseurDeLeSessionHttp(Membre membrePossesseurDeLeSessionHttp) {
        this.membrePossesseurDeLeSessionHttp = membrePossesseurDeLeSessionHttp;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Membre getMembreARejoindrePourChatVideo() {
        return membreARejoindrePourChatVideo;
    }

    public void setMembreARejoindrePourChatVideo(Membre membreARejoindrePourChatVideo) {
        this.membreARejoindrePourChatVideo = membreARejoindrePourChatVideo;
    }

    public void accesALaPageDesConnectesActionListenner(ActionEvent actionevent) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().redirect("pageConnectes.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ControllerCanal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rejoindreUnCanal(ActionEvent actionevent) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        Membre membre = (Membre) httpSes.getAttribute("membre");
        membreFacade.changeIdCanalMembre(membre.getId(), selectedCanal);
        membres = new ArrayList<>();
        membres = null;
        try {
            membres = this.membreFacade.findByIdCanal(this.selectedCanal);
        } catch (NullPointerException e) {
        }
        String url = this.selectedCanal.getName() + ".xhtml";
        try {
            context.getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(ControllerCanal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rejoindreUnAutreCanal(ActionEvent actionevent) {
        membreFacade.setIdCanalMembreNull(membrePossesseurDeLeSessionHttp.getId());
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("pageChoixCanal.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void envoyerLeMessage(ActionEvent actionevent) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        Membre membre = (Membre) httpSes.getAttribute("membre");
        Message message = new Message();
        message.setContenu(this.messageAEnvoyer);
        message.setIdRecepteur(SelectedMembreFromOrderList);
        message.setIdEmetteur(membre);
        Date date = new Date();
        message.setDateWithTime(date);
        messageFacade.create(message);
        addMessage("votre message " + messageAEnvoyer + "  a été bien envoyé");
        messageAEnvoyer = "";

    }

    public void trouverMessagesEnvoyes() {
        messagesEnvoyes = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        Membre membre = (Membre) httpSes.getAttribute("membre");
        try {
            messagesEnvoyes = messageFacade.findMessagesByIdEmetteur(membre);
            System.out.println("hahouuuuuma ya soophia" + messagesEnvoyes.size());
        } catch (NullPointerException e) {
        }

    }

    public void trouverMessagesRecus() {
        messagesRecus = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        Membre membre = (Membre) httpSes.getAttribute("membre");
        try {
            messagesRecus = messageFacade.findMessagesByIdRecepteur(membre);
            System.out.println("hahouuuuuma ya soophia" + messagesRecus.size());
        } catch (NullPointerException e) {
        }
    }

    public void mettreAjourLesContatcts() {
        try {

            membres = this.membreFacade.findByIdCanal(this.selectedCanal);

        } catch (NullPointerException e) {
        }

        /*Map<String, HttpSession> sessions = HttpSessionManager.sessions;
        Set<HttpSession> set = (Set<HttpSession>) sessions.values();
        ArrayList<Membre> membresConnectes = new ArrayList<>();
        Iterator itMembres = membres.iterator();
        Iterator itSessions = set.iterator();
        while (itMembres.hasNext()) {
            while (itSessions.hasNext()) {
                Membre memSess = itSessions.next();

            }
        }*/

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void whenWeCloseTheBrowser() {
        Membre m;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        m = (Membre) httpSes.getAttribute("membre");
        membreFacade.setIdCanalMembreNull(m.getId());

    }

    public void whenChooseRoomNumber() {
        Membre m;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        m = (Membre) httpSes.getAttribute("membre");
        m.setRoomNumber(roomNumber);
        System.out.println("c'est faiiiiiiiiiiiiiiiiiit 1  " + roomNumber);
        membrePossesseurDeLeSessionHttp = m;
        System.out.println("c'est faiiiiiiiiiiiiiiiiiit 2   " + membrePossesseurDeLeSessionHttp.getRoomNumber());
        membreFacade.changeIdRoomMember(m.getId(), this.roomNumber);
        try {
            context.getExternalContext().redirect("pageChatVideo.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ControllerCanal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rejoindreLeMembrePourChatVideo() {
        Membre m;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSes = request.getSession(false);
        m = (Membre) httpSes.getAttribute("membre");
        m.setRoomNumber(membreARejoindrePourChatVideo.getRoomNumber());
        membrePossesseurDeLeSessionHttp = m;
        membreFacade.changeIdRoomMember(m.getId(), membreARejoindrePourChatVideo.getRoomNumber());
        try {
            context.getExternalContext().redirect("pageChatVideo.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ControllerCanal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void changeRoomNumberToNull() {
        try {
            membreFacade.idRoomNumberNull(membrePossesseurDeLeSessionHttp.getId());
        } catch (NullPointerException e) {
        }
    }
    
    public void quitterVideoChatPage(ActionEvent actionevent){
        changeRoomNumberToNull();
        addMessage("Merci, vous pouvez maintenant quitter cette page");
    }
    
    public void revenirSurMonCanal(){
        String url = this.selectedCanal.getName() + ".xhtml";
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(ControllerCanal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
