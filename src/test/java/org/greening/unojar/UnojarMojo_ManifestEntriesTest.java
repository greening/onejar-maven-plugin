package org.greening.unojar;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UnojarMojo_ManifestEntriesTest {
	@BeforeAll
	static void initAll() {
	}

	/**
	 * we might want to use more sensible tests,
	 * but for now I would just like to see whether new changes
	 * break older options.
	 */
	
	@Test
	public void whenManifestEntriesSpecifiedThenNoFailure() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("filename", "test-1.one-jar.jar");
		mojo.set("manifestEntries", new HashMap<String, String>());
		mojo.execute();
	}

	@Test
	public void whenManifestEntriesMissingThenNoFailure() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("filename", "test-2.one-jar.jar");
		mojo.execute();
	}
	@Test
	public void whenOneJar_0_97_patched() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("bootfile", "one-jar-boot-0.97-patched");
		mojo.set("filename", "test-3.one-jar.jar");
		mojo.execute();
	}
	@Test
	public void when_unojar_core_1_0_1() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("bootfile", "unojar-core-1.0.1");
		mojo.set("filename", "test-4.uno-jar.jar");
		mojo.execute();
	}
	@Test
	public void when_unojar_core_1_0_1_jar() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("bootfile", "classes/unojar-core-1.0.1.jar");
		mojo.set("filename", "test-5.uno-jar.jar");
		mojo.execute();
	}
	@Test
	public void when_not_exists() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("bootfile", "NOTVALID-core-1.0.1");
		mojo.set("filename", "test-6.uno-jar.jar");
		Assertions.assertThrows(MojoExecutionException.class, () -> mojo.execute());
	}
	@Test
	public void when_not_exists_jar() throws Exception {
		TestableMojo mojo = new TestableMojo().useDefaultTestArtifact();
		mojo.set("bootfile", "classes/NOTVALID-core-1.0.1.jar");
		mojo.set("filename", "test-7.uno-jar.jar");
		Assertions.assertThrows(MojoExecutionException.class, () -> mojo.execute());
	}

}
