package project.seoulTTD.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @QueryProjection
    public UserDto(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
