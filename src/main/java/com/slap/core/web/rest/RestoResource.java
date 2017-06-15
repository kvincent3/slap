package com.slap.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.slap.core.domain.Resto;

import com.slap.core.repository.RestoRepository;
import com.slap.core.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Resto.
 */
@RestController
@RequestMapping("/api")
public class RestoResource {

    private final Logger log = LoggerFactory.getLogger(RestoResource.class);

    private static final String ENTITY_NAME = "resto";

    private final RestoRepository restoRepository;

    public RestoResource(RestoRepository restoRepository) {
        this.restoRepository = restoRepository;
    }

    /**
     * POST  /restos : Create a new resto.
     *
     * @param resto the resto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resto, or with status 400 (Bad Request) if the resto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restos")
    @Timed
    public ResponseEntity<Resto> createResto(@RequestBody Resto resto) throws URISyntaxException {
        log.debug("REST request to save Resto : {}", resto);
        if (resto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new resto cannot already have an ID")).body(null);
        }
        Resto result = restoRepository.save(resto);
        return ResponseEntity.created(new URI("/api/restos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restos : Updates an existing resto.
     *
     * @param resto the resto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resto,
     * or with status 400 (Bad Request) if the resto is not valid,
     * or with status 500 (Internal Server Error) if the resto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restos")
    @Timed
    public ResponseEntity<Resto> updateResto(@RequestBody Resto resto) throws URISyntaxException {
        log.debug("REST request to update Resto : {}", resto);
        if (resto.getId() == null) {
            return createResto(resto);
        }
        Resto result = restoRepository.save(resto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restos : get all the restos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restos in body
     */
    @GetMapping("/restos")
    @Timed
    public List<Resto> getAllRestos() {
        log.debug("REST request to get all Restos");
        return restoRepository.findAll();
    }

    /**
     * GET  /restos/:id : get the "id" resto.
     *
     * @param id the id of the resto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resto, or with status 404 (Not Found)
     */
    @GetMapping("/restos/{id}")
    @Timed
    public ResponseEntity<Resto> getResto(@PathVariable Long id) {
        log.debug("REST request to get Resto : {}", id);
        Resto resto = restoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resto));
    }

    /**
     * DELETE  /restos/:id : delete the "id" resto.
     *
     * @param id the id of the resto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restos/{id}")
    @Timed
    public ResponseEntity<Void> deleteResto(@PathVariable Long id) {
        log.debug("REST request to delete Resto : {}", id);
        restoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
