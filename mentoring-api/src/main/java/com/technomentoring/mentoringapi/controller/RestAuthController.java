package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.config.JwtGenerator;
import com.technomentoring.mentoringapi.dto.LoginDTO;
import com.technomentoring.mentoringapi.dto.UsernameDto;
import com.technomentoring.mentoringapi.model.Roles;
import com.technomentoring.mentoringapi.model.Usuarios;
import com.technomentoring.mentoringapi.repository.IRolesRepository;
import com.technomentoring.mentoringapi.repository.IUsuariosRepository;
import com.technomentoring.mentoringapi.service.impl.UsuariosServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class RestAuthController {
    private UsuariosServiceImpl usuariosService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository iRolesRepository;
    private IUsuariosRepository iUsuariosRepository;
    private JwtGenerator jwtGenerator;

    public RestAuthController(UsuariosServiceImpl usuariosService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository iRolesRepository, IUsuariosRepository iUsuariosRepository, JwtGenerator jwtGenerator) {
        this.usuariosService = usuariosService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.iRolesRepository = iRolesRepository;
        this.iUsuariosRepository = iUsuariosRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register/student")
    public ResponseEntity<String> registrarStudent(@RequestBody RegisterDTO registerDTO){
    if (iUsuariosRepository.existsByUsername(registerDTO.getUsername())){
        return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
    }
    Usuarios usuarios = new Usuarios();
    usuarios.setUsername(registerDTO.getUsername());
    usuarios.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
    usuarios.setName(registerDTO.getName());
    usuarios.setEmail(registerDTO.getEmail());
    usuarios.setDni(registerDTO.getDni());
    usuarios.setProfile(registerDTO.getProfile());
    Roles roles = iRolesRepository.findByName("STUDENT").get();
    usuarios.setRoles(Collections.singletonList(roles));
    iUsuariosRepository.save(usuarios);
    return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
}

    @PostMapping("/register/mentor")
    public ResponseEntity<String> registrarMentor(@RequestBody RegisterDTO registerDTO){
        if (iUsuariosRepository.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(registerDTO.getUsername());
        usuarios.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        usuarios.setName(registerDTO.getName());
        usuarios.setEmail(registerDTO.getEmail());
        usuarios.setDni(registerDTO.getDni());
        usuarios.setProfile(registerDTO.getProfile());
        Roles roles = iRolesRepository.findByName("MENTOR").get();
        usuarios.setRoles(Collections.singletonList(roles));
        iUsuariosRepository.save(usuarios);
        return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
    }

    @PostMapping("/register/adm")
    public ResponseEntity<String> registrarAdmin(@RequestBody RegisterDTO registerDTO){
        if (iUsuariosRepository.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(registerDTO.getUsername());
        usuarios.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        usuarios.setName(registerDTO.getName());
        usuarios.setEmail(registerDTO.getEmail());
        usuarios.setDni(registerDTO.getDni());
        usuarios.setProfile(registerDTO.getProfile());
        Roles roles = iRolesRepository.findByName("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        iUsuariosRepository.save(usuarios);
        return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRespuestaDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generarToken(authentication);
        return new ResponseEntity<>(new AuthRespuestaDTO(token), HttpStatus.OK);
    }


    @PostMapping("/getusername")
    public ResponseEntity<String> getUsernameFromToken(@RequestBody TokenRequestDTO tokenRequest) {
        String token = tokenRequest.getAccessToken();

        // Valida el token
        if (jwtGenerator.validarToken(token)) {
            String username = jwtGenerator.obtenerUsernameDeJwt(token);
            return ResponseEntity.ok("Usuario: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

    @PostMapping("/userbyusername")
    public ResponseEntity<Optional<Usuarios>> getUserByUsername(@RequestBody UsernameDto usernameDTO) {
        String username = usernameDTO.getUsername();

        // Retrieve the user from the repository using the provided username
        Optional<Usuarios> usuario = iUsuariosRepository.findByUsername(username);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/userbytoken")
    public ResponseEntity<Usuarios> obtenerUsuarioPorToken(@RequestBody TokenRequestDTO tokenRequest) {
        String token = tokenRequest.getAccessToken();

        // Valida el token
        if (jwtGenerator.validarToken(token)) {
            String username = jwtGenerator.obtenerUsernameDeJwt(token);

            // Obtener el usuario desde el repositorio usando el nombre de usuario obtenido
            Usuarios usuario = iUsuariosRepository.findByUsername(username).orElse(null);

            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Usuarios>> getAllUsers() {
        List<Usuarios> usuarios = iUsuariosRepository.findAll();
        if (!usuarios.isEmpty()) {
            return ResponseEntity.ok(usuarios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }







}
