package med.voll.api.model.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.direccion.DatosDireccion;

public record DatosActualizarPacientes(
        @NotNull
        Long id,
        String nombre,
        String nss,
        Boolean activo,
        DatosDireccion direccion
) {
}
