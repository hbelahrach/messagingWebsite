/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Canal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sophia
 */
@Stateless
public class CanalFacade extends AbstractFacade<Canal> {
    @PersistenceContext(unitName = "our_application-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CanalFacade() {
        super(Canal.class);
    }
    
}
