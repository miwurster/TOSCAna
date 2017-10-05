package org.opentosca.toscana.core.api;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentosca.toscana.core.transformation.Platform;
import org.opentosca.toscana.core.util.PlatformProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PlatformController.class, secure = false)
public class PlatformControllerTest {
	
	private static Platform[] platforms = {
		new Platform("kubernetes","Kubernetes", new HashSet<>()),
		new Platform("open-stack","OpenStack", new HashSet<>()),
		new Platform("cloud-foundry","CloudFoundry",new HashSet<>())
	};
	
	private static List<String> ids = Arrays.asList("kubernetes", "open-stack", "cloud-foundry");
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PlatformProvider provider;
	
	@Before
	public void setUp() throws Exception {
		when(provider.getSupportedPlatforms()).thenReturn(Arrays.asList(platforms));
		for (int i = 0; i < platforms.length; i++) {
			Platform p = platforms[i];
			when(provider.findById(p.id)).thenReturn(p);
		}
	}

	@Test
	public void listPlatforms() throws Exception {
		ResultActions resultActions = mvc.perform(
			get("/platforms")
		).andDo(print()).andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType("application/hal+json;charset=UTF-8"));
		resultActions.andExpect(jsonPath("$._embedded.platform").isArray());
		resultActions.andExpect(jsonPath("$._embedded.platform[3]").doesNotExist());
		for (int i = 0; i < 3; i++) {
			resultActions.andExpect(jsonPath("$._embedded.platform["+i+"].id").isString());
			resultActions.andExpect(jsonPath("$._embedded.platform["+i+"].name").isString());
		}
		resultActions.andReturn();
	}

	@Test
	public void platformDetails() throws Exception {
		for (Platform platform : platforms) {
			ResultActions resultActions = mvc.perform(
				get("/platforms/"+platform.id)
			).andDo(print());
			resultActions.andExpect(jsonPath("$.id").value(platform.id));
			resultActions.andExpect(jsonPath("$.name").value(platform.name));
			resultActions.andExpect(jsonPath("$._links.self.href").isString());
			resultActions.andReturn();
		}
	}
	@Test
	public void platformDetails404() throws Exception {
		ResultActions resultActions = mvc.perform(
			get("/platforms/notaplatform")
		).andDo(print());
		resultActions.andExpect(status().isNotFound());
		resultActions.andReturn();
	}
}
