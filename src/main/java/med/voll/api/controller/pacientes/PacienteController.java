package med.voll.api.controller.pacientes;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarMedico(@RequestBody @Valid RegistroPaciente registroPaciente,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente =pacienteRepository.save(new Paciente(registroPaciente));
        DatosRespuestaPaciente rsp = new DatosRespuestaPaciente(
                paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),paciente.getNss(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())
        );
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(rsp);
    }
    @GetMapping
    public ResponseEntity<Page<ListaPacientes>> ListadoPaciente(@PageableDefault(size = 20) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(ListaPacientes::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> buscarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DatosRespuestaPaciente rsp = new DatosRespuestaPaciente(
                paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),paciente.getNss(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())
        );
        return ResponseEntity.ok(rsp);
    }
    /**
     * Metodo modificarPaciente es el metodo que modifica los campos de los pacientes
     * que estan dados de alta o de baja
     * @param actualizarPacientes recibe los componentes que recibe el ID, y puede
     *                            obtener nombre, nss, activo y objetos de direccion.
     */
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> modificarPaciente(@RequestBody @Valid DatosActualizarPacientes actualizarPacientes) {
        Paciente paciente = pacienteRepository.getReferenceById(actualizarPacientes.id());
        paciente.actualizarDatos(actualizarPacientes);
        DatosRespuestaPaciente rsp = new DatosRespuestaPaciente(
                paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),paciente.getNss(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())
        );
        return ResponseEntity.ok(rsp);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity bajaDeServicioMedico(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.bajaDeServicioMedico();
        return ResponseEntity.noContent().build();
    }
}
