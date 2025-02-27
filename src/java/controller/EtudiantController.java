package controller;

import bean.Enseignant;
import bean.Etudiant;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import service.EtudiantFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import net.sf.jasperreports.engine.JRException;

@Named("etudiantController")
@SessionScoped
public class EtudiantController implements Serializable {

    @EJB
    private service.EtudiantFacade ejbFacade;
    private List<Etudiant> items = null;
    private Etudiant selected;
    private Enseignant enseignant;

    public EtudiantController() {
    }

    public Etudiant getSelected() {
       // Etudiant e = (Etudiant) SessionUtil.getConnectedUser();
        if (selected == null) {
           // selected = ejbFacade.find(e.getCne());
           selected = new Etudiant();
        }
        return selected;
    }

    public void setSelected(Etudiant selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EtudiantFacade getFacade() {
        return ejbFacade;
    }

    public void initEns() {

        enseignant = ejbFacade.findEns(selected);
        System.out.println("enseiget" + enseignant);
    }

    public Etudiant prepareCreate() {
        selected = new Etudiant();
        initializeEmbeddableKey();
        return selected;
    }

    public Enseignant getEnseignant() {
        if (enseignant == null) {
            enseignant = new Enseignant();
        }
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EtudiantCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void initStudent() {
        Etudiant e = (Etudiant) SessionUtil.getConnectedUser();
        selected = ejbFacade.find(e.getCne());
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EtudiantUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EtudiantDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Etudiant> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Etudiant getEtudiant(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Etudiant> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Etudiant> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Etudiant.class)
    public static class EtudiantControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EtudiantController controller = (EtudiantController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "etudiantController");
            return controller.getEtudiant(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Etudiant) {
                Etudiant o = (Etudiant) object;
                return getStringKey(o.getCne());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Etudiant.class.getName()});
                return null;
            }
        }

    }
    
    public boolean hasEmail() {
        System.out.println("=== has Email ===");
        Etudiant etudiant = (Etudiant) SessionUtil.getConnectedUser();
        System.out.println("Etudiant == " + etudiant);

        return ejbFacade.hasEmail(etudiant.getCne());

    }
    
    public boolean hasSecure() {
        System.out.println("=== has Secures ===");
        Etudiant etudiant = (Etudiant) SessionUtil.getConnectedUser();
        System.out.println("Etudiant == " + etudiant);

        return ejbFacade.hasSecure(etudiant.getCne());

    }
    
    public boolean isNotPass()
    {
        return ejbFacade.isNotPass();
    }
    
    public void changeData() {
        System.out.println("=== Start ChangeData Controller ===");
        Etudiant etudiantConnected = (Etudiant) SessionUtil.getConnectedUser();
        Etudiant cloneSelected = ejbFacade.cloneEtud(selected);
        selected = new Etudiant();
        if (ejbFacade.dejaEmail(cloneSelected.getEmail())) {
            System.out.println("******** DejaMail *******");
            JsfUtil.addErrorMessage("This Email is already Exist, Please Try Again another Email");
        }
        ejbFacade.changeData(etudiantConnected, cloneSelected);
        System.out.println("=== End ChangeData Controller ===");

    }
    
     public void generateCSPdf() throws JRException, IOException
     {
         Etudiant etud = ejbFacade.find(11001001);
         ejbFacade.generateCertificatScolairePdf(etud);
         FacesContext.getCurrentInstance().responseComplete();
         
     }
     
     public boolean dateAnnif()
     {
         Date myDate = new Date();
         
         if ( myDate.getDate() == 9 && myDate.getMonth() == 6 )
         {
             return true;
         }
         else
             return false;
     }

}
