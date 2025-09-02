    package com.usta.Universidad.controllers;


    import com.usta.Universidad.entities.ConvocatoriaEntity;
    import com.usta.Universidad.entities.UsuarioEntity;
    import com.usta.Universidad.models.services.ConvocatoriaService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import javax.validation.Valid;
    import java.util.Comparator;
    import java.util.List;


    @Controller
    public class adminitradorController {

        @Autowired
        private ConvocatoriaService convocatoriaService;


            @GetMapping("/administrador")
            public String gestionar(Model model) {
                model.addAttribute("title", "Gestion");
                model.addAttribute("urlRegister", "/crearConvocatoria");
                List<ConvocatoriaEntity> lista = convocatoriaService.findAll();
                lista.sort(Comparator.comparing(ConvocatoriaEntity::getIdConvocatorias));
                model.addAttribute("Convocatorias", lista);
                return "Administrador/gestionar";
            }

        @GetMapping("/crearConvocatoria")
        public String crearCon(Model model) {
            model.addAttribute("title", "Register a New Convocatoria");
            model.addAttribute("convocatoria", new ConvocatoriaEntity());
            return "Administrador/formConvocatoria";
        }

        @PostMapping("/crearConvocatoria")
        public String guardarConvocatoria(@Valid ConvocatoriaEntity convocatoria,
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes) {

            if (result.hasErrors()) {
                System.out.println(result.getAllErrors());
                return "Administrador/formConvocatoria";
            }

            convocatoriaService.save(convocatoria);
            redirectAttributes.addFlashAttribute("mensajeExito", "Convocatoria creada correctamente");

            return "redirect:/administrador";
        }
        // Eliminar usuario
        @PostMapping("/eliminarConvocatoria/{id}")
        public String eliminarConvocatoria(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
            if (id > 0) {
                ConvocatoriaEntity convocatoria = convocatoriaService.findById(id);
                if (convocatoria != null) {
                    try {
                        convocatoriaService.deleteById(id);
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
            return "redirect:/administrador";
        }

        @GetMapping("/modificar/{id}")
        public String modificarConvocatoria(@PathVariable("id") Long idConvocatoria, Model model) {
            ConvocatoriaEntity convocatoria = convocatoriaService.findById(idConvocatoria);
            model.addAttribute("title", "Editar Convocatoria");
            model.addAttribute("convocatoriaEdit", convocatoria);
            return "Administrador/editarConvocatoria"; // Vista a mostrar
        }

        @PostMapping("/modificar/{id}")
        public String actualizarConvocatoria(@ModelAttribute("convocatoriaEdit") ConvocatoriaEntity convocatoria,
                                             @PathVariable("id") Long idConvocatoria,
                                             BindingResult result,
                                             RedirectAttributes redirectAttributes) {

            if (result.hasErrors()) {
                System.out.println(result.getAllErrors());
                return "Administrador/editarConvocatoria";  // Mostrar el formulario con errores
            }

            ConvocatoriaEntity convocatoriaExistente = convocatoriaService.findById(idConvocatoria);
            convocatoriaExistente.setTitulo(convocatoria.getTitulo());
            convocatoriaExistente.setFechaIni(convocatoria.getFechaIni());
            convocatoriaExistente.setFechaFin(convocatoria.getFechaFin());

            convocatoriaService.save(convocatoriaExistente);

            redirectAttributes.addFlashAttribute("mensajeExito", "Convocatoria actualizada correctamente");
            return "redirect:/administrador";

        }
    }