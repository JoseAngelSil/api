package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarPacientes(
        @NotNull
        Long id,
        String nombre,
        String nss,
        Boolean activo,
        DatosDireccion direccion
) {
}
