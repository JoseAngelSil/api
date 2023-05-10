package med.voll.api.controller.medico;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        medicoRepository.save(new Medico( datosRegistroMedico));
    }

    /**
     * Metodo Listado Medico
     * @return lista de medicos por medio de la tabla medicos por medio de una lista
     * El parametro @GetMapping es el metodo para que envie lista es como consulta o un Read
     * de modelo CRUD.
     */
    @GetMapping
    public Page<DatosListadoMedico> ListadoMedico(@PageableDefault(size = 2) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
    }
    @PutMapping
    @Transactional
    public void modificarMedico(@RequestBody @Valid DatosActualizarMedicos datosActualizarMedico){
        Medico  medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteMedico(@PathVariable Long id){
        Medico  medico = medicoRepository.getReferenceById(id);
        // delete logico
        medico.desactivarMedico();
        // delete DB
        //medicoRepository.delete(medico);
    }
}
