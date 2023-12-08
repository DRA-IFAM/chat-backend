package com.dra.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createUser(@RequestBody CriarContatoDTO data) {
        String validateDTO = data.validateFields();
        if (validateDTO != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateDTO);
        }
        try {
            Contato user = this.authService.register(
                    CriarContatoDTO.from(data.getNome(), data.getEmail(), data.getSenha(), data.getTelefone(),
                            data.getEndereço(), data.getBairro(), data.getCidade(), data.getEstado()));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao criar usuário. Por favor, verifique os dados e tente novamente.");
        }
    }

    @PostMapping("/logar")
    public ResponseEntity<String> login(@RequestBody LogarContatoDTO data) {
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
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7);
            String jwtToken = this.jwtService.refreshToken(jwt);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido.");
        }
    }

}
