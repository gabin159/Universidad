package com.usta.Universidad.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "USUARIOS")
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // Constructor vacío requerido por JPA
    public UsuarioEntity() {
    }

    // Constructor adicional (opcional) para facilitar la creación de instancias
    public UsuarioEntity(String email, String clave, String apellidoUsuario, String nombreUsuario,
                         String ocupacion, String telefono, String ciudad, RolEntity rol) {
        this.email = email;
        this.clave = clave;
        this.apellidoUsuario = apellidoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.ocupacion = ocupacion;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.rol = rol;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Usuario")
    private Long idUsuario;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "clave", nullable = false, length = 100)
    private String clave;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "apellido_usu", nullable = false, length = 40)
    private String apellidoUsuario;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre_usu", nullable = false, length = 40)
    private String nombreUsuario;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ocupacion", nullable = false, length = 60)
    private String ocupacion;

    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ciudad", nullable = false, length = 50)
    private String ciudad;

    @NotNull
    @JoinColumn(name="id_rol", referencedColumnName = "id_rol")
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RolEntity rol;

}
