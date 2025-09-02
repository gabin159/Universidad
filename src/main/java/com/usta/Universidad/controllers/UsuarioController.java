package com.usta.Universidad.controllers;

import com.usta.Universidad.entities.RolEntity;
import com.usta.Universidad.entities.UsuarioEntity;
import com.usta.Universidad.models.services.RolService;
import com.usta.Universidad.models.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        model.addAttribute("title", "Registro de Usuario");
        return "register"; // la vista con el formulario
    }

    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") @Valid UsuarioEntity usuario,
                                   BindingResult result,
                                   @RequestParam("confirmarClave") String confirmarClave,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   SessionStatus status) {

        // Validación de errores de formulario
        if (result.hasErrors()) {
            model.addAttribute("title", "Registro de Usuario");
            return "register";
        }

        // Validar coincidencia de contraseñas
        if (!usuario.getClave().equals(confirmarClave)) {
            result.rejectValue("clave", "error.usuario", "Las contraseñas no coinciden.");
            model.addAttribute("title", "Registro de Usuario");
            return "register";
        }

        // Codificar contraseña
        String claveCodificada = new BCryptPasswordEncoder().encode(usuario.getClave());
        usuario.setClave(claveCodificada);

        RolEntity rolEmprendedor = new RolEntity();
        rolEmprendedor.setId_rol(2L);
        usuario.setRol(rolEmprendedor);
        usuarioService.save(usuario);
        status.setComplete();
        redirectAttributes.addFlashAttribute("success", "User registered successfully!");
        return "redirect:/login";
    }



    // Listar usuarios
    @GetMapping("/Usuarios")
    public String gestionarUsu(Model model) {
        model.addAttribute("title", "Gestionar Usuarios");
        model.addAttribute("urlRegisterUser", "/crearUsuario");  // Variable para el botón
        List<UsuarioEntity> lista = usuarioService.findAll();
        lista.sort(Comparator.comparing(UsuarioEntity::getIdUsuario));
        model.addAttribute("Usuarios", lista);
        return "Administrador/ListarUsuario";
    }

    // Mostrar formulario para crear usuario
    @GetMapping("/crearUsuario")
    public String crearUsu(Model model) {
        model.addAttribute("title", "Nuevo Usuario");
        model.addAttribute("usuario", new UsuarioEntity());
        model.addAttribute("listaRoles", rolService.findAll());
        return "Administrador/fromUsuario";  // Vista del formulario
    }

    // Guardar usuario creado
    @PostMapping("/crearUsuario")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") UsuarioEntity usuario,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaRoles", rolService.findAll());
            return "Administrador/fromUsuario";
        }

        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario creado correctamente");
        return "redirect:/Usuarios";
    }

    // Eliminar usuario
    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            UsuarioEntity usuario = usuarioService.findById(id);
            if (usuario != null) {
                try {
                    usuarioService.deleteById(id);
                    redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente");
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "ID inválido");
        }
        return "redirect:/Usuarios";
    }


    // Mostrar formulario para editar usuario
    @GetMapping("/modificarUsu/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable("id") Long idUsuario, Model model) {
        UsuarioEntity usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            model.addAttribute("errorMensaje", "Usuario no encontrado");
            return "redirect:/Usuarios"; // o vista de error
        }

        model.addAttribute("title", "Editar Usuario");
        model.addAttribute("usuarioEdit", usuario);
        model.addAttribute("listaRoles", rolService.findAll()); // Cargar roles en el formulario
        return "Administrador/editarUsuario"; // Vista Thymeleaf o JSP
    }

    // Procesar formulario de actualización
    @PostMapping("/modificarUsu/{id}")
    public String actualizarUsuario(@Valid @ModelAttribute("usuarioEdit") UsuarioEntity usuario,
                                    BindingResult result,
                                    @PathVariable("id") Long idUsuario,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listaRoles", rolService.findAll()); // Volver a cargar roles si hay errores
            model.addAttribute("title", "Editar Usuario");
            return "Administrador/editarUsuario";
        }

        UsuarioEntity usuarioExistente = usuarioService.findById(idUsuario);
        if (usuarioExistente == null) {
            redirectAttributes.addFlashAttribute("errorMensaje", "Usuario no encontrado");
            return "redirect:/Usuarios";
        }

        // Actualizar campos necesarios
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setClave(usuario.getClave());
        usuarioExistente.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
        usuarioExistente.setOcupacion(usuario.getOcupacion());
        usuarioExistente.setRol(usuario.getRol()); // Asignar nuevo rol

        usuarioService.save(usuarioExistente);

        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario actualizado correctamente");
        return "redirect:/Usuarios"; // Página a la que redirige después de modificar
    }


}


