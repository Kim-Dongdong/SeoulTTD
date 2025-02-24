package project.seoulTTD.web.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phoneNum;
}
