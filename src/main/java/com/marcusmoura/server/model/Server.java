package com.marcusmoura.server.model;

import com.marcusmoura.server.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "Ip address cannot be empty or null")
    private String ipAdress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;

}
