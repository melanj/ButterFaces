package org.butterfaces.component.html.resourcelibraries;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlActivateLibrariesTest {

    @Test
    @Disabled
    void testAssertThatAllResourcesThatAreAnnotatedAreFoundInResourcesFolders() {
        final List<ResourceDependency> resourceDependencies = this.loadResourceDependencies();

        for (ResourceDependency resourceDependency : resourceDependencies) {
            if ("butterfaces-dist-js".equals(resourceDependency.library())) {
                // will be checked in other test
                continue;
            }

            final List<String> fileNames = this.loadResourcesFileNamesFromResourceSubDirectory(resourceDependency.library());

            boolean foundResourceByName = false;

            for (String fileName : fileNames) {
                if (fileName.equals(resourceDependency.name())) {
                    foundResourceByName = true;
                    break;
                }
            }

            assertThat(foundResourceByName).isTrue();
        }
    }

    @Test
    @Disabled
    void testAssertThatAllResourcesThatAreAnnotatedAreFoundInResourcesFoldersCreatedByLess() {
        final List<ResourceDependency> resourceDependencies = this.loadResourceDependencies();

        for (ResourceDependency resourceDependency : resourceDependencies) {
            if (!"butterfaces-dist".equals(resourceDependency.library())) {
                // will be checked in other test
                continue;
            }

            final List<String> fileNames = this.loadResourcesFileNamesFromResourceSubDirectory(resourceDependency.library() + "/css");

            boolean foundResourceByName = false;

            for (String fileName : fileNames) {
                if (("css/" + fileName).equals(resourceDependency.name())) {
                    foundResourceByName = true;
                    break;
                }
            }

            assertThat(foundResourceByName).isTrue();
        }
    }

    @Test
    @Disabled
    void testAssertThatAllResourcesAreAnnotatedInResourceComponent() {
        final List<ResourceDependency> resourceDependencies = this.loadResourceDependencies();

        this.assertResourcesInSubDirectory(resourceDependencies, "butterfaces-dist-bundle-dev-js");
        this.assertResourcesInSubDirectory(resourceDependencies, "butterfaces-js");
    }

    private void assertResourcesInSubDirectory(List<ResourceDependency> resourceDependencies, String subFolder) {
        for (String fileName : this.loadResourcesFileNamesFromResourceSubDirectory(subFolder)) {
            boolean foundResource = false;

            for (ResourceDependency resourceDependency : resourceDependencies) {
                if (fileName.equals(resourceDependency.name()) && subFolder.equals(resourceDependency.library())) {
                    foundResource = true;
                    break;
                }
            }

            assertThat(foundResource).isTrue();
        }
    }

    private List<String> loadResourcesFileNamesFromResourceSubDirectory(final String subFolder) {
        final List<String> fileNames = new ArrayList<>();
        final URL resource = getClass().getResource("/META-INF/resources/" + subFolder + "/");

        for (File resourceFile : new File(resource.getFile()).listFiles()) {
            if (!resourceFile.getName().endsWith(".gz")) {
                fileNames.add(resourceFile.getName());
            }
        }

        return fileNames;
    }

    private List<ResourceDependency> loadResourceDependencies() {
        final Annotation[] annotations = HtmlActivateLibraries.class.getAnnotations();

        final List<ResourceDependency> resourceDependencies = new ArrayList<>();

        for (Annotation annotation : annotations) {
            if (annotation instanceof ResourceDependencies) {
                final ResourceDependencies resourceDependenciesAnnotation = (ResourceDependencies) annotation;
                resourceDependencies.addAll(Arrays.asList(resourceDependenciesAnnotation.value()));
            }
        }

        return resourceDependencies;
    }
}
