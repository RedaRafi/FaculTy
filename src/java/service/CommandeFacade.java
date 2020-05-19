/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.LigneCommande;
import bean.UserStock;
import controller.util.JsfUtil;
import controller.util.PdfUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CHAACHAI Youssef <youssef.chaachai@gmail.com>
 */
@Stateless
public class CommandeFacade extends AbstractFacade<Commande> {

    @PersistenceContext(unitName = "Pfe_FstgProjectPU")
    private EntityManager em;

    @EJB
    private UserStockFacade userStockFacade;
    @EJB
    private LigneCommandeFacade ligneCommandeFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeFacade() {
        super(Commande.class);
    }

    public Commande addCommande() {
        int nbrCmd = nombreCommande() + 1;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        UserStock userStock = userStockFacade.find("SH184344");
        Commande commande = new Commande();
        commande.setUserStock(userStock);
        commande.setId("CMD-" + year + "-" + nbrCmd);
        commande.setNombreCommande(nbrCmd);
        create(commande);
        System.out.println("Commande created successfully.....");
        return commande;
    }
    public void PrintMultiCommande() throws JRException, IOException{
        List<Commande> commandes = new ArrayList<>();
        List<Commande> cm = new ArrayList<>();
        commandes=findAll();
        List<JasperPrint> jasperPrints = new ArrayList();
        for (int i = 0; i < commandes.size(); i++) {
            Commande c = commandes.get(i);
            cm.add(c);
            jasperPrints.add(PdfUtil.createJasperPrint(cm, null,"/jasper/Commande.jasper"));
            cm.clear();
        }
        if (jasperPrints != null && !jasperPrints.isEmpty()) {
            PdfUtil.generatePdfs(jasperPrints,"MultiCommande Report");
        } else {
            JsfUtil.addErrorMessage("Une erreur s'est produit lors de l'impression");
        }
        
    }

    public int nombreCommande() {
        String query = "SELECT MAX(c.nombreCommande) FROM Commande c";
        return (int) em.createQuery(query).getSingleResult();
    }

    public void imprimerCommande(Commande commande) throws JRException, IOException {
        List<LigneCommande> lcm=ligneCommandeFacade.findLignecmd(commande.getId());
        commande.setLigneCommandes(lcm);
        System.out.println("==============================");
        System.out.println("IMPRIMER =====> OUI");
        System.out.println("==============================");
        System.out.println("##### Le numero de la commande est ======> " + commande.getId());
        System.out.println("##### La date de la commande est ======> " + commande.getDateCommande());
        System.out.println("##### L'utilisateur qui l'a effectué est ======> " + commande.getUserStock().getNom() + " " + commande.getUserStock().getPrenom());
        System.out.println("***** Les ligne de Commande ***** ");
        List<LigneCommande> lignes = ligneCommandeFacade.findByCommande(commande);
        System.out.println("----------------------------------------LigneCommande---------------------------------");
        System.out.println(commande.getLigneCommandes());
        
        List<Commande> c = new ArrayList<>();
        c.add(commande);
        PdfUtil.generatePdf(c, null, "Commande Report", "/jasper/Commande.jasper");
    
    }
    
    
    public List<Commande> findnonlivree(){
        System.out.println("=========== I've got here !! ===============");
        List<Commande> rest =new ArrayList();
        rest = em.createQuery("SELECT c FROM Commande c WHERE c.livree=1 OR c.livree=0").getResultList();
    
        for (int i = 0; i < rest.size(); i++) {
            
            Commande get = rest.get(i);
            System.out.println(get.getId());
            
        }
            
    return rest;
    }


    public List<Commande> findlivree(){
        System.out.println("=========== I've got here too !! ===============");
        List<Commande> rest =new ArrayList();
        rest = em.createQuery("SELECT c FROM Commande c WHERE c.livree=2").getResultList();
    
        for (int i = 0; i < rest.size(); i++) {
            
            Commande get = rest.get(i);
            System.out.println(get.getId());
            
        }
            
    return rest;
    }
}
