package med.voll.api.controller.pacientes;

import jakarta.validation.Valid;
import med.voll.api.model.medico.DatosListadoMedico;
import med.voll.api.model.medico.DatosRegistroMedico;
import med.voll.api.model.medico.Medico;
import med.voll.api.model.paciente.Paciente;
import med.voll.api.model.paciente.PacienteRepository;
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

}
