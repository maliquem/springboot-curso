package br.com.jael.springcurso.springbootcurso.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String login;
    private String token;
}
