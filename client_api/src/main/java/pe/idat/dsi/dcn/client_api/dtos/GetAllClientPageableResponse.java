package pe.idat.dsi.dcn.client_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsi.dcn.client_api.models.Client;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllClientPageableResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String documentType;
    private String documentId;
    private int addresses;

     public static GetAllClientPageableResponse toDto(Client entity){
        return new GetAllClientPageableResponse(
            entity.getId(), 
            entity.getName(),
            entity.getLastname(), 
            entity.getNid().length() == 8 ? "DNI": entity.getNid().length() == 11 ? "RUC" : "CEX", 
            entity.getNid(), 
            entity.getAddresses().size()
            );
    } 
}
