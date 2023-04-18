package lk.ijse.dep10.application.model;

import java.io.Serializable;

public class Company implements Serializable {
    private String companyId;
    private String companyName;
    private String companyContact;
    private String companyAddress;
    private String repName;
    private String repContact;

    public Company() {
    }

    public Company(String companyId, String companyName, String companyContact, String companyAddress, String repName, String repContact) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyContact = companyContact;
        this.companyAddress = companyAddress;
        this.repName = repName;
        this.repContact = repContact;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepContact() {
        return repContact;
    }

    public void setRepContact(String repContact) {
        this.repContact = repContact;
    }
}
