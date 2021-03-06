package org.butterfaces.component.renderkit.html_basic.text.util;

import org.butterfaces.component.html.text.HtmlTags;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FreeTextSeparatorsTest {

    @Test
    void getFreeTextSeparators() {
        final HtmlTags tags = mock(HtmlTags.class);
        when(tags.getConfirmKeys())
            .thenReturn(",")
            .thenReturn(", ")
            .thenReturn(null)
            .thenReturn("")
            .thenReturn("; ,");

        assertThat(FreeTextSeparators.getFreeTextSeparators(tags)).containsExactly(",");
        assertThat(FreeTextSeparators.getFreeTextSeparators(tags)).containsExactly(",", " ");
        assertThat(FreeTextSeparators.getFreeTextSeparators(tags)).containsExactly(",", " ");
        assertThat(FreeTextSeparators.getFreeTextSeparators(tags)).containsExactly(",", " ");
        assertThat(FreeTextSeparators.getFreeTextSeparators(tags)).containsExactly(";", " ", ",");
    }
}