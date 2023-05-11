package med.voll.api.model.paciente;

import med.voll.api.model.direccion.DatosDireccion;

public record DatosRespuestaPaciente(
        long id,
        String nombre,
        String email,
        String telefono,
        String nss,
        DatosDireccion direccion
) {
}
