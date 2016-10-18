/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sophia
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByContenu", query = "SELECT m FROM Message m WHERE m.contenu = :contenu"),
    @NamedQuery(name = "Message.findByDateWithTime", query = "SELECT m FROM Message m WHERE m.dateWithTime = :dateWithTime"),
    @NamedQuery(name = "Message.findByIdEmetteur", query = "SELECT m FROM Message m WHERE m.idEmetteur = :idEmetteur"),
    @NamedQuery(name = "Message.findByIdRecepteur", query = "SELECT m FROM Message m WHERE m.idRecepteur = :idRecepteur")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Contenu")
    private String contenu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_with_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateWithTime;
    @JoinColumn(name = "ID_RECEPTEUR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Membre idRecepteur;
    @JoinColumn(name = "ID_EMETTEUR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Membre idEmetteur;

    public Message() {
    }

    public Message(Long id) {
        this.id = id;
    }

    public Message(Long id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }



    public Date getDateWithTime() {
        return dateWithTime;
    }

    public void setDateWithTime(Date dateWithTime) {
        this.dateWithTime = dateWithTime;
    }

    public Membre getIdRecepteur() {
        return idRecepteur;
    }

    public void setIdRecepteur(Membre idRecepteur) {
        this.idRecepteur = idRecepteur;
    }

    public Membre getIdEmetteur() {
        return idEmetteur;
    }

    public void setIdEmetteur(Membre idEmetteur) {
        this.idEmetteur = idEmetteur;
    }
    
    public String changeDateFormat() {

        String nouvelleDate;
        Date date = this.dateWithTime;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d MMMM yyyy à HH:mm"); 
        nouvelleDate = dateFormatter.format(date);
        return "Le " + nouvelleDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Message[ id=" + id + " ]";
    }

}
