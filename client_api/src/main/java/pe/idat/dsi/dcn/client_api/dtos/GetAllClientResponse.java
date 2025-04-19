package pe.idat.dsi.dcn.client_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsi.dcn.client_api.models.Client;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllClientResponse {
    private Long id;
    private String fullname;
    private String nid;

    public static GetAllClientResponse toDto(Client client){
        return new GetAllClientResponse(
            client.getId(), 
            client.getName() + " " + client.getLastname(),
            client.getNid()
            );
    }
}
