package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(
        long id,
        String nombre,
        String email,
        String telefono,
        String nss,
        DatosDireccion direccion
) {
}
