package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class PreferencesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Preferences.class);
        Preferences preferences1 = new Preferences();
        preferences1.setScUserId("id1");
        Preferences preferences2 = new Preferences();
        preferences2.setScUserId(preferences1.getScUserId());
        assertThat(preferences1).isEqualTo(preferences2);
        preferences2.setScUserId("id2");
        assertThat(preferences1).isNotEqualTo(preferences2);
        preferences1.setScUserId(null);
        assertThat(preferences1).isNotEqualTo(preferences2);
    }
}
