package med.voll.api.domain.paciente;

public record ListaPacientes(
        Long id,
        String nombre,
        String nss,
        String email,
        String telefono
) {
    public ListaPacientes (Paciente paciente) {
        this(paciente.getId(),
                paciente.getNombre(),
                paciente.getNss(),
                paciente.getEmail(),
                paciente.getTelefono());
    }
}
