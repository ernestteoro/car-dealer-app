package com.assignment.cardealer.service;

import com.assignment.cardealer.config.JwtService;
import com.assignment.cardealer.model.*;
import com.assignment.cardealer.repository.RolesUserRepository;
import com.assignment.cardealer.repository.TokenRepository;
import com.assignment.cardealer.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService{

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final RolesUserRepository rolesUserRepository;

    public List<Users> findAllUsers(){
        return usersRepository.findAll();
    }

    public Optional<Users> findOneUserUserById(long idUser){
        return usersRepository.findById(idUser);
    }

    public Users findUsersByUsername(String name){
        return usersRepository.findUsersByUsername(name).orElse(null);
    }

    public AuthenticationResponse register(Users user) {
        String pass = user.getPassword();
        String encodedPassword = passwordEncoder.encode(pass);
        user.setPassword(encodedPassword);
        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        saveUserRole(user,user.getRolesUsersByIdUsers().stream().toList());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .username(user.getUsername())
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(Users user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        var userExist = usersRepository.findUsersByUsername(user.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(userExist);
        var refreshToken = jwtService.generateRefreshToken(userExist);
        revokeAllUserTokens(userExist);
        saveUserToken(userExist, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .username(userExist.getUsername())
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Users user, String jwtToken) {
        Token token = new Token();
        token.setExpired(false);
        token.setRevoked(false);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private void saveUserRole(Users user, List<RolesUser> rolesUsers) {
        rolesUsers.forEach(rolesUser -> rolesUser.setUsersByIdUsers(user));
        rolesUserRepository.saveAllAndFlush(rolesUsers);
    }

    public Optional<Token> findByToken(String token){
        return tokenRepository.findByToken(token);
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getIdUsers());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = this.usersRepository.findUsersByUsername(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}