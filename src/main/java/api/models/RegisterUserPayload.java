package api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUserPayload {

    private String email;

    private String password;

}
