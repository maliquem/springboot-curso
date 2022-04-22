package br.com.jael.springcurso.springbootcurso.rest.controller;

import br.com.jael.springcurso.springbootcurso.domain.entities.Usuario;
import br.com.jael.springcurso.springbootcurso.exception.SenhaInvalidaException;
import br.com.jael.springcurso.springbootcurso.rest.dto.CredenciaisDTO;
import br.com.jael.springcurso.springbootcurso.rest.dto.TokenDTO;
import br.com.jael.springcurso.springbootcurso.security.jwt.JwtService;
import br.com.jael.springcurso.springbootcurso.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Registrar um novo usuário.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário registrado com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Usuario postUsuario(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    @ApiOperation("Autorizar um novo usuário.")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Usuário autorizado com sucesso."),
            @ApiResponse(code = 401, message = "Usuário não autorizado.")
    })
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {
            Usuario usuario = Usuario.builder().login(credenciais.getLogin()).senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        }catch ( UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
