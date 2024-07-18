/*
 * Copyright 2014-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.restdocs.mockmvc;

import java.net.URI;

import jakarta.servlet.ServletContext;
import org.junit.Test;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.head;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.options;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request;

/**
 * Tests for {@link RestDocumentationRequestBuilders}.
 *
 * @author Andy Wilkinson
 *
 */
public class RestDocumentationRequestBuildersTests {

	private final ServletContext servletContext = new MockServletContext();

	@Test
	public void getTemplate() {
		assertTemplate(get("/{template}", "t"), HttpMethod.GET);
	}

	@Test
	public void getUri() {
		assertUri(get(URI.create("/uri")), HttpMethod.GET);
	}

	@Test
	public void postTemplate() {
		assertTemplate(post("/{template}", "t"), HttpMethod.POST);
	}

	@Test
	public void postUri() {
		assertUri(post(URI.create("/uri")), HttpMethod.POST);
	}

	@Test
	public void putTemplate() {
		assertTemplate(put("/{template}", "t"), HttpMethod.PUT);
	}

	@Test
	public void putUri() {
		assertUri(put(URI.create("/uri")), HttpMethod.PUT);
	}

	@Test
	public void patchTemplate() {
		assertTemplate(patch("/{template}", "t"), HttpMethod.PATCH);
	}

	@Test
	public void patchUri() {
		assertUri(patch(URI.create("/uri")), HttpMethod.PATCH);
	}

	@Test
	public void deleteTemplate() {
		assertTemplate(delete("/{template}", "t"), HttpMethod.DELETE);
	}

	@Test
	public void deleteUri() {
		assertUri(delete(URI.create("/uri")), HttpMethod.DELETE);
	}

	@Test
	public void optionsTemplate() {
		assertTemplate(options("/{template}", "t"), HttpMethod.OPTIONS);
	}

	@Test
	public void optionsUri() {
		assertUri(options(URI.create("/uri")), HttpMethod.OPTIONS);
	}

	@Test
	public void headTemplate() {
		assertTemplate(head("/{template}", "t"), HttpMethod.HEAD);
	}

	@Test
	public void headUri() {
		assertUri(head(URI.create("/uri")), HttpMethod.HEAD);
	}

	@Test
	public void requestTemplate() {
		assertTemplate(request(HttpMethod.GET, "/{template}", "t"), HttpMethod.GET);
	}

	@Test
	public void requestUri() {
		assertUri(request(HttpMethod.GET, URI.create("/uri")), HttpMethod.GET);
	}

	@Test
	public void multipartTemplate() {
		assertTemplate(multipart("/{template}", "t"), HttpMethod.POST);
	}

	@Test
	public void multipartUri() {
		assertUri(multipart(URI.create("/uri")), HttpMethod.POST);
	}

	private void assertTemplate(MockHttpServletRequestBuilder builder, HttpMethod httpMethod) {
		MockHttpServletRequest request = builder.buildRequest(this.servletContext);
		assertTemplate(httpMethod, request);
	}

	private void assertTemplate(MockMultipartHttpServletRequestBuilder builder, HttpMethod httpMethod) {
		MockHttpServletRequest request = builder.buildRequest(this.servletContext);
		assertTemplate(httpMethod, request);
	}

	private void assertTemplate(HttpMethod httpMethod, MockHttpServletRequest request) {
		assertThat((String) request.getAttribute(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE))
			.isEqualTo("/{template}");
		assertThat(request.getRequestURI()).isEqualTo("/t");
		assertThat(request.getMethod()).isEqualTo(httpMethod.name());
	}

	private void assertUri(MockHttpServletRequestBuilder builder, HttpMethod httpMethod) {
		MockHttpServletRequest request = builder.buildRequest(this.servletContext);
		assertUri(httpMethod, request);
	}

	private void assertUri(MockMultipartHttpServletRequestBuilder builder, HttpMethod httpMethod) {
		MockHttpServletRequest request = builder.buildRequest(this.servletContext);
		assertUri(httpMethod, request);
	}

	private void assertUri(HttpMethod httpMethod, MockHttpServletRequest request) {
		assertThat(request.getRequestURI()).isEqualTo("/uri");
		assertThat(request.getMethod()).isEqualTo(httpMethod.name());
	}

}
