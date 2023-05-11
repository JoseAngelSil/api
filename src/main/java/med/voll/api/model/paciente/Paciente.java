package med.voll.api.model.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String nss;
    private Boolean activo;
    @Embedded
    private Direccion direccion;

    public Paciente(RegistroPaciente registroPaciente) {
        this.nombre = registroPaciente.nombre();
        this.email = registroPaciente.email();
        this.telefono = registroPaciente.telefono();
        this.nss = registroPaciente.nss();
        this.activo = true;
        this.direccion = new Direccion(registroPaciente.direccion());
    }

    public void actualizarDatos(DatosActualizarPacientes actualizarPacientes) {
        if (actualizarPacientes.nombre() != null)
            this.nombre = actualizarPacientes.nombre();
        if (actualizarPacientes.nss() != null)
            this.nss = actualizarPacientes.nss();
        if (actualizarPacientes.activo() != null)
            this.activo = actualizarPacientes.activo();
        if (actualizarPacientes.direccion() != null)
            this.direccion = direccion.actualizarDatos(actualizarPacientes.direccion());
    }

    public void bajaDeServicioMedico() {
        this.activo = false;
    }
}
