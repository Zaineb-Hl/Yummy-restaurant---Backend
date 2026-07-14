package croco.restau.yummy.controllers;

import croco.restau.yummy.models.Chef;
import croco.restau.yummy.services.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chefs")
public class ChefController {
	
    @Autowired
    private ChefService chefService;

    @PostMapping
    public Chef addChef(@RequestBody Chef chef) {
        return chefService.createChef(chef);
    }

    @PutMapping("/{id}")
    public Chef editChef(@PathVariable Long id, @RequestBody Chef chef) {
        return chefService.updateChef(id, chef);
    }

    @GetMapping
    public List<Chef> getAllChefs() {
        return chefService.getAllChefs();
    }

    @GetMapping("/{id}")
    public Chef getChefById(@PathVariable Long id) {
        return chefService.getChefById(id);
    }

    @GetMapping("/speciality")
    public List<Chef> getBySpeciality(@RequestParam String value) {
        return chefService.getChefsBySpeciality(value);
    }

    @GetMapping("/search")
    public List<Chef> searchByName(@RequestParam String name) {
        return chefService.searchChefsByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteChef(@PathVariable Long id) {
        chefService.deleteChef(id);
    }
}
