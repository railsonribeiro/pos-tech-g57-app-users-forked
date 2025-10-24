package br.com.five.seven.food.infra.persistence.entity;

import br.com.five.seven.food.domain.model.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_client")
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ClientEntity(Client client) {
        this.id = client.getId();
        this.cpf = client.getCpf();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.createdAt = client.getCreatedAt();
        this.updatedAt = client.getUpdatedAt();
    }

}
