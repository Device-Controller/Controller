package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;

/**
 * @author ThomasSTodal
 *
 * Controller for managing the Theatre entity. Supports CRUD to a database through form submissions.
 */
@Controller
@RequestMapping("/api/theatre")
public class TheatreController {
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    DeviceGroupRepository deviceGroupRepository;
    @Autowired
    DeviceRepository deviceRepository;

    /**
     *
     * @return List containing all Theatres in the database.
     */
    @RequestMapping("/getall")
    public ResponseEntity<List<Theatre>> getAll() {
        return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Converts form data to a Theatre object and stores it in a TheatreRepository
     * @param form Form data
     * @return REsponseEntity with the stored Theatre, or BAD_REQUEST if invalid form data
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Theatre> add(ServletRequest form) {
        Theatre theatre = parseRequest(form);
        if (theatre != null) {
            if(theatreRepository.findByTheatreName(theatre.getTheatreName()) == null) {
                return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates a Theatre object with the provided form data
     * @param request Form data
     * @return ResponseEntity with the updated Theatre, or BAD_REQUEST if invalid form data
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Theatre> update(ServletRequest request) {
        Theatre theatre = parseRequest(request);
        if (theatre != null) {
            return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param theatreName Name of Theatre
     * @return ResponseEntity with all groups associated with the given Theatre, or BAD_REQUEST if invalid TheatreName
     */
    @RequestMapping(value = "/getgroups")
    public ResponseEntity<List<DeviceGroup>> getGroups(@RequestParam("theatrename") String theatreName) {
        Theatre theatre = theatreRepository.findByTheatreName(theatreName);
        if (theatre != null) {
            List<DeviceGroup> groups = deviceGroupRepository.findAllByTheatre(theatre);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param theatreName Name of Theatre
     * @return ResponseEntity with all devices associated with the given Theatre, or BAD_REQUEST if invalid TheatreName
     */
    @RequestMapping(value = "/getdevices")
    public ResponseEntity<List<Device>> getDevices(@RequestParam("theatrename") String theatreName) {
        Theatre theatre = theatreRepository.findByTheatreName(theatreName);
        if (theatre != null) {
            return new ResponseEntity<>(theatre.getDevices(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Removes the Theatre matching the given id
     * @param id Theatre id
     * @return ResponseEntity conforming that the Theatre has been removed, or BAD_REQUEST if invalid id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/remove"
            , method = RequestMethod.DELETE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestParam("id") int id) {
        Theatre theatre = null;
        if (theatreRepository.findById(id).isPresent()) {
            theatre = theatreRepository.findById(id).get();
            theatreRepository.delete(theatre);
            return new ResponseEntity<>("Removed Theatre:" + theatre.getId(), HttpStatus.OK);
        } return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Attempts to parse the given form data into a Theatre object
     * @param request Form data
     * @return The new Theatre object, or null if invalid form data
     */
    private Theatre parseRequest(ServletRequest request) {
        String id = request.getParameter("id");
        String theatreName = request.getParameter("name");
        Theatre theatre = null;
        if (id != null && !id.isEmpty()) {
            try {
                int idNum = Integer.parseInt(id);
                if (theatreRepository.findById(idNum).isPresent()) {
                    theatre = theatreRepository.findById(idNum).get();
                    theatre.setTheatreName(theatreName);
                }
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            theatre = new Theatre(theatreName);
        }
        String[] deviceIds = request.getParameterValues("device-id");
        List<Device> devices = new ArrayList<>();
        try {
            for (int i = 0; i < deviceIds.length; i++) {
                int anInt = Integer.parseInt(deviceIds[i]);
                if (deviceRepository.findById(anInt).isPresent()) {
                    devices.add(deviceRepository.findById(anInt).get());
                }
            }
            theatre.removeAllDevices();
            theatre.getDevices().addAll(devices);
            return theatre;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
