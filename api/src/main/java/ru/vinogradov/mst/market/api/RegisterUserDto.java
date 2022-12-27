package ru.vinogradov.mst.market.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String username;
    private String password;
    private String confirmPassword;

    // TODO возможно расширение DTO
}
