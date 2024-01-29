package com.dreamer925.emergencyMonitoringSystem.authentication;

import com.dreamer925.emergencyMonitoringSystem.config.TokenService;
import com.dreamer925.emergencyMonitoringSystem.user.User;
import com.dreamer925.emergencyMonitoringSystem.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/auth/login")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    //Todo: get rid of model
    @GetMapping
    public ModelAndView getLoginView(){
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse token(@Valid @RequestBody LoginRequest userLogin) throws AuthenticationException {
        if (userRepository.findByEmail(userLogin.username()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with email " + userLogin.username() +
                    " was not found.");
        }
        User user = userRepository.findByEmail(userLogin.username()).get();
        user.setEnabled(true);
        System.out.println(userLogin.username() + " " + userLogin.password());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        System.out.println(authentication.getName() + " " + authentication.isAuthenticated());
        return new AuthResponse(tokenService.generateToken(authentication),
                user.getName(), user.getRole().toString().toLowerCase());
    }

    record AuthResponse(String token, String name, String role){}
}
