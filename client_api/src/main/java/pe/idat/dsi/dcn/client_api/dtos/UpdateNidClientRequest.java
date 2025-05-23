package pe.idat.dsi.dcn.client_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNidClientRequest {
    private Long id;
    private String nid;
}
