package pe.idat.dsi.dcn.client_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.idat.dsi.dcn.client_api.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
