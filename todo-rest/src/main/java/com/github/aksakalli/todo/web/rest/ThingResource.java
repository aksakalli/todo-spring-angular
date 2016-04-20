package com.github.aksakalli.todo.web.rest;

import com.github.aksakalli.todo.domain.Thing;
import com.github.aksakalli.todo.domain.User;
import com.github.aksakalli.todo.repository.ThingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST endpoint for Thing.
 */
@RestController
@RequestMapping("/api")
public class ThingResource {
    private final Logger log = LoggerFactory.getLogger(ThingResource.class);


    @Inject
    private ThingRepository thingRepository;

    /**
     * GET  /things -> get all things.
     */
    @RequestMapping(value = "/things",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Thing> getAll() {
        log.debug("REST request to get all User");
        return thingRepository.findAll();
    }

    /**
     * GET  /things/:id -> get "id" thing.
     */
    @RequestMapping(value = "/things/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thing> get(@PathVariable Long id) {
        return thingRepository.findOneById(id)
            .map(thing -> new ResponseEntity<>(thing, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST  /things -> Create a new thing.
     */
    @RequestMapping(value = "/things",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Thing thing) throws URISyntaxException {
        log.debug("REST request to save Thing : {}", thing);
        if (thing.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new thing cannot already have an ID").build();
        }
        thingRepository.save(thing);
        return ResponseEntity.created(new URI("/api/things/" + thing.getId())).build();
    }


    /**
     * PUT  /things -> Updates an existing thing.
     */
    @RequestMapping(value = "/things/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody Thing thing) throws URISyntaxException {
        log.debug("REST request to update Thing : {}", thing);
        Thing currentThing = thingRepository.findOne(id);
        currentThing.setTitle(thing.getTitle());
        currentThing.setContent(thing.getContent());
        thingRepository.save(currentThing);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE  /things/:id -> delete the "id" thing.
     */
    @RequestMapping(value = "/things/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Things : {}", id);
        thingRepository.delete(id);
    }

}
