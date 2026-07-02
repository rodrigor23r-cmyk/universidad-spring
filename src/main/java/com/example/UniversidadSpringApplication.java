package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Profesor;
import com.example.entities.Telefono;
import com.example.models.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.ProfesorService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class UniversidadSpringApplication implements CommandLineRunner {

        private final EstudianteService estudianteService;
        private final ProfesorService profesorService;
        private final FacultadService facultadService;

        public static void main(String[] args) {
                SpringApplication.run(UniversidadSpringApplication.class, args);
        }

    @Override
	public void run(String... args) throws Exception {

		Facultad facultad1 = Facultad.builder().nombre("Ingeniería").build();
		Facultad facultad2 = Facultad.builder().nombre("Medicina").build();
		Facultad facultad3 = Facultad.builder().nombre("Derecho").build();
		Facultad facultad4 = Facultad.builder().nombre("Economía").build();
		Facultad facultad5 = Facultad.builder().nombre("Letras").build();

		facultadService.saveFacultad(facultad1);
		facultadService.saveFacultad(facultad2);
		facultadService.saveFacultad(facultad3);
		facultadService.saveFacultad(facultad4);
		facultadService.saveFacultad(facultad5);

		Estudiante estudiante1 = Estudiante.builder()
				.nombre("Juan")
				.primerApellido("Rivero")
				.segundoApellido("Fernández")
				.genero(Genero.HOMBRE)
				.fechaMatricula(LocalDate.of(1999, 2, 15))
				.facultad(facultad1)
				.telefonos(Set.of(Telefono.builder().numero("434343434").build(),
						Telefono.builder().numero("343223411").build()))
				.correos(Set.of(Correo.builder().email("tyte22@gmail.com").build(),
						Correo.builder().email("huodtyin@gmail.com").build()))
				.build();
		estudiante1.getCorreos().forEach(correo -> correo.setEstudiante(estudiante1));
		estudiante1.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante1));
		estudianteService.saveEstudiante(estudiante1);

		// --- Estudiante 2 ---
        Estudiante estudiante2 = Estudiante.builder()
                .nombre("María")
                .primerApellido("López")
                .segundoApellido("García")
                .genero(Genero.MUJER)
                .fechaMatricula(LocalDate.of(2001, 9, 10))
                .facultad(facultad2)
                .telefonos(Set.of(Telefono.builder().numero("600123456").build()))
                .correos(Set.of(Correo.builder().email("maria.lopez@gmail.com").build()))
                .build();
        estudiante2.getCorreos().forEach(correo -> correo.setEstudiante(estudiante2));
        estudiante2.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante2));
        estudianteService.saveEstudiante(estudiante2);

        // --- Estudiante 3 ---
        Estudiante estudiante3 = Estudiante.builder()
                .nombre("Carlos")
                .primerApellido("Martínez")
                .segundoApellido("Ruiz")
                .genero(Genero.HOMBRE)
                .fechaMatricula(LocalDate.of(2003, 1, 20))
                .facultad(facultad3)
                .telefonos(Set.of(Telefono.builder().numero("611987654").build(),
                        Telefono.builder().numero("622456789").build()))
                .correos(Set.of(Correo.builder().email("cmartinez.99@hotmail.com").build()))
                .build();
        estudiante3.getCorreos().forEach(correo -> correo.setEstudiante(estudiante3));
        estudiante3.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante3));
        estudianteService.saveEstudiante(estudiante3);

        // --- Estudiante 4 ---
        Estudiante estudiante4 = Estudiante.builder()
                .nombre("Alex")
                .primerApellido("Gómez")
                .segundoApellido("Sánchez")
                .genero(Genero.OTRO)
                .fechaMatricula(LocalDate.of(2005, 10, 5))
                .facultad(facultad4)
                .telefonos(Set.of(Telefono.builder().numero("633112233").build()))
                .correos(Set.of(Correo.builder().email("alex.gs@outlook.com").build(),
                        Correo.builder().email("alex_dev@gmail.com").build()))
                .build();
        estudiante4.getCorreos().forEach(correo -> correo.setEstudiante(estudiante4));
        estudiante4.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante4));
        estudianteService.saveEstudiante(estudiante4);

        // --- Estudiante 5 ---
        Estudiante estudiante5 = Estudiante.builder()
                .nombre("Lucía")
                .primerApellido("Díaz")
                .segundoApellido("Velasco")
                .genero(Genero.MUJER)
                .fechaMatricula(LocalDate.of(2002, 3, 12))
                .facultad(facultad5)
                .telefonos(Set.of(Telefono.builder().numero("644556677").build(),
                        Telefono.builder().numero("655889900").build()))
                .correos(Set.of(Correo.builder().email("lucia.dv@universidad.edu").build(),
                        Correo.builder().email("luci_diaz@gmail.com").build()))
                .build();
        estudiante5.getCorreos().forEach(correo -> correo.setEstudiante(estudiante5));
        estudiante5.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante5));
        estudianteService.saveEstudiante(estudiante5);

        // --- Profesor 1 ---
        Profesor profesor1 = Profesor.builder()
                .nombre("Víctor Rafael")
                .primerApellido("Machado")
                .segundoApellido("Arteaga")
                .genero(Genero.MUJER)
                .fechaAlta(LocalDate.of(2002, 3, 12))
                .facultad(facultad5)
                .salario(BigDecimal.valueOf(5200.00))
                .build();
        
        profesorService.saveProfesor(profesor1);
		
        // --- Profesor 2 ---
