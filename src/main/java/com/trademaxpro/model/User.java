package com.trademaxpro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

// stores user details, wallet and portfolio.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;                      

    private String name;                     
    private String email;                   
    private String pan;                     

    private Wallet wallet = new Wallet();    

    private List<PortfolioItem> portfolio = new ArrayList<>();  // owned stocks
}
