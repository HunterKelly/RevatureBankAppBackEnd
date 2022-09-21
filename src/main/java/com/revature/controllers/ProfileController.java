package com.revature.controllers;

import com.revature.dtos.UserProfileRequest;
import com.revature.models.User;
import com.revature.models.UserProfile;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserProfile> setProfile(
            @PathVariable int id,
            @RequestBody UserProfileRequest profileRequest
    ) {
        User dbUser = userService.findById(id);
        Optional<UserProfile> dbProfile = userService.findProfileForUser(id);
        if (dbProfile.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            UserProfile newProfile = new UserProfile();
            newProfile.setFirstName(profileRequest.getFirstName());
            newProfile.setLastName(profileRequest.getLastName());
            newProfile.setAddress(profileRequest.getAddress());
            newProfile.setCity(profileRequest.getCity());
            newProfile.setState(profileRequest.getState());
            newProfile.setZipCode(profileRequest.getZipCode());
            newProfile.setPhone(profileRequest.getPhone());
            newProfile.setUser(dbUser);
            return ResponseEntity.ok(userService.saveProfile(newProfile));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable int id) {
        Optional<UserProfile> dbProfile = userService.findProfileForUser(id);
        if (dbProfile.isPresent()) {
            return ResponseEntity.ok(dbProfile.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile(
            @PathVariable int id,
            @RequestBody UserProfileRequest profileRequest
    ) {
        User dbUser = userService.findById(id);
        Optional<UserProfile> dbProfile = userService.findProfileForUser(id);
        UserProfile newProfile = new UserProfile();
        newProfile.setFirstName(profileRequest.getFirstName());
        newProfile.setLastName(profileRequest.getLastName());
        newProfile.setAddress(profileRequest.getAddress());
        newProfile.setCity(profileRequest.getCity());
        newProfile.setState(profileRequest.getState());
        newProfile.setZipCode(profileRequest.getZipCode());
        newProfile.setPhone(profileRequest.getPhone());
        newProfile.setUser(dbUser);
        if (dbProfile.isPresent()) {
            newProfile.setId(dbProfile.get().getId());
        }
        return ResponseEntity.ok(userService.saveProfile(newProfile));
    }
}