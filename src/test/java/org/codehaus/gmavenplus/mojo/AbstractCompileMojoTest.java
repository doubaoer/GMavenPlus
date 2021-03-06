/*
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.gmavenplus.mojo;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.codehaus.gmavenplus.model.Version;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.List;
import java.util.Set;


/**
 * Unit tests for the AbstractCompileMojo class.
 *
 * @author Keegan Witt
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractCompileMojoTest {
    private TestMojo testMojo;

    @Mock
    private MavenProject project;

    @Before
    public void setup() {
        testMojo = new TestMojo();
    }

    @Test
    public void testGetSourcesEmpty() {
        testMojo.setSources(new FileSet[] {});
        Set<File> sources = testMojo.getSources();
        Assert.assertEquals(0, sources.size());
    }

    @Test
    public void testGetTestSourcesEmpty() {
        testMojo.setTestSources(new FileSet[] {});
        Set<File> testSources = testMojo.getTestSources();
        Assert.assertEquals(0, testSources.size());
    }

    @Test
    public void testGroovyVersionSupportsActionTrue() {
        testMojo = new TestMojo("1.5.0");
        Assert.assertTrue(testMojo.groovyVersionSupportsAction());
    }

    @Test
    public void testGroovyVersionSupportsActionFalse() {
        testMojo = new TestMojo("1.1-rc-3");
        Assert.assertFalse(testMojo.groovyVersionSupportsAction());
    }

    private class TestMojo extends AbstractCompileMojo {
        private String overrideGroovyVersion = minGroovyVersion.toString();

        protected TestMojo() { }

        protected TestMojo(String overrideGroovyVersion) {
            this.overrideGroovyVersion = overrideGroovyVersion;
        }

        protected Version getGroovyVersion() {
            return Version.parseFromString(overrideGroovyVersion);
        }

        protected List getProjectClasspathElements() throws DependencyResolutionRequiredException {
            return null;
        }

        public void execute() throws MojoExecutionException, MojoFailureException { }

    }

}
