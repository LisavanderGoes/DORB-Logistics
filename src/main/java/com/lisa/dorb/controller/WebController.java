package com.lisa.dorb.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    /*@Autowired
    KlantRepository repository;
    @Autowired
    AdminRepository managerRepository;

    @RequestMapping("/test")
    public String test(){
        List<Klant> dbnaam = repository.findByInlognaam("stan");
        return "test";
    }

    @RequestMapping("/save")
    public String process(){
        repository.save(new Klant("Jack", "van der", "Stan", "123456789012345678", "stan", "123"));
        return "Done";
    }


    @RequestMapping("/findall")
    public String findAll(){
        String result = "<html>";

        for(Klant cust : repository.findAll()){
            result += "<div>" + cust.toString() + "</div>";
        }

        return result + "</html>";
    }

    @RequestMapping("/findbyiAchtd")
    public String findById(@RequestParam("id") long id){
        String result = "";
        result = repository.findById(id).toString();
        return result;
    }

    @RequestMapping("/findbylastname")
    public String fetchDataByLastName(@RequestParam("lastname") String lastname){
        String result = "<html>";

        for(Klant cust: repository.findByAchternaam(lastname)){
            result += "<div>" + cust.toString() + "</div>";
        }

        return result + "</html>";
    }*/
}
