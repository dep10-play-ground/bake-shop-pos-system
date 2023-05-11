package lk.ijse.dep10.application.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;


import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {
    private String companyId;
    private String companyName;
    private ArrayList<String> companyContact;
    private String Address;
    private String email;



}
