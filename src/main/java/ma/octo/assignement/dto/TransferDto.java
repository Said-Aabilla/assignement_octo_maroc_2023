package ma.octo.assignement.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransferDto {
  private String nrCompteEmetteur;
  private String nrCompteBeneficiaire;
  private String motif;
  private BigDecimal montant;
  private Date date;

  public String getNrCompteEmetteur() {
    return nrCompteEmetteur;
  }

  public void setNrCompteEmetteur(String nrCompteEmetteur) {
    this.nrCompteEmetteur = nrCompteEmetteur;
  }

  public String getNrCompteBeneficiaire() {
    return nrCompteBeneficiaire;
  }

  public void setNrCompteBeneficiaire(String nrCompteBeneficiaire) {
    this.nrCompteBeneficiaire = nrCompteBeneficiaire;
  }

  public BigDecimal getMontant() {
    return montant;
  }

  public void setMontant(BigDecimal montant) {
    this.montant = montant;
  }

  public String getMotif() {
    return motif;
  }

  public void setMotif(String motif) {
    this.motif = motif;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
