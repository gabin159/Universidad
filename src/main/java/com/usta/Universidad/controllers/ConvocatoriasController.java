package com.usta.Universidad.controllers;

import com.usta.Universidad.entities.ConvocatoriaEntity;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;


@Controller
public class ConvocatoriasController {
    @Autowired
    private ConvocatoriaService convocatoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private StartupService startupService;


    @GetMapping("/verConvocatorias")
    public String verConvocatorias(Model model) {
        model.addAttribute("title", "listar Convocatorias");
        model.addAttribute("urlRegisterStarup", "/crearStartup");
        List<ConvocatoriaEntity> lista = convocatoriaService.findAll();
        lista.sort(Comparator.comparing(ConvocatoriaEntity::getIdConvocatorias));
        model.addAttribute("Convocatorias", lista);
        return "Emprendedor/Convocatorias";
    }

    @GetMapping("/crearStartup")
    public String crearStartup(Model model) {
        model.addAttribute("title", "Register a New Startup");
        model.addAttribute("startup", new StartupEntity());

        // Añadir listas de usuarios y convocatorias al modelo
        model.addAttribute("listaUsuario", usuarioService.findAll());
        model.addAttribute("listaConvocatoria", convocatoriaService.findAll());

        return "Emprendedor/formStartup";
    }

    @PostMapping("/crearStartup")
    public String guardarStartup(@Valid StartupEntity startup,
                                 BindingResult result,
                                 @RequestParam("foto") MultipartFile foto,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());

            // Volver a cargar las listas en caso de error
            model.addAttribute("title", "Register a New Startup");
            model.addAttribute("listaUsuario", usuarioService.findAll());
            model.addAttribute("listaConvocatoria", convocatoriaService.findAll());

            return "Emprendedor/formStartup";
        }

        String urlLogo = guardarImagen(foto);

        if (urlLogo == null) {
            redirectAttributes.addFlashAttribute("error", "Error uploading logo. Please try again.");
            return "redirect:/emprendedor";
        }

        startup.setLogo(urlLogo);
        startupService.save(startup);
        redirectAttributes.addFlashAttribute("mensajeExito", "Startup saved successfully");

        return "redirect:/emprendedor";
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
