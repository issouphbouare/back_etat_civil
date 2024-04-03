package com.fst.back_etat_civil.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fst.back_etat_civil.model.ERole;
import com.fst.back_etat_civil.model.Role;
import com.fst.back_etat_civil.model.User;
import com.fst.back_etat_civil.repository.RoleRepository;
import com.fst.back_etat_civil.repository.UserRepository;
import com.fst.back_etat_civil.request.LoginRequest;
import com.fst.back_etat_civil.request.SignupRequest;
import com.fst.back_etat_civil.response.JwtResponse;
import com.fst.back_etat_civil.security.jwt.JwtUtils;
import com.fst.back_etat_civil.services.UserDetailsImpl;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    @ApiOperation(value = "Add Project")

    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println(authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<Role> roles=userRepository.findByUsername(loginRequest.getUsername()).get().getRoles();

        /*List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());*/

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
        System.out.println("\n \n test \n \n ");
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ce nom utilisateur existe déjà");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Cet email existe déjà");
        } else {

        // Create new user's account
        User users = new User();
    users.setUsername(signUpRequest.getUsername());
    users.setEmail(signUpRequest.getEmail());
    users.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        users.setRoles(roles);
        userRepository.save(users);

        return ResponseEntity.ok("L'utilisateur est enrengistrer avec succès");
        }
    }

    //////////////////////////////////

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String username) {
        try {
            List<User> users;

            if (username != null) {
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isPresent()) {
                    users = Collections.singletonList(userOptional.get());
                } else {
                    users = Collections.emptyList();
                }
            } else {
                users = userRepository.findAll();
            }

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable("id") long id) {
        Optional<User> usersData = userRepository.findById(id);

        if (usersData.isPresent()) {
            return new ResponseEntity<>(usersData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //////////////////////////

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUsers(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setEmail(user.getEmail());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "Delete Project")

    public ResponseEntity<HttpStatus> deleteUsers(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
    @DeleteMapping("/userss/{usersId}/roles/{roleId}")
    public ResponseEntity<HttpStatus> deleteRoleFromUsers(@PathVariable(value = "usersId") Long usersId, @PathVariable(value = "roleId") Long roleId) throws ResourceNotFoundException {
        Users users = usersRepository.findById(usersId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Users with id = " + usersId));

        users.removeRole(roleId);
        usersRepository.save(users);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    */

    //////////////////////////////////
    /*
    @PutMapping("/usernameupdate/{id}")
    public ResponseEntity updateUsername(@PathVariable("id") long id,@Valid @RequestBody UsernameUpdateRequest usernameUpdateRequest) {
        Optional user = usersRepository.findById(id);
        if (usersRepository.existsByUsername(usernameUpdateRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        else{
            user.setusername(usernameUpdateRequest.getusername())
            return ResponseEntity
                    .body(new MessageResponse("Username is updated!"));
        }
    }*/


}

