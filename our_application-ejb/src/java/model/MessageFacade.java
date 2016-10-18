/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Membre;
import entities.Message;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sophia
 */
@Stateless
public class MessageFacade extends AbstractFacade<Message> {
    @PersistenceContext(unitName = "our_application-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessageFacade() {
        super(Message.class);
    }
    
    public List<Message> findMessagesByIdEmetteur(Membre idEmetteur){
        List<Message> messagesEnvoyes = new ArrayList<>();
        Query query = em.createNamedQuery("Message.findByIdEmetteur").setParameter("idEmetteur", idEmetteur);

        try {
            messagesEnvoyes= query.getResultList();
         } catch (NoResultException e) {
           
        }
        return messagesEnvoyes;
    }
    
    public List<Message> findMessagesByIdRecepteur(Membre idRecepteur){
        List<Message> messagesRecus = new ArrayList<>();
        Query query = em.createNamedQuery("Message.findByIdRecepteur").setParameter("idRecepteur", idRecepteur);

        try {
            messagesRecus= query.getResultList();
         } catch (NoResultException e) {
           
        }
        return messagesRecus;
    }
    
}
