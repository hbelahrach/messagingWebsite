package model;

import entities.Canal;
import entities.Membre;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MembreFacade extends AbstractFacade<Membre> {

    @PersistenceContext(unitName = "our_application-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MembreFacade() {
        super(Membre.class);
    }

    public Membre findByUserNameAndPass(String username, String password) {

        Query query = em.createNamedQuery("Membre.control").setParameter("userName", username).setParameter("password", password);

        try {
            return (Membre) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return null;

    }

    public Membre findByUserName(String username) {
        Query query = em.createNamedQuery("Membre.findByUserName").setParameter("userName", username);

        try {
            return (Membre) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return null;
    }

    public Membre findByEmail(String email) {
        Query query = em.createNamedQuery("Membre.findByEmail").setParameter("email", email);

        try {
            return (Membre) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return null;
    }

    public Membre find(Long id) {
        return em.find(Membre.class, id);
    }

    public ArrayList findMembres() {
        try {
            return (ArrayList) findAll();
        } catch (Exception e) {

            return null;
        }
    }

    public ArrayList findAllMembresUserNames() throws NullPointerException {
        ArrayList<String> listeNoms = new ArrayList<>();
        listeNoms.add("looool");
        /*Query query = em.createNamedQuery("findAllMembresUserNames");
         
         try {
         return  (ArrayList) query.getSingleResult();
         } catch (NoResultException e) {
         return null;
         }*/

        ArrayList<Membre> maliste;
        maliste = this.findMembres();
        Iterator it = maliste.iterator();
        Membre m;
        String name;
        while (it.hasNext()) {
            m = (Membre) it.next();
            name = m.getUserName();
            listeNoms.add(name);

        }
        return listeNoms;
    }

    public List<Membre> findByIdCanal(Canal canal) {
        List<Membre> membres = new ArrayList();
        Query query = em.createNamedQuery("Membre.findByIdCanal").setParameter("idCanal", canal);
        try {
            membres = query.getResultList();
            System.out.println("siiiiiiiiiiiiiiiiiiiiiiiiiiize is  " + membres.size());
        } catch (NullPointerException e) {

        }

        return membres;

    }

    public void changeIdCanalMembre(Long idMembre, Canal canal) {
        Membre m = em.find(Membre.class, idMembre);
        m.setIDcanal(canal);

    }

    public void setIdCanalMembreNull(Long idMembre) {
        Membre m = em.find(Membre.class, idMembre);
        m.setIDcanal(null);

    }

    public void changeIdRoomMember(Long idMembre, String numberRoom) {
        Membre m = em.find(Membre.class, idMembre);
        m.setRoomNumber(numberRoom);

    }

    public void idRoomNumberNull(Long idMembre) {
        Membre m = em.find(Membre.class, idMembre);
        m.setRoomNumber(null);
    }

}
