package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.models.Genero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "estudiantes")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"facultad", "telefonos", "correos"})
@Builder
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 30, message = "El nombre tiene que tener un tamaño entre 3 y 30 caracteres")
    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+){0,}$", 
          message = "Debe contener palabras, separadas por un único espacio, y cada palabra debe empezar en mayúscula")
    private String nombre;
    
    
    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede estar en blanco")
    @NotEmpty(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 30, message = "El apellido tiene que tener un tamaño entre 3 y 30 caracteres")
    @Pattern(regexp = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+){0,}$", 
            message = "Debe contener palabras, separadas por un único espacio, y cada palabra debe empezar en mayúscula")
    private String primerApellido;



    @Size(max = 30, message = "El apellido tiene que tener menos de 30 caracteres")
    @Pattern(regexp = "^(|([A-ZÁÉÍÓÚÑ][a-záéíóúñ]{2,}+(\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+){0,}))$", 
          message = "Debe contener palabras, separadas por un único espacio, y cada palabra debe empezar en mayúscula y tener un mínimo de 3 caracteres")
    private String segundoApellido;

    // de mi cosecha.
    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @DateTimeFormat(pattern="yyyy-MM-dd") // mm sería minutos
    @PastOrPresent(message = "la fecha debe ser anterior o igual a la actual")
    private LocalDate fechaMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    @Builder.Default
    private Set<Telefono> telefonos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    @Builder.Default
    private Set<Correo> correos = new HashSet<>();

    private String foto;


    

}
