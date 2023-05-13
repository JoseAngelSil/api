package med.voll.api.controller.medico;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico =medicoRepository.save(new Medico( datosRegistroMedico));
        // retorna un 201 created
        //URL donde encontrar al medico GET http://localhost:8080/medicos/xxx
        DatosRespuestaMedico resp = new DatosRespuestaMedico(medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        //URI url = "http://localhost:8080/medicos/"+medico.getId();
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(resp);
    }

    /**
     * Metodo Listado Medico
     * @return lista de medicos por medio de la tabla medicos por medio de una lista
     * El parametro @GetMapping es el metodo para que envie lista es como consulta o un Read
     * de modelo CRUD.
     */
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> ListadoMedico(@PageableDefault(size = 2) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> buscarMedico(@PathVariable Long id){
        Medico  medico = medicoRepository.getReferenceById(id);
        // delete DB
        //medicoRepository.delete(medico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()))); // respondiendo con un codifo automatico como el 204
    }

    @PutMapping
    @Transactional
    public ResponseEntity modificarMedico(@RequestBody @Valid DatosActualizarMedicos datosActualizarMedico){
        Medico  medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())
        )); // Retorna un 200 pero con el objeto de la persona registrada
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedico(@PathVariable Long id){
        Medico  medico = medicoRepository.getReferenceById(id);
        // delete logico
        medico.desactivarMedico();
        // delete DB
        //medicoRepository.delete(medico);
        return ResponseEntity.noContent().build(); // respondiendo con un codifo automatico como el 204
    }
}
