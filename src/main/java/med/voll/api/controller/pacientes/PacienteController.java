package med.voll.api.controller.pacientes;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    @PostMapping
    public void registrarMedico(@RequestBody @Valid RegistroPaciente registroPaciente){
        pacienteRepository.save(new Paciente(registroPaciente));
    }
    @GetMapping
    public Page<ListaPacientes> ListadoMedico(@PageableDefault(size = 20) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return pacienteRepository.findByActivoTrue(paginacion).map(ListaPacientes::new);
    }

    /**
     * Metodo modificarPaciente es el metodo que modifica los campos de los pacientes
     * que estan dados de alta o de baja
     * @param actualizarPacientes recibe los componentes que recibe el ID, y puede
     *                            obtener nombre, nss, activo y objetos de direccion.
     */
    @PutMapping
    @Transactional
    public void modificarPaciente(@RequestBody @Valid DatosActualizarPacientes actualizarPacientes) {
        Paciente paciente = pacienteRepository.getReferenceById(actualizarPacientes.id());
        paciente.actualizarDatos(actualizarPacientes);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void bajaDeServicioMedico(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.bajaDeServicioMedico();
    }
}
