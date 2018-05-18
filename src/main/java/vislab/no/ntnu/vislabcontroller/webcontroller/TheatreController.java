package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;

import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/theatre")
public class TheatreController {
    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<Theatre>> getAll() {
        return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Theatre>> add(@RequestBody Theatre[] theatreArray) {
        List<Theatre> theatres = new ArrayList<>(Arrays.asList(theatreArray));
        return new ResponseEntity<>(theatreRepository.saveAll(theatres), HttpStatus.OK);
    }

    @RequestMapping(value = "/update",
    method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // TODO: this shit
    public ResponseEntity<Theatre> update(ServletRequest request){
        return null;
    }

    @RequestMapping(value = "/getgroups")
    public ResponseEntity<List<DeviceGroup>> getGroups(@RequestParam ("theatrename") String theatreName){
        Theatre theatre = theatreRepository.findByTheatreName(theatreName);
        if(theatre!= null){
            List<DeviceGroup> groups = deviceGroupRepository.findAllByTheatre(theatre);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @RequestMapping(value = "/remove"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestBody Theatre[] theatreArray) {
        List<Theatre> theatres = new ArrayList<>(Arrays.asList(theatreArray));
        theatreRepository.deleteAll(theatres);
        return new ResponseEntity<>("Removed theatres", HttpStatus.OK);
    }
}
