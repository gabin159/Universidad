package com.usta.Universidad.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Startup")
public class StartupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public StartupEntity() {
        // Constructor vacío requerido por JPA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Startup")
    private Long idStartup;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "descripcion", nullable = false, length = 40)
    private String descripcion;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "sector", nullable = false, length = 40)
    private String sector;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "logo", nullable = false, length = 200)
    private String logo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_Usuario", referencedColumnName = "id_Usuario", nullable = false)
    private UsuarioEntity usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_Convocatorias", referencedColumnName = "id_Convocatorias", nullable = false)
    private ConvocatoriaEntity convocatoria;

}
