package pe.idat.dsi.dcn.client_api.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsi.dcn.client_api.models.Client;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageableDto<T> {
    private int totalElements;
    private int pageNumber;
    private int pageSize;
    private List<T> items;

    public static BasePageableDto<GetAllClientPageableResponse> toGetAllClientPageableResponse(Page<Client> pageable){
        return new BasePageableDto<GetAllClientPageableResponse>(
            Integer.parseInt(Long.toString( pageable.getTotalElements())),
            pageable.getNumber(),
            pageable.getSize(),
            pageable.getContent()
                .stream()
                .map(GetAllClientPageableResponse::toDto)
                .collect(Collectors.toList())
        );
    }
}
