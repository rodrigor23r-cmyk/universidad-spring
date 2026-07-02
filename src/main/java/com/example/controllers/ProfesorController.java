package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.services.ProfesorService;
import com.example.entities.Profesor;
import com.example.services.FacultadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService profesorService;
    private final FacultadService facultadService;

    private static final Logger LOG = Logger.getLogger("ProfesorController");

    @GetMapping("/listar")
    public String listarProfesor(Model model) {

        model.addAttribute("profesores", profesorService.getAllProfesores());
        return "listadoProfesores";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model, @ModelAttribute Profesor profesor) {

        model.addAttribute("facultades", facultadService.getAllFacultades());

        return "formAltaModifProfe";
    }

    @PostMapping("/persistir")
    public String procesarFormularioAltaModificacion(
            Model model,
            @Valid @ModelAttribute Profesor profesor,
            BindingResult result,
            @RequestParam(name = "file", required = false) MultipartFile file) {

        if (result.hasErrors()) {
            model.addAttribute("facultades",
                    facultadService.getAllFacultades());

            return "formAltaModifProfe";
        }

        if (file != null && !file.isEmpty()) {

            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
            String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

            try {
                byte[] bytesFotoRecibida = file.getBytes();
                Files.write(rutaCompleta, bytesFotoRecibida);
                profesor.setFoto(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (profesor.getId() != 0) {
            Profesor profesorAntiguo = profesorService.getProfesorById(profesor.getId());
            profesor.setFoto(profesorAntiguo.getFoto());
        }

        LOG.info("objeto profesor recibido: ");
        LOG.info(profesor.toString());

        profesorService.saveProfesor(profesor);
        return "redirect:/profesores/listar";
    }

    @GetMapping("/details/{id}")
    public String mostrarDetalles(Model model,
            @PathVariable(name = "id", required = true) int profesor_id) {

        model.addAttribute("profesor", profesorService.getProfesorById(profesor_id));

        return "detalleProfe";
    }

    @GetMapping("/update/{id}")
    public String updateProfesor(
            Model model,
            @PathVariable(name = "id", required = true) int profesor_id) {

        Profesor profesor = profesorService.getProfesorById(profesor_id);

        model.addAttribute("profesor", profesor);
        model.addAttribute("facultades", facultadService.getAllFacultades());


        return "formAltaModifProfe";
    }

    @GetMapping("/delete/{id}")
    public String deleteProfesor(
        Model model,
        @PathVariable("id") int profesor_id) {

        Profesor profesor = profesorService.getProfesorById(profesor_id);
        String foto = profesor.getFoto();
        if (foto != null) {
            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes/" + foto);

            if (Files.exists(rutaRelativa)){
                try {
                    Files.delete(rutaRelativa);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        profesorService.deleteProfesor(profesor_id);

        return "redirect:/profesores/listar";
    }
    

}
