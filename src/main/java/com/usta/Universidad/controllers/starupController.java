package com.usta.Universidad.controllers;


import com.usta.Universidad.entities.StartupEntity;
import com.usta.Universidad.models.services.ConvocatoriaService;
import com.usta.Universidad.models.services.StartupService;
import com.usta.Universidad.models.services.UsuarioService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class starupController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConvocatoriaService convocatoriaService;
    @Autowired
    private StartupService startupService;
    @GetMapping("/STARTUP")
    public String listarStartups(Model model) {

        // Lista de todas las startups
        List<StartupEntity> todas = startupService.findAll();
        todas.sort(Comparator.comparing(StartupEntity::getIdStartup));

        // Lista de startups con convocatoria
        List<StartupEntity> conConvocatoria = startupService.findAllWithConvocatoria();
        conConvocatoria.sort(Comparator.comparing(StartupEntity::getIdStartup));

        // Agregar atributos al modelo
        model.addAttribute("title", "Lista de Startups");
        model.addAttribute("todasStartupsa", todas);
        model.addAttribute("startupsConConvocatoria", conConvocatoria);

        return "Administrador/Listarstartups";

    }
//    @GetMapping("/modificarStartupAdm/{id}")
//    public String modificarStartupAdm(@PathVariable("id") Long idStartup, Model model) {
//        StartupEntity startup = startupService.findById(idStartup);
//
//        if (startup == null) {
//            model.addAttribute("errorMensaje", "Startup no encontrado");
//            return "redirect:/STARTUP"; // o vista de error
//        }
//
//        model.addAttribute("title", "Editar Startup");
//        model.addAttribute("startupEditAdm", startup);
//        model.addAttribute("listaUsuario", usuarioService.findAll()); // Cargar roles en el formulario
//        model.addAttribute("listaConvocatoria", convocatoriaService.findAll());
//        model.addAttribute("imagen", startup.getLogo());
//        return "Administrador/editarStarupAdm"; // Vista Thymeleaf o JSP
//    }

//
//    @PostMapping("/modificarStartupAdm/{id}")
//    public String editStartupAdm(
//            @ModelAttribute("startupEditAdm") StartupEntity startup,
//            BindingResult result,
//            @PathVariable("id") long idStartup,
//            @RequestParam("foto") MultipartFile foto,
//            RedirectAttributes redirectAttributes,
//            Model model) {
//
//        if (result.hasErrors()) {
//            model.addAttribute("listaUsuario", usuarioService.findAll());
//            model.addAttribute("listaConvocatoria", convocatoriaService.findAll());
//            model.addAttribute("title", "Editar Startup");
//            return "Administrador/editarStarupAdm";  // corregir nombre
//        }
//
//        StartupEntity startupExistente = startupService.findById(idStartup);
//        if (startupExistente == null) {
//            redirectAttributes.addFlashAttribute("errorMensaje", "Startup no encontrado");
//            return "redirect:/STARTUP";
//        }
//
//        startupExistente.setNombre(startup.getNombre());
//        startupExistente.setDescripcion(startup.getDescripcion());
//        startupExistente.setSector(startup.getSector());
//        startupExistente.setConvocatoria(startup.getConvocatoria());
//        startupExistente.setUsuario(startup.getUsuario());
//
//        String nombreImagen = guardarImagen(foto);
//        if (nombreImagen == null || nombreImagen.isBlank()) {
//            // mantener imagen anterior
//        } else {
//            startupExistente.setLogo(nombreImagen);
//        }
//
//        startupService.actualizarstar(startupExistente);
//        redirectAttributes.addFlashAttribute("success", "Startup actualizada correctamente");
//        return "redirect:/STARTUP";
//    }
    @PostMapping("/eliminarStartupAdm/{id}")
    public String eliminarStartupAdm(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            StartupEntity startup = startupService.findById(id);
            if (startup != null) {
                startupService.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "Startup eliminada correctamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Startup no encontrada");
            }
        }
        return "redirect:/STARTUP";
    }
    private String guardarImagen(MultipartFile imagen) {
        if (imagen == null || imagen.isEmpty()) {
            return null;
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("key", "e1e089d8d24c1d97aebc44e40dc9679a", ContentType.TEXT_PLAIN);
            builder.addBinaryBody("image", imagen.getInputStream(), ContentType.DEFAULT_BINARY, imagen.getOriginalFilename());

            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseString = EntityUtils.toString(entity);
                JSONObject jsonResponse = new JSONObject(responseString);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    JSONObject data = jsonResponse.getJSONObject("data");
                    return data.getString("url");
                } else {
                    System.err.println("Image upload failed: " + jsonResponse.optString("error"));
                }
            } else {
                System.err.println("HTTP error: " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
