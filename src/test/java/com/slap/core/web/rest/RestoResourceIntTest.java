package com.slap.core.web.rest;

import com.slap.core.SlapApp;

import com.slap.core.domain.Resto;
import com.slap.core.repository.RestoRepository;
import com.slap.core.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RestoResource REST controller.
 *
 * @see RestoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlapApp.class)
public class RestoResourceIntTest {

    private static final String DEFAULT_RESTO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESTO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESTO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_RESTO_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RESTO_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_RESTO_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private RestoRepository restoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRestoMockMvc;

    private Resto resto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestoResource restoResource = new RestoResource(restoRepository);
        this.restRestoMockMvc = MockMvcBuilders.standaloneSetup(restoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resto createEntity(EntityManager em) {
        Resto resto = new Resto()
            .restoName(DEFAULT_RESTO_NAME)
            .restoAddress(DEFAULT_RESTO_ADDRESS)
            .restoPhoneNumber(DEFAULT_RESTO_PHONE_NUMBER);
        return resto;
    }

    @Before
    public void initTest() {
        resto = createEntity(em);
    }

    @Test
    @Transactional
    public void createResto() throws Exception {
        int databaseSizeBeforeCreate = restoRepository.findAll().size();

        // Create the Resto
        restRestoMockMvc.perform(post("/api/restos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resto)))
            .andExpect(status().isCreated());

        // Validate the Resto in the database
        List<Resto> restoList = restoRepository.findAll();
        assertThat(restoList).hasSize(databaseSizeBeforeCreate + 1);
        Resto testResto = restoList.get(restoList.size() - 1);
        assertThat(testResto.getRestoName()).isEqualTo(DEFAULT_RESTO_NAME);
        assertThat(testResto.getRestoAddress()).isEqualTo(DEFAULT_RESTO_ADDRESS);
        assertThat(testResto.getRestoPhoneNumber()).isEqualTo(DEFAULT_RESTO_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createRestoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restoRepository.findAll().size();

        // Create the Resto with an existing ID
        resto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestoMockMvc.perform(post("/api/restos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Resto> restoList = restoRepository.findAll();
        assertThat(restoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRestos() throws Exception {
        // Initialize the database
        restoRepository.saveAndFlush(resto);

        // Get all the restoList
        restRestoMockMvc.perform(get("/api/restos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resto.getId().intValue())))
            .andExpect(jsonPath("$.[*].restoName").value(hasItem(DEFAULT_RESTO_NAME.toString())))
            .andExpect(jsonPath("$.[*].restoAddress").value(hasItem(DEFAULT_RESTO_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].restoPhoneNumber").value(hasItem(DEFAULT_RESTO_PHONE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getResto() throws Exception {
        // Initialize the database
        restoRepository.saveAndFlush(resto);

        // Get the resto
        restRestoMockMvc.perform(get("/api/restos/{id}", resto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resto.getId().intValue()))
            .andExpect(jsonPath("$.restoName").value(DEFAULT_RESTO_NAME.toString()))
            .andExpect(jsonPath("$.restoAddress").value(DEFAULT_RESTO_ADDRESS.toString()))
            .andExpect(jsonPath("$.restoPhoneNumber").value(DEFAULT_RESTO_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResto() throws Exception {
        // Get the resto
        restRestoMockMvc.perform(get("/api/restos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResto() throws Exception {
        // Initialize the database
        restoRepository.saveAndFlush(resto);
        int databaseSizeBeforeUpdate = restoRepository.findAll().size();

        // Update the resto
        Resto updatedResto = restoRepository.findOne(resto.getId());
        updatedResto
            .restoName(UPDATED_RESTO_NAME)
            .restoAddress(UPDATED_RESTO_ADDRESS)
            .restoPhoneNumber(UPDATED_RESTO_PHONE_NUMBER);

        restRestoMockMvc.perform(put("/api/restos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResto)))
            .andExpect(status().isOk());

        // Validate the Resto in the database
        List<Resto> restoList = restoRepository.findAll();
        assertThat(restoList).hasSize(databaseSizeBeforeUpdate);
        Resto testResto = restoList.get(restoList.size() - 1);
        assertThat(testResto.getRestoName()).isEqualTo(UPDATED_RESTO_NAME);
        assertThat(testResto.getRestoAddress()).isEqualTo(UPDATED_RESTO_ADDRESS);
        assertThat(testResto.getRestoPhoneNumber()).isEqualTo(UPDATED_RESTO_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingResto() throws Exception {
        int databaseSizeBeforeUpdate = restoRepository.findAll().size();

        // Create the Resto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRestoMockMvc.perform(put("/api/restos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resto)))
            .andExpect(status().isCreated());

        // Validate the Resto in the database
        List<Resto> restoList = restoRepository.findAll();
        assertThat(restoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResto() throws Exception {
        // Initialize the database
        restoRepository.saveAndFlush(resto);
        int databaseSizeBeforeDelete = restoRepository.findAll().size();

        // Get the resto
        restRestoMockMvc.perform(delete("/api/restos/{id}", resto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resto> restoList = restoRepository.findAll();
        assertThat(restoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resto.class);
        Resto resto1 = new Resto();
        resto1.setId(1L);
        Resto resto2 = new Resto();
        resto2.setId(resto1.getId());
        assertThat(resto1).isEqualTo(resto2);
        resto2.setId(2L);
        assertThat(resto1).isNotEqualTo(resto2);
        resto1.setId(null);
        assertThat(resto1).isNotEqualTo(resto2);
    }
}
