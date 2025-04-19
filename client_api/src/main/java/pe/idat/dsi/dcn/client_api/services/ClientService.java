package pe.idat.dsi.dcn.client_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.idat.dsi.dcn.client_api.dtos.BasePageableDto;
import pe.idat.dsi.dcn.client_api.dtos.GetAllClientPageableResponse;
import pe.idat.dsi.dcn.client_api.dtos.GetAllClientResponse;
import pe.idat.dsi.dcn.client_api.dtos.GetClientByIdResponse;
import pe.idat.dsi.dcn.client_api.models.Client;
import pe.idat.dsi.dcn.client_api.repositories.ClientRepository;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client create(Client client){
        var result = clientRepository.saveAndFlush(client);
        return result;
    }

    public Client update(Long id, Client entity){
        var response = clientRepository.findById(id)
            .orElse(null);

        if(response == null) return null;

        response.setName(entity.getName());
        response.setLastname(entity.getLastname());
        response.setNid(entity.getNid());
        response.setEmail(entity.getEmail());

        clientRepository.saveAndFlush(response);
        return response;
    }

    public boolean delete(Long id){
        var response = clientRepository.findById(id)
        .orElse(null);

        if(response == null) return false;
        clientRepository.deleteById(id);

        return true;
    }

    public List<GetAllClientResponse> getAll(){
        return clientRepository.findAll()
            .stream()
            .map(GetAllClientResponse::toDto)
            .collect(Collectors.toList());
    }

    public BasePageableDto<GetAllClientPageableResponse> getAllPageable(int pageNumber, int pageSize, String sortColumn, String sortOrder, String name, String nid){

        Sort sorting = Sort.by(sortOrder.equals("asc")? Direction.ASC : Direction.DESC , sortColumn);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting);

        return BasePageableDto.toGetAllClientPageableResponse( clientRepository.findAllWithPagingAndCustomFilter(name, nid, pageable));
    }

    public GetClientByIdResponse getById(Long id){
        var response = clientRepository
            .findById(id)
            .orElse(null);
        if(response == null) return null;

        return GetClientByIdResponse.toDto(response);
    }

    @Transactional(readOnly = false, timeout = 10)
    public Client updateNid(Long id, String nid) throws NotFoundException {
        var client = clientRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        client.setNid(nid);
        clientRepository.save(client);
        return client;
    } 

}

