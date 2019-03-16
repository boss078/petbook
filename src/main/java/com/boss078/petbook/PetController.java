package com.boss078.petbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller    // This means that this class is a Controller
@RequestMapping(path="pet") // This means URL's start with /pet (after Application path)
public class PetController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("all")
    public @ResponseBody Iterable<Pet> getAllPets() {
        // This returns a JSON or XML with the users
        return petRepository.findAll();
    }

    @GetMapping("find")
    public @ResponseBody Optional<Pet> getPet(@RequestParam Integer id) {
        if (petRepository.existsById(id)) {
            return petRepository.findById(id);
        }
        else {
            return null;
        }
    }

    @GetMapping("user")
    public @ResponseBody User getUser(@RequestParam Integer id) {
        if (petRepository.existsById(id)) {
            return petRepository.findById(id).get().getUser();
        }
        else {
            return null;
        }
    }


    @PutMapping("update")
    public @ResponseBody String updatePet(@RequestParam Integer id, @RequestParam String newNickname
            , @RequestParam String newType, @RequestParam Integer newAge) {
        if (petRepository.existsById(id)) {
            Pet n = petRepository.findById(id).get();
            if (newNickname != null && !newNickname.isEmpty())
                n.setNickname(newNickname);
            if (newType != null && !newType.isEmpty())
                n.setType(newType);
            if (newAge != null)
                n.setAge(newAge);
            petRepository.save(n);
            return "Updated.";
        }
        else {
            return "Not updated, Pet with this id is not exist.";
        }
    }

    @PostMapping(path="pet")
    public @ResponseBody String addNewPet(@RequestParam Integer idOfHostUser, @RequestParam String nickname
            , @RequestParam String type, @RequestParam Integer age) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        if (userRepository.existsById(idOfHostUser)) {
            Pet n = new Pet();
            n.setNickname(nickname);
            n.setType(type);
            n.setAge(age);

            User host = userRepository.findById(idOfHostUser).get();
            host.addPet(n);
            userRepository.save(host);
            return "Saved.";
        }
        else {
            return "Not saved, User with this id is not exist.";
        }
    }

    @DeleteMapping("delete")
    public @ResponseBody String deletePet(@RequestParam Integer id, @RequestParam Integer idOfHostUser) {
        if (petRepository.existsById(id) && userRepository.existsById(idOfHostUser)) {
            Pet n = petRepository.findById(id).get();
            User host = userRepository.findById(id).get();
            host.removePet(n);
            userRepository.save(host);
            petRepository.deleteById(id);
            return "Deleted.";
        }
        else {
            return "Not deleted, Pet or User with this id is not exist.";
        }
    }

}