package pe.idat.dsi.dcn.client_api.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.idat.dsi.dcn.client_api.models.Address;
import pe.idat.dsi.dcn.client_api.repositories.AddressRepository;
import pe.idat.dsi.dcn.client_api.repositories.ClientRepository;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private ClientRepository clientRepository;

    public AddressService(AddressRepository addressRepository, 
    ClientRepository clientRepository){
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(timeout = 10, readOnly = false)
    public Address insert(Address entity) throws NotFoundException{
        var client = clientRepository.findById(entity.getClient().getId())
        .orElseThrow( () -> new NotFoundException());

        entity.setClient(client);

        return addressRepository.save(entity);
    }
}