Profesor profesor2 = Profesor.builder()
        .nombre("Carlos")
        .primerApellido("Mendoza")
        .segundoApellido("Ruiz")
        .genero(Genero.HOMBRE)
        .fechaAlta(LocalDate.of(2005, 9, 15))
        .facultad(facultad1)
        .salario(BigDecimal.valueOf(4800.00))
        .build();
profesorService.saveProfesor(profesor2);

// --- Profesor 3 ---
Profesor profesor3 = Profesor.builder()
        .nombre("Elena")
        .primerApellido("Gómez")
        .segundoApellido("Marín")
        .genero(Genero.MUJER)
        .fechaAlta(LocalDate.of(2010, 2, 1))
        .facultad(facultad2)
        .salario(BigDecimal.valueOf(4100.50))
        .build();
profesorService.saveProfesor(profesor3);

// --- Profesor 4 ---
Profesor profesor4 = Profesor.builder()
        .nombre("Alejandro")
        .primerApellido("Silva")
        .segundoApellido("Torres")
        .genero(Genero.HOMBRE)
        .fechaAlta(LocalDate.of(1998, 11, 24))
        .facultad(facultad3)
        .salario(BigDecimal.valueOf(6100.00))
        .build();
profesorService.saveProfesor(profesor4);

// --- Profesor 5 ---
Profesor profesor5 = Profesor.builder()
        .nombre("Sofía")
        .primerApellido("Castro")
        .segundoApellido("Peña")
        .genero(Genero.MUJER)
        .fechaAlta(LocalDate.of(2015, 6, 30))
        .facultad(facultad4)
        .salario(BigDecimal.valueOf(3500.00))
        .build();
profesorService.saveProfesor(profesor5);

// --- Profesor 6 ---
Profesor profesor6 = Profesor.builder()
        .nombre("Javier")
        .primerApellido("Morales")
        .segundoApellido("Sanz")
        .genero(Genero.HOMBRE)
        .fechaAlta(LocalDate.of(2008, 4, 18))
        .facultad(facultad5)
        .salario(BigDecimal.valueOf(4950.00))
        .build();
profesorService.saveProfesor(profesor6);

// --- Profesor 7 ---
Profesor profesor7 = Profesor.builder()
        .nombre("Martina")
        .primerApellido("Navarro")
        .segundoApellido("Soler")
        .genero(Genero.MUJER)
        .fechaAlta(LocalDate.of(2012, 1, 15))
        .facultad(facultad4)
        .salario(BigDecimal.valueOf(3900.00))
        .build();
profesorService.saveProfesor(profesor7);

// --- Profesor 8 ---
Profesor profesor8 = Profesor.builder()
        .nombre("Diego")
        .primerApellido("Ortega")
        .segundoApellido("Flores")
        .genero(Genero.HOMBRE)
        .fechaAlta(LocalDate.of(2001, 7, 22))
        .facultad(facultad3)
        .salario(BigDecimal.valueOf(5500.00))
        .build();
profesorService.saveProfesor(profesor8);

// --- Profesor 9 ---
Profesor profesor9 = Profesor.builder()
        .nombre("Carmen")
        .primerApellido("Rubio")
        .segundoApellido("Lara")
        .genero(Genero.MUJER)
        .fechaAlta(LocalDate.of(2019, 10, 5))
        .facultad(facultad2)
        .salario(BigDecimal.valueOf(3200.00))
        .build();
profesorService.saveProfesor(profesor9);

// --- Profesor 10 ---
Profesor profesor10 = Profesor.builder()
        .nombre("Pablo")
        .primerApellido("Benítez")
        .segundoApellido("Vargas")
        .genero(Genero.HOMBRE)
        .fechaAlta(LocalDate.of(1995, 3, 1))
        .facultad(facultad1)
        .salario(BigDecimal.valueOf(6500.00))
        .build();
profesorService.saveProfesor(profesor10);


	}

}
