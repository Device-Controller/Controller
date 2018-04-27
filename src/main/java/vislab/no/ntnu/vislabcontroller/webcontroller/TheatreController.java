package vislab.no.ntnu.vislabcontroller.webcontroller;

import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
