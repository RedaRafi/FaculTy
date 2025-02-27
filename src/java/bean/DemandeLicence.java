/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author HP
 */
@Entity
public class DemandeLicence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Etudiant etudiant;
    @OneToMany(mappedBy = "demandeLicence")
    private List<DemandeLicenceItem> demandeLicenceItems;
    @OneToOne
    private AnneUniversitaire anneUniversitaire;

    public DemandeLicence() {
    }

    public AnneUniversitaire getAnneUniversitaire() {
        return anneUniversitaire;
    }

    public void setAnneUniversitaire(AnneUniversitaire anneUniversitaire) {
        this.anneUniversitaire = anneUniversitaire;
    }
    

    public List<DemandeLicenceItem> getDemandeLicenceItems() {
        return demandeLicenceItems;
    }

    public void setDemandeLicenceItems(List<DemandeLicenceItem> demandeLicenceItems) {
        this.demandeLicenceItems = demandeLicenceItems;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof DemandeLicence)) {
            return false;
        }
        DemandeLicence other = (DemandeLicence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.DemandeLicence[ id=" + id + " ]";
    }

}
