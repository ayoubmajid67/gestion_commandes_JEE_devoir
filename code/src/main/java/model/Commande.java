package model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;



public class Commande {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;
    private double montant;
    private String idCompte;

    // Constructors
    public Commande() {
        this.id = UUID.randomUUID().toString();
        this.date = new Date(); // current date by default
        this.montant = 0.0;
        this.idCompte = "unknown";
    }

    public Commande(String idCommande, String date, double montant, String idCompte)  {
        this.id = idCommande;
        try {
            this.date=SDF.parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        this.montant = montant;
        this.idCompte = idCompte;
    }
    public Commande(String idCommande, Date date, double montant, String idCompte)  {
        this.id = idCommande;

            this.date=date;



        this.montant = montant;
        this.idCompte = idCompte;
    }
    public String dateToString() {
        return date != null ? SDF.format(date) : "N/A";
    }
    public static Date parseDate(String dateStr) throws  ParseException {
        return  SDF.parse(dateStr);
    }
    public String getFormattedDate() {
        return SDF.format(date);
    }

    // Getters
    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getmontant() {
        return montant;
    }

    public String getidCompte() {
        return idCompte;
    }

    // Setters
    public void setDate(Date date) {
        if (date != null) {

               this.date= date;

        }
    }
    public void setMontant(double montant) {
        if (montant >= 0) {
            this.montant = montant;
        }
    }

    public void setIdCompte(String idCompte) {
        if (idCompte != null && !idCompte.isEmpty()) {
            this.idCompte = idCompte;
        }
    }

    // Method to update the Commande object
    public void editCommande(Commande updatedCommande) {
        this.setDate(updatedCommande.date);
        this.setMontant(updatedCommande.montant);
        this.setIdCompte(updatedCommande.idCompte);
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande='" + id + '\'' +
                ", date=" + date +
                ", montant=" + montant +
                ", idClient='" + idCompte + '\'' +
                '}';
    }
}