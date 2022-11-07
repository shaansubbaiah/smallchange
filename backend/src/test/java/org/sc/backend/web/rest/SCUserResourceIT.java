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
import org.sc.backend.domain.ScUser;
import org.sc.backend.domain.enumeration.UserRoles;
import org.sc.backend.repository.ScUserRepository;
import org.sc.backend.web.rest.admin.ScUserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ScUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "o@Ul.2RAzZ.rYF.Na6";
    private static final String UPDATED_EMAIL = "ecwU@_KbiT.xy6u4.V.9RL";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final UserRoles DEFAULT_SC_USER_ROLE = UserRoles.USER;
    private static final UserRoles UPDATED_SC_USER_ROLE = UserRoles.ADMIN;

    private static final Boolean DEFAULT_SC_USER_ENABLED = false;
    private static final Boolean UPDATED_SC_USER_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/sc-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{scUserId}";

    @Autowired
    private ScUserRepository scUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScUserMockMvc;

    private ScUser scUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScUser createEntity(EntityManager em) {
        ScUser scUser = new ScUser()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .scUserRole(DEFAULT_SC_USER_ROLE)
            .scUserEnabled(DEFAULT_SC_USER_ENABLED);
        return scUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScUser createUpdatedEntity(EntityManager em) {
        ScUser scUser = new ScUser()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .scUserRole(UPDATED_SC_USER_ROLE)
            .scUserEnabled(UPDATED_SC_USER_ENABLED);
        return scUser;
    }

    @BeforeEach
    public void initTest() {
        scUser = createEntity(em);
    }

    @Test
    @Transactional
    void createScUser() throws Exception {
        int databaseSizeBeforeCreate = scUserRepository.findAll().size();
        // Create the ScUser
        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isCreated());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeCreate + 1);
        ScUser testScUser = scUserList.get(scUserList.size() - 1);
        assertThat(testScUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testScUser.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testScUser.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testScUser.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testScUser.getScUserRole()).isEqualTo(DEFAULT_SC_USER_ROLE);
        assertThat(testScUser.getScUserEnabled()).isEqualTo(DEFAULT_SC_USER_ENABLED);
    }

    @Test
    @Transactional
    void createScUserWithExistingId() throws Exception {
        // Create the ScUser with an existing ID
        scUser.setScUserId("existing_id");

        int databaseSizeBeforeCreate = scUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isBadRequest());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scUserRepository.findAll().size();
        // set the field null
        scUser.setName(null);

        // Create the ScUser, which fails.

        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isBadRequest());

        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = scUserRepository.findAll().size();
        // set the field null
        scUser.setEmail(null);

        // Create the ScUser, which fails.

        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isBadRequest());

        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = scUserRepository.findAll().size();
        // set the field null
        scUser.setPasswordHash(null);

        // Create the ScUser, which fails.

        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isBadRequest());

        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkScUserRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = scUserRepository.findAll().size();
        // set the field null
        scUser.setScUserRole(null);

        // Create the ScUser, which fails.

        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isBadRequest());

        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkScUserEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = scUserRepository.findAll().size();
        // set the field null
        scUser.setScUserEnabled(null);

        // Create the ScUser, which fails.

        restScUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isBadRequest());

        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllScUsers() throws Exception {
        // Initialize the database
        scUser.setScUserId(UUID.randomUUID().toString());
        scUserRepository.saveAndFlush(scUser);

        // Get all the scUserList
        restScUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=scUserId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(scUser.getScUserId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].scUserRole").value(hasItem(DEFAULT_SC_USER_ROLE.toString())))
            .andExpect(jsonPath("$.[*].scUserEnabled").value(hasItem(DEFAULT_SC_USER_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    void getScUser() throws Exception {
        // Initialize the database
        scUser.setScUserId(UUID.randomUUID().toString());
        scUserRepository.saveAndFlush(scUser);

        // Get the scUser
        restScUserMockMvc
            .perform(get(ENTITY_API_URL_ID, scUser.getScUserId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.scUserId").value(scUser.getScUserId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.scUserRole").value(DEFAULT_SC_USER_ROLE.toString()))
            .andExpect(jsonPath("$.scUserEnabled").value(DEFAULT_SC_USER_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingScUser() throws Exception {
        // Get the scUser
        restScUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScUser() throws Exception {
        // Initialize the database
        scUser.setScUserId(UUID.randomUUID().toString());
        scUserRepository.saveAndFlush(scUser);

        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();

        // Update the scUser
        ScUser updatedScUser = scUserRepository.findById(scUser.getScUserId()).get();
        // Disconnect from session so that the updates on updatedScUser are not directly saved in db
        em.detach(updatedScUser);
        updatedScUser
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .scUserRole(UPDATED_SC_USER_ROLE)
            .scUserEnabled(UPDATED_SC_USER_ENABLED);

        restScUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScUser.getScUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScUser))
            )
            .andExpect(status().isOk());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
        ScUser testScUser = scUserList.get(scUserList.size() - 1);
        assertThat(testScUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testScUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testScUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testScUser.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testScUser.getScUserRole()).isEqualTo(UPDATED_SC_USER_ROLE);
        assertThat(testScUser.getScUserEnabled()).isEqualTo(UPDATED_SC_USER_ENABLED);
    }

    @Test
    @Transactional
    void putNonExistingScUser() throws Exception {
        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();
        scUser.setScUserId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scUser.getScUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScUser() throws Exception {
        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();
        scUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScUser() throws Exception {
        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();
        scUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScUserWithPatch() throws Exception {
        // Initialize the database
        scUser.setScUserId(UUID.randomUUID().toString());
        scUserRepository.saveAndFlush(scUser);

        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();

        // Update the scUser using partial update
        ScUser partialUpdatedScUser = new ScUser();
        partialUpdatedScUser.setScUserId(scUser.getScUserId());

        partialUpdatedScUser
            .name(UPDATED_NAME)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .scUserRole(UPDATED_SC_USER_ROLE);

        restScUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScUser.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScUser))
            )
            .andExpect(status().isOk());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
        ScUser testScUser = scUserList.get(scUserList.size() - 1);
        assertThat(testScUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testScUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testScUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testScUser.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testScUser.getScUserRole()).isEqualTo(UPDATED_SC_USER_ROLE);
        assertThat(testScUser.getScUserEnabled()).isEqualTo(DEFAULT_SC_USER_ENABLED);
    }

    @Test
    @Transactional
    void fullUpdateScUserWithPatch() throws Exception {
        // Initialize the database
        scUser.setScUserId(UUID.randomUUID().toString());
        scUserRepository.saveAndFlush(scUser);

        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();

        // Update the scUser using partial update
        ScUser partialUpdatedScUser = new ScUser();
        partialUpdatedScUser.setScUserId(scUser.getScUserId());

        partialUpdatedScUser
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .scUserRole(UPDATED_SC_USER_ROLE)
            .scUserEnabled(UPDATED_SC_USER_ENABLED);

        restScUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScUser.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScUser))
            )
            .andExpect(status().isOk());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
        ScUser testScUser = scUserList.get(scUserList.size() - 1);
        assertThat(testScUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testScUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testScUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testScUser.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testScUser.getScUserRole()).isEqualTo(UPDATED_SC_USER_ROLE);
        assertThat(testScUser.getScUserEnabled()).isEqualTo(UPDATED_SC_USER_ENABLED);
    }

    @Test
    @Transactional
    void patchNonExistingScUser() throws Exception {
        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();
        scUser.setScUserId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scUser.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScUser() throws Exception {
        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();
        scUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScUser() throws Exception {
        int databaseSizeBeforeUpdate = scUserRepository.findAll().size();
        scUser.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScUser in the database
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScUser() throws Exception {
        // Initialize the database
        scUser.setScUserId(UUID.randomUUID().toString());
        scUserRepository.saveAndFlush(scUser);

        int databaseSizeBeforeDelete = scUserRepository.findAll().size();

        // Delete the scUser
        restScUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, scUser.getScUserId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScUser> scUserList = scUserRepository.findAll();
        assertThat(scUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
