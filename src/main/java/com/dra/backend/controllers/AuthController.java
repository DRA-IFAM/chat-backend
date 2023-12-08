package com.dra.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dra.backend.dto.auth.CriarContatoDTO;
import com.dra.backend.dto.auth.LogarContatoDTO;
import com.dra.backend.models.entities.Contato;
import com.dra.backend.services.AuthService;
import com.dra.backend.services.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/criar")
    @Operation(summary = "Cria um novo usuário.")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Usuário já existe.")
    @ApiResponse(responseCode = "400", description = "Erro ao criar usuário. Por favor, verifique os dados e tente novamente.")
     ResponseEntity<String> createUser(@RequestBody CriarContatoDTO contatoDTO) {
        String validateDTO = contatoDTO.validateFields();
        if (validateDTO != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateDTO);
        }
        try {
            Contato contato = CriarContatoDTO.from(contatoDTO);
            Optional<Contato> user = this.authService.register(contato);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao criar usuário. Por favor, verifique os dados e tente novamente.");
        }
    }

    @PostMapping("/logar")
    @Operation(summary = "Realiza login.")
    @ApiResponse(responseCode = "200", description = "JWT Token")
    @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos.")
     ResponseEntity<String> login(@RequestBody LogarContatoDTO data) {
        String validateDTO = data.validateFields();
        if (validateDTO != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateDTO);
        }
        try {
            var usernameAndPassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
            var auth = authenticationManager.authenticate(usernameAndPassword);
            Contato user = (Contato) auth.getPrincipal();
            String jwtToken = this.jwtService.generateToken(user.getId());
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
        }
    }

    @GetMapping("/refresh")
    @Operation(summary = "Atualiza o token.")
    @ApiResponse(responseCode = "200", description = "JWT Token")
    @ApiResponse(responseCode = "401", description = "Token inválido.")
     ResponseEntity<String> refresh(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7);
            String jwtToken = this.jwtService.refreshToken(jwt);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido.");
        }
    }

}
