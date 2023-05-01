package lk.ijse.dep10.application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {
    private String companyId;
    private String companyName;
    private String companyAddress;
    private ArrayList<String> contactContact = new ArrayList<>();
    private String email;


    public Company() {
    }

    public Company(String companyId, String companyName, String companyAddress, ArrayList<String> contactContact, String email) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.contactContact = contactContact;
        this.email = email;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public ArrayList<String> getContactContact() {
        return contactContact;
    }

    public void setContactContact(ArrayList<String> contactContact) {
        this.contactContact = contactContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
