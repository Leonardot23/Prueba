package pe.idat.dsi.dcn.client_api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.idat.dsi.dcn.client_api.dtos.BasePageableDto;
import pe.idat.dsi.dcn.client_api.dtos.GetAllClientPageableResponse;
import pe.idat.dsi.dcn.client_api.dtos.GetAllClientResponse;
import pe.idat.dsi.dcn.client_api.dtos.GetClientByIdResponse;
import pe.idat.dsi.dcn.client_api.dtos.UpdateNidClientRequest;
import pe.idat.dsi.dcn.client_api.models.Client;
import pe.idat.dsi.dcn.client_api.services.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/clients")
public class ClientRestController {

    private ClientService clientService;

    public ClientRestController(ClientService service){
        this.clientService = service;
    }

    @GetMapping
    public ResponseEntity<List<GetAllClientResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetClientByIdResponse> getById(@PathVariable Long id) {
       var response = clientService.getById(id);
        
        return response != null ? 
            ResponseEntity.ok(response):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/paging")
    public  ResponseEntity<BasePageableDto<GetAllClientPageableResponse>> getAllPageable(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "5") int pageSize, 
        @RequestParam(defaultValue = "id") String sortColumn,
        @RequestParam(defaultValue = "asc") String sortOrder, 
        @RequestParam(defaultValue = "") String name, 
        @RequestParam(defaultValue = "") String nid) {
        return ResponseEntity.ok(clientService.getAllPageable(pageNumber, pageSize, sortColumn, sortOrder, name, nid));
    }
    

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client entity){
        try {
            var response = clientService.create(entity);
            return ResponseEntity.created(URI.create("/api/v1/clients/"+response.getId())).body(entity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client entity) {
        
        var response = clientService.update(id, entity);

        if(response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean hasRemoved = clientService.delete(id);
        return hasRemoved ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping()
    public ResponseEntity<Client> delete(@RequestBody UpdateNidClientRequest filter) {
        try {
            var client = clientService.updateNid(filter.getId(), filter.getNid());
            return client == null ? 
                ResponseEntity.notFound().build() : 
                ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
