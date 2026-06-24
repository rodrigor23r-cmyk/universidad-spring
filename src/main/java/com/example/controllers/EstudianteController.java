package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {


    private final EstudianteService estudianteService;
    private final FacultadService facultadService;

    private static final Logger LOG = Logger.getLogger("EstudianteController");

    @GetMapping("/listar")
    public String listarEstudiante(Model model) {

        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());
        return "listadoEstudiantes";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model, @ModelAttribute Estudiante estudiante) {
        
        model.addAttribute("facultades", facultadService.getAllFacultades());

        return "formularioAltaModificacion";
    }
    
    @PostMapping("/persistir")
    public String procesarFormularioAltaModificacion(@ModelAttribute Estudiante estudiante,
        @RequestParam("numerosTelefono") String numerosTelefono,
        @RequestParam("direccionesCorreo") String direccionesCorreo) {

        LOG.info("objeto estudiante recibido: ");
        LOG.info(estudiante.toString());
        LOG.info("numeros telefono: " + numerosTelefono);
        LOG.info("direcciones de correo: " + direccionesCorreo);

        if (!numerosTelefono.isEmpty() && !numerosTelefono.isBlank()) {

            String [] arrayNumerosTelefono =numerosTelefono.split(";");
            List<String> listaNumerosTelefono = Arrays.asList(arrayNumerosTelefono);
            listaNumerosTelefono.forEach(num -> {
                estudiante.getTelefonos().add(Telefono.builder().numero(num).estudiante(estudiante).build());
            });
        }

      
        if (!direccionesCorreo.isEmpty() && !direccionesCorreo.isBlank()) {
            String [] arrayDireccionesCorreo =direccionesCorreo.split(";");
            List<String> listaDireccionesCorreo = Arrays.asList(arrayDireccionesCorreo);
            listaDireccionesCorreo.forEach(dir -> {
                estudiante.getCorreos().add(Correo.builder().email(dir).estudiante(estudiante).build());
            });
        }  
        
        estudianteService.saveEstudiante(estudiante);
        return "redirect:/estudiantes/listar";
    }
    
}
