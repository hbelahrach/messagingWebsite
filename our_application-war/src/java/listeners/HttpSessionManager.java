package listeners;

import entities.Membre;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.MembreFacade;

@ManagedBean
@ApplicationScoped
public class HttpSessionManager implements HttpSessionListener {

    public static final Map<String, HttpSession> sessions = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        sessions.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        Membre m = (Membre) event.getSession().getAttribute("membre");
        //membreFacade.setIdCanalMembreNull(m.getId());
        //sessions.remove(event.getSession().getId());

    }

    public static HttpSession find(String sessionId) {
        return sessions.get(sessionId);
    }

    private MembreFacade lookupMembreFacadeBean() {
        try {
            Context c = new InitialContext();
            return (MembreFacade) c.lookup("java:global/our_application/our_application-ejb/MembreFacade!model.MembreFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
