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

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final FacultadService facultadService;
    private final CorreoService correoService;
    private final TelefonoService telefonoService;

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
    public String procesarFormularioAltaModificacion(
            Model model,
            @Valid @ModelAttribute Estudiante estudiante,
            BindingResult result,
            @RequestParam("numerosTelefono") String numerosTelefono,
            @RequestParam("direccionesCorreo") String direccionesCorreo,
            @RequestParam(name = "file", required = false) MultipartFile file) {

        if (result.hasErrors()) {
            model.addAttribute("facultades",
                    facultadService.getAllFacultades());

            return "formularioAltaModificacion";
        }

        if (file != null && !file.isEmpty()) {

            Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
            String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

            try {
                byte[] bytesFotoRecibida = file.getBytes();
                Files.write(rutaCompleta, bytesFotoRecibida);
                estudiante.setFoto(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (estudiante.getId() != 0) {
            Estudiante estudianteAntiguo = estudianteService.getEstudianteById(estudiante.getId());
            estudiante.setFoto(estudianteAntiguo.getFoto());
        }

        LOG.info("objeto estudiante recibido: ");
        LOG.info(estudiante.toString());
        LOG.info("numeros telefono: " + numerosTelefono);
        LOG.info("direcciones de correo: " + direccionesCorreo);

        if (!numerosTelefono.isEmpty() && !numerosTelefono.isBlank()) {

            String[] arrayNumerosTelefono = numerosTelefono.split(";");
            List<String> listaNumerosTelefono = Arrays.asList(arrayNumerosTelefono);
            listaNumerosTelefono.forEach(num -> {
                estudiante.getTelefonos().add(Telefono.builder().numero(num).estudiante(estudiante).build());
            });
        }

        if (!direccionesCorreo.isEmpty() && !direccionesCorreo.isBlank()) {
            String[] arrayDireccionesCorreo = direccionesCorreo.split(";");
            List<String> listaDireccionesCorreo = Arrays.asList(arrayDireccionesCorreo);
            listaDireccionesCorreo.forEach(dir -> {
                estudiante.getCorreos().add(Correo.builder().email(dir).estudiante(estudiante).build());
            });
        }

        // borrar
        if (estudiante.getId() != 0) {
            if (telefonoService.existsByEstudiante(estudiante)) {
                telefonoService.deleteByEstudiante(estudiante);
            }
            if (correoService.existsByEstudiante(estudiante)) {
                correoService.deleteByEstudiante(estudiante);
            }
        }

        estudianteService.saveEstudiante(estudiante);
        return "redirect:/estudiantes/listar";
    }

    @GetMapping("/details/{id}")
    public String mostrarDetalles(Model model,
            @PathVariable(name = "id", required = true) int estudiante_id) {

        model.addAttribute("estudiante", estudianteService.getEstudianteById(estudiante_id));

        return "detalle";
    }

    @GetMapping("/update/{id}")
    public String updateEstudiante(
            Model model,
            @PathVariable(name = "id", required = true) int estudiante_id) {

        Estudiante estudiante = estudianteService.getEstudianteById(estudiante_id);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("facultades", facultadService.getAllFacultades());

        Set<Telefono> telefonos = estudiante.getTelefonos();
        if (telefonos.size() > 0) {
            String numerosTelefono = telefonos.stream().map(telefono -> telefono.getNumero())
                    .collect(Collectors.joining(";"));
            model.addAttribute("numerosTelefono", numerosTelefono);
        }

        Set<Correo> correos = estudiante.getCorreos();

        if (correos.size() > 0) {

            String direccionesCorreos = correos.stream()
                    .map(correo -> correo.getEmail())
                    .collect(Collectors.joining(";"));
            model.addAttribute("direccionesCorreos", direccionesCorreos);
        }

        return "formularioAltaModificacion";
    }

    @GetMapping("/delete/{id}")
    public String deleteEstudiante(
        Model model,
        @PathVariable("id") int estudiante_id) {

        Estudiante estudiante = estudianteService.getEstudianteById(estudiante_id);
        String foto = estudiante.getFoto();
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
        estudianteService.deleteEstudiante(estudiante_id);

        return "redirect:/estudiantes/listar";
    }
    

}
