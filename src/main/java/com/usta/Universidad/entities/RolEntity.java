package com.usta.Universidad.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name="ROLES")
public class RolEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private long id_rol;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "rol", length = 40, nullable = false)
    private String rol;

    public RolEntity(String rol) {
        super();
        this.rol =rol;
    }

    public RolEntity() {

    }


}
