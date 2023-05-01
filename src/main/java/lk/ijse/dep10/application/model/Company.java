package lk.ijse.dep10.application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {
    private String companyId;
    private String companyName;
    private ArrayList<String> contactContact = new ArrayList<>();
    private String companyAddress;
    private String email;
    private String repName;

    public Company(String companyId, String companyName, ArrayList<String> contactContact, String companyAddress, String email, String repName) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.contactContact = contactContact;
        this.companyAddress = companyAddress;
        this.email = email;
        this.repName = repName;
    }

    public Company() {
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

    public ArrayList<String> getContactContact() {
        return contactContact;
    }

    public void setContactContact(ArrayList<String> contactContact) {
        this.contactContact = contactContact;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }
}
