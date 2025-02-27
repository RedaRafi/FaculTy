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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author RedaRafi
 */
@Entity
public class PvNoteSemestreItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private NoteSemestre noteSemestre;

    @ManyToOne
    private Pv pv;

    @OneToMany(mappedBy = "pvNoteSemestreItem")
    private List<PvNoteModulaireItem> pvNoteModulaireItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NoteSemestre getNoteSemestre() {
        return noteSemestre;
    }

    public void setNoteSemestre(NoteSemestre noteSemestre) {
        this.noteSemestre = noteSemestre;
    }

    public Pv getPv() {
        return pv;
    }

    public void setPv(Pv pv) {
        this.pv = pv;
    }

    public List<PvNoteModulaireItem> getPvNoteModulaireItems() {
        return pvNoteModulaireItems;
    }

    public void setPvNoteModulaireItems(List<PvNoteModulaireItem> pvNoteModulaireItems) {
        this.pvNoteModulaireItems = pvNoteModulaireItems;
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
        if (!(object instanceof PvNoteSemestreItem)) {
            return false;
        }
        PvNoteSemestreItem other = (PvNoteSemestreItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.PvSemestreItem[ id=" + id + " ]";
    }

}
