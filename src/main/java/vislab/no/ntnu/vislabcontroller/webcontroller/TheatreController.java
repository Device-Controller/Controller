package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/theatre")
public class TheatreController {
    @Autowired
    TheatreRepository theatreRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<Theatre>> getAll() {
        return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/addone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Theatre> addOne(@RequestBody Theatre theatre) {
        return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
    }

    @RequestMapping(value = "/addlist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Theatre>> addList(@RequestBody Theatre[] theatreArray) {
        List<Theatre> theatres = new ArrayList<>(Arrays.asList(theatreArray));
        return new ResponseEntity<>(theatreRepository.saveAll(theatres), HttpStatus.OK);
    }

    @RequestMapping(value = "/removeone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeOne(@RequestBody Theatre theatre) {
        theatreRepository.delete(theatre);
        return new ResponseEntity<>("Removed theatre: "
                + theatre.getTheatreName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/removelist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeList(@RequestBody Theatre[] theatreArray) {
        List<Theatre> theatres = new ArrayList<>(Arrays.asList(theatreArray));
        return new ResponseEntity<>("Removed theatres", HttpStatus.OK);
    }

    @RequestMapping("/removeall")
    public ResponseEntity<String> removeAll() {
        theatreRepository.deleteAll();
        return new ResponseEntity<>("Removed all theatres", HttpStatus.OK);
    }
}
