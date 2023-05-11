package med.voll.api.model.medico;

import med.voll.api.model.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion
) {
}
