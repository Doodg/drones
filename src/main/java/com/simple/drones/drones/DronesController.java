package com.simple.drones.drones;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/drones")
@RestController
public class DronesController {


    @GetMapping("/all")
    private void  getAllDrones(){

    }
}
