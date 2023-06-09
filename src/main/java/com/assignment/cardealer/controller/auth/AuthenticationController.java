package com.assignment.cardealer.controller.auth;

import com.assignment.cardealer.exception.DealerNotFoundException;
import com.assignment.cardealer.exception.ListingNotFoundException;
import com.assignment.cardealer.exception.UserNotFoundException;
import com.assignment.cardealer.model.AuthenticationResponse;
import com.assignment.cardealer.model.Listing;
import com.assignment.cardealer.model.Users;
import com.assignment.cardealer.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "User operation endpoints")
public class AuthenticationController {
    private final UsersService usersService;

    @Operation(summary = "register", description = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Users user){
        var userExist = this.usersService.findUsersByUsername(user.getUsername());
        if(userExist == null){
            return ResponseEntity.ok(this.usersService.register(user));
        }
        return ResponseEntity
                .badRequest()
                .body(AuthenticationResponse.builder()
                .accessToken("This user already exist")
                .username(user.getUsername())
                .refreshToken("")
                .build());
    }

    @Operation(summary = "authenticate", description = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody Users user) {
        return  new ResponseEntity<>(usersService.authenticate(user), HttpStatus.OK);
    }

    @Operation(summary = "refreshToken", description = "Refresh a user token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<Listing> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usersService.refreshToken(request, response);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
