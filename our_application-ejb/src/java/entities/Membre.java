/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sophia
 */
@Entity
@Table(name = "membre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membre.control", query = "SELECT m FROM Membre m WHERE m.userName = :userName and m.password = :password"),
    @NamedQuery(name = "Membre.findAll", query = "SELECT m FROM Membre m"),
    @NamedQuery(name = "Membre.findById", query = "SELECT m FROM Membre m WHERE m.id = :id"),
    @NamedQuery(name = "Membre.findByUserName", query = "SELECT m FROM Membre m WHERE m.userName = :userName"),
    @NamedQuery(name = "Membre.findByEmail", query = "SELECT m FROM Membre m WHERE m.email = :email"),
    @NamedQuery(name = "Membre.findByPassword", query = "SELECT m FROM Membre m WHERE m.password = :password"),
    @NamedQuery(name = "Membre.findByConfirPassword", query = "SELECT m FROM Membre m WHERE m.confirPassword = :confirPassword"),
    @NamedQuery(name = "Membre.findBySex", query = "SELECT m FROM Membre m WHERE m.sex = :sex"),
    @NamedQuery(name = "Membre.findByIdCanal", query = "SELECT m FROM Membre m WHERE m.iDcanal = :idCanal")})
public class Membre implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "user_name")
    private String userName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "confir_password")
    private String confirPassword;
    @Basic(optional = false)
    @Size(min = 1, max = 10)
    @Column(name = "sex")
    private String sex;
    @JoinColumn(name = "ID_canal", referencedColumnName = "ID")
    @ManyToOne
    private Canal iDcanal;
    @Size(max = 30)
    @Column(name = "room_number")
    private String roomNumber;

    public Membre() {
    }

    public Membre(Long id) {
        this.id = id;
    }

    public Membre(Long id, String userName, String email, String password, String sex) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirPassword() {
        return confirPassword;
    }

    public void setConfirPassword(String confirPassword) {
        this.confirPassword = confirPassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Canal getIDcanal() {
        return iDcanal;
    }

    public void setIDcanal(Canal iDcanal) {
        this.iDcanal = iDcanal;
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
        if (!(object instanceof Membre)) {
            return false;
        }
        Membre other = (Membre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Membre[ id=" + id + " ]";
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String rommNumber) {
        this.roomNumber = rommNumber;
    }

    public Canal getiDcanal() {
        return iDcanal;
    }

    public void setiDcanal(Canal iDcanal) {
        this.iDcanal = iDcanal;
    }

   

}
