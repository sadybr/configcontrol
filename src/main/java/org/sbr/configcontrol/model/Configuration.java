package org.sbr.configcontrol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Configuration {
    @Id
    private String id;
    private String email;
    private String password;
}
