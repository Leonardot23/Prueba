package pe.idat.dsi.dcn.client_api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "lastname", length = 100, nullable = false)
    private String lastname;
    @Column(name = "nid", length = 8, unique = true, nullable = false)
    private String nid;
    @Column(name = "email", length = 200, nullable = true)
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "client")
    private List<Address> addresses;
}
