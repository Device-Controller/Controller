package no.ntnu.vislab.vislabcontroller.webcontroller;

import no.ntnu.vislab.vislabcontroller.entity.Theatre;
import no.ntnu.vislab.vislabcontroller.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/theatre")
public class TheatreController {
    @Autowired
    TheatreRepository theatreRepository;

    @RequestMapping("/theatres")
    public ResponseEntity<List<Theatre>> getAll() {
        return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/addtheatre")
    public ResponseEntity<Theatre> addTheatre(@RequestParam("theatre") Theatre theatre) {
        return null;
    }
}
