package org.sc.backend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sc.backend.IntegrationTest;
import org.sc.backend.domain.SCUser;
import org.sc.backend.repository.SCUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SCUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SCUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "0VT.@1plXNI.NX8.gY0C.yD.n4hR_.bc";
    private static final String UPDATED_EMAIL = "wn0m.0@eUz8.SuPL.zgS0r.QSm.58ef";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/sc-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{scUserId}";

    @Autowired
    private SCUserRepository sCUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSCUserMockMvc;

    private SCUser sCUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCUser createEntity(EntityManager em) {
        SCUser sCUser = new SCUser()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return sCUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCUser createUpdatedEntity(EntityManager em) {
        SCUser sCUser = new SCUser()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return sCUser;
    }

    @BeforeEach
    public void initTest() {
        sCUser = createEntity(em);
    }

    @Test
    @Transactional
    void createSCUser() throws Exception {
        int databaseSizeBeforeCreate = sCUserRepository.findAll().size();
        // Create the SCUser
        restSCUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isCreated());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeCreate + 1);
        SCUser testSCUser = sCUserList.get(sCUserList.size() - 1);
        assertThat(testSCUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSCUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSCUser.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testSCUser.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSCUser.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createSCUserWithExistingId() throws Exception {
        // Create the SCUser with an existing ID
        sCUser.setScUserId("existing_id");

        int databaseSizeBeforeCreate = sCUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSCUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isBadRequest());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCUserRepository.findAll().size();
        // set the field null
        sCUser.setName(null);

        // Create the SCUser, which fails.

        restSCUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isBadRequest());

        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCUserRepository.findAll().size();
        // set the field null
        sCUser.setEmail(null);

        // Create the SCUser, which fails.

        restSCUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isBadRequest());

        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCUserRepository.findAll().size();
        // set the field null
        sCUser.setPasswordHash(null);

        // Create the SCUser, which fails.

        restSCUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isBadRequest());

        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSCUsers() throws Exception {
        // Initialize the database
        sCUser.setScUserId(UUID.randomUUID().toString());
        sCUserRepository.saveAndFlush(sCUser);

        // Get all the sCUserList
        restSCUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=scUserId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(sCUser.getScUserId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getSCUser() throws Exception {
        // Initialize the database
        sCUser.setScUserId(UUID.randomUUID().toString());
        sCUserRepository.saveAndFlush(sCUser);

        // Get the sCUser
        restSCUserMockMvc
            .perform(get(ENTITY_API_URL_ID, sCUser.getScUserId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.scUserId").value(sCUser.getScUserId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingSCUser() throws Exception {
        // Get the sCUser
        restSCUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSCUser() throws Exception {
        // Initialize the database
        sCUser.setScUserId(UUID.randomUUID().toString());
        sCUserRepository.saveAndFlush(sCUser);

        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();

        // Update the sCUser
        SCUser updatedSCUser = sCUserRepository.findById(sCUser.getScUserId()).get();
        // Disconnect from session so that the updates on updatedSCUser are not directly saved in db
        em.detach(updatedSCUser);
        updatedSCUser
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restSCUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSCUser.getScUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSCUser))
            )
            .andExpect(status().isOk());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
        SCUser testSCUser = sCUserList.get(sCUserList.size() - 1);
        assertThat(testSCUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSCUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSCUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testSCUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSCUser.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSCUser() throws Exception {
        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();
        sCUser.setScUserId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSCUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sCUser.getScUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sCUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSCUser() throws Exception {
        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();
        sCUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sCUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSCUser() throws Exception {
        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();
        sCUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSCUserWithPatch() throws Exception {
        // Initialize the database
        sCUser.setScUserId(UUID.randomUUID().toString());
        sCUserRepository.saveAndFlush(sCUser);

        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();

        // Update the sCUser using partial update
        SCUser partialUpdatedSCUser = new SCUser();
        partialUpdatedSCUser.setScUserId(sCUser.getScUserId());

        partialUpdatedSCUser.passwordHash(UPDATED_PASSWORD_HASH).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restSCUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSCUser.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSCUser))
            )
            .andExpect(status().isOk());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
        SCUser testSCUser = sCUserList.get(sCUserList.size() - 1);
        assertThat(testSCUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSCUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSCUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testSCUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSCUser.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSCUserWithPatch() throws Exception {
        // Initialize the database
        sCUser.setScUserId(UUID.randomUUID().toString());
        sCUserRepository.saveAndFlush(sCUser);

        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();

        // Update the sCUser using partial update
        SCUser partialUpdatedSCUser = new SCUser();
        partialUpdatedSCUser.setScUserId(sCUser.getScUserId());

        partialUpdatedSCUser
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restSCUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSCUser.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSCUser))
            )
            .andExpect(status().isOk());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
        SCUser testSCUser = sCUserList.get(sCUserList.size() - 1);
        assertThat(testSCUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSCUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSCUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testSCUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSCUser.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSCUser() throws Exception {
        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();
        sCUser.setScUserId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSCUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sCUser.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sCUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSCUser() throws Exception {
        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();
        sCUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sCUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSCUser() throws Exception {
        int databaseSizeBeforeUpdate = sCUserRepository.findAll().size();
        sCUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sCUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SCUser in the database
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSCUser() throws Exception {
        // Initialize the database
        sCUser.setScUserId(UUID.randomUUID().toString());
        sCUserRepository.saveAndFlush(sCUser);

        int databaseSizeBeforeDelete = sCUserRepository.findAll().size();

        // Delete the sCUser
        restSCUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, sCUser.getScUserId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SCUser> sCUserList = sCUserRepository.findAll();
        assertThat(sCUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
