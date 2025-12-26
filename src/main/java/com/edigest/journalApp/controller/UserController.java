package com.edigest.journalApp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.api.WeatherResponse;
import com.edigest.journalApp.dto.UserDto;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import com.edigest.journalApp.service.UserService;
import com.edigest.journalApp.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @Operation(summary = "Update the details of an user.")
    @PutMapping("/update")
    // Updates after searching for username
    public ResponseEntity<?> updateUser(@RequestBody UserDto user) {

        // Since this is a secured endpoint, we will use the Authenticated one to verify
        // the user. So when a user updates, it will check the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);

        userInDb.setUserName(user.getUserName());

        userInDb.setPassword(user.getPassword());

        userInDb.setCity(user.getCity());

        userInDb.setSentimentAnalysis(true);

        userService.saveNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete an user.")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody UserDto user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Greetings to view weather according to user's city.")
    @GetMapping("/greeting")
    public ResponseEntity<String> greeting() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);

        String city = userInDb.getCity();

        if (city == null || city.isBlank()) {
            return new ResponseEntity<>(
                "Hi " + userName +
                ", set your city to view the weather.",
                HttpStatus.OK
            );
        }

        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);

            WeatherResponse.Current current = weatherResponse.getCurrent();

            String greeting =
                ", " + city + " weather: " +
                current.getWeatherDescriptions().get(0) +
                ", " + current.getTemperature() +
                "°C (feels like " + current.getFeelslike() + "°C)";

            return new ResponseEntity<>(
                "Hi " + userName + greeting,
                HttpStatus.OK
            );

        } catch (org.springframework.web.client.HttpClientErrorException e) {

            return new ResponseEntity<>(
                "Hi " + userName +
                ", '" + city + "' is an invalid city.",
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                "Hi " + userName +
                ", weather service unavailable.",
                HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

}
