/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.web.servlet.view.freemarker;

import freemarker.core.ParseException;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.*;
import freemarker.template.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 复制FreeMarkerView类解决
 * 由于ReloadableResourceBundleMessageSource导致TaglibFactory为空的问题
 * @author： wjun_java@163.com
 * @date： 2022/10/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FreeMarkerView extends AbstractTemplateView {

	@Nullable
	private String encoding;

	@Nullable
	private Configuration configuration;

	@Nullable
	private TaglibFactory taglibFactory;

	@Nullable
	private ServletContextHashModel servletContextHashModel;


	/**
	 * Set the encoding of the FreeMarker template file. Default is determined
	 * by the FreeMarker Configuration: "ISO-8859-1" if not specified otherwise.
	 * <p>Specify the encoding in the FreeMarker Configuration rather than per
	 * template if all your templates share a common encoding.
	 */
	public void setEncoding(@Nullable String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Return the encoding for the FreeMarker template.
	 */
	@Nullable
	protected String getEncoding() {
		return this.encoding;
	}

	/**
	 * Set the FreeMarker Configuration to be used by this view.
	 * <p>If this is not set, the default lookup will occur: a single {@link FreeMarkerConfig}
	 * is expected in the current web application context, with any bean name.
	 * <strong>Note:</strong> using this method will cause a new instance of {@link TaglibFactory}
	 * to created for every single {@link FreeMarkerView} instance. This can be quite expensive
	 * in terms of memory and initial CPU usage. In production it is recommended that you use
	 * a {@link FreeMarkerConfig} which exposes a single shared {@link TaglibFactory}.
	 */
	public void setConfiguration(@Nullable Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Return the FreeMarker configuration used by this view.
	 */
	@Nullable
	protected Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * Obtain the FreeMarker configuration for actual use.
	 * @return the FreeMarker configuration (never {@code null})
	 * @throws IllegalStateException in case of no Configuration object set
	 * @since 5.0
	 */
	protected Configuration obtainConfiguration() {
		Configuration configuration = getConfiguration();
		Assert.state(configuration != null, "No Configuration set");
		return configuration;
	}


	/**
	 * Invoked on startup. Looks for a single FreeMarkerConfig bean to
	 * find the relevant Configuration for this factory.
	 * <p>Checks that the template for the default Locale can be found:
	 * FreeMarker will check non-Locale-specific templates if a
	 * locale-specific one is not found.
	 * @see freemarker.cache.TemplateCache#getTemplate
	 */
	@Override
	protected void initServletContext(ServletContext servletContext) throws BeansException {
		if (getConfiguration() != null) {
			this.taglibFactory = new TaglibFactory(servletContext);
		}
		else {
			FreeMarkerConfig config = autodetectConfiguration();
			setConfiguration(config.getConfiguration());
			// TODO 暂时处理国际化ReloadableResourceBundleMessageSource bean导致 taglibFactory为空的问题
//			this.taglibFactory = config.getTaglibFactory();
		}

		GenericServlet servlet = new GenericServletAdapter();
		try {
			servlet.init(new DelegatingServletConfig());
		}
		catch (ServletException ex) {
			throw new BeanInitializationException("Initialization of GenericServlet adapter failed", ex);
		}
		this.servletContextHashModel = new ServletContextHashModel(servlet, getObjectWrapper());

	}

	/**
	 * Autodetect a {@link FreeMarkerConfig} object via the ApplicationContext.
	 * @return the Configuration instance to use for FreeMarkerViews
	 * @throws BeansException if no Configuration instance could be found
	 * @see #getApplicationContext
	 * @see #setConfiguration
	 */
	protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
		try {
			return BeanFactoryUtils.beanOfTypeIncludingAncestors(
					obtainApplicationContext(), FreeMarkerConfig.class, true, false);
		}
		catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException(
					"Must define a single FreeMarkerConfig bean in this web application context " +
							"(may be inherited): FreeMarkerConfigurer is the usual implementation. " +
							"This bean may be given any name.", ex);
		}
	}

	/**
	 * Return the configured FreeMarker {@link ObjectWrapper}, or the
	 * {@link ObjectWrapper#DEFAULT_WRAPPER default wrapper} if none specified.
	 * @see freemarker.template.Configuration#getObjectWrapper()
	 */
	protected ObjectWrapper getObjectWrapper() {
		ObjectWrapper ow = obtainConfiguration().getObjectWrapper();
		return (ow != null ? ow :
				new DefaultObjectWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build());
	}

	/**
	 * Check that the FreeMarker template used for this view exists and is valid.
	 * <p>Can be overridden to customize the behavior, for example in case of
	 * multiple templates to be rendered into a single view.
	 */
	@Override
	public boolean checkResource(Locale locale) throws Exception {
		String url = getUrl();
		Assert.state(url != null, "'url' not set");

		try {
			// Check that we can get the template, even if we might subsequently get it again.
			getTemplate(url, locale);
			return true;
		}
		catch (FileNotFoundException ex) {
			// Allow for ViewResolver chaining...
			return false;
		}
		catch (ParseException ex) {
			throw new ApplicationContextException("Failed to parse [" + url + "]", ex);
		}
		catch (IOException ex) {
			throw new ApplicationContextException("Failed to load [" + url + "]", ex);
		}
	}


	/**
	 * Process the model map by merging it with the FreeMarker template.
	 * Output is directed to the servlet response.
	 * <p>This method can be overridden if custom behavior is needed.
	 */
	@Override
	protected void renderMergedTemplateModel(
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		exposeHelpers(model, request);
		doRender(model, request, response);
	}

	/**
	 * Expose helpers unique to each rendering operation. This is necessary so that
	 * different rendering operations can't overwrite each other's formats etc.
	 * <p>Called by {@code renderMergedTemplateModel}. The default implementation
	 * is empty. This method can be overridden to add custom helpers to the model.
	 * @param model the model that will be passed to the template at merge time
	 * @param request current HTTP request
	 * @throws Exception if there's a fatal error while we're adding information to the context
	 * @see #renderMergedTemplateModel
	 */
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
	}

	/**
	 * Render the FreeMarker view to the given response, using the given model
	 * map which contains the complete template model to use.
	 * <p>The default implementation renders the template specified by the "url"
	 * bean property, retrieved via {@code getTemplate}. It delegates to the
	 * {@code processTemplate} method to merge the template instance with
	 * the given template model.
	 * <p>Adds the standard Freemarker hash models to the model: request parameters,
	 * request, session and application (ServletContext), as well as the JSP tag
	 * library hash model.
	 * <p>Can be overridden to customize the behavior, for example to render
	 * multiple templates into a single view.
	 * @param model the model to use for rendering
	 * @param request current HTTP request
	 * @param response current servlet response
	 * @throws IOException if the template file could not be retrieved
	 * @throws Exception if rendering failed
	 * @see #setUrl
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getLocale
	 * @see #getTemplate(java.util.Locale)
	 * @see #processTemplate
	 * @see freemarker.ext.servlet.FreemarkerServlet
	 */
	protected void doRender(Map<String, Object> model, HttpServletRequest request,
							HttpServletResponse response) throws Exception {

		// Expose model to JSP tags (as request attributes).
		exposeModelAsRequestAttributes(model, request);
		// Expose all standard FreeMarker hash models.
		SimpleHash fmModel = buildTemplateModel(model, request, response);

		// Grab the locale-specific version of the template.
		Locale locale = RequestContextUtils.getLocale(request);
		processTemplate(getTemplate(locale), fmModel, response);
	}

	/**
	 * Build a FreeMarker template model for the given model Map.
	 * <p>The default implementation builds a {@link AllHttpScopesHashModel}.
	 * @param model the model to use for rendering
	 * @param request current HTTP request
	 * @param response current servlet response
	 * @return the FreeMarker template model, as a {@link SimpleHash} or subclass thereof
	 */
	protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request,
											HttpServletResponse response) {

		AllHttpScopesHashModel fmModel = new AllHttpScopesHashModel(getObjectWrapper(), getServletContext(), request);
		fmModel.put(FreemarkerServlet.KEY_JSP_TAGLIBS, this.taglibFactory);
		fmModel.put(FreemarkerServlet.KEY_APPLICATION, this.servletContextHashModel);
		fmModel.put(FreemarkerServlet.KEY_SESSION, buildSessionModel(request, response));
		fmModel.put(FreemarkerServlet.KEY_REQUEST, new HttpRequestHashModel(request, response, getObjectWrapper()));
		fmModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new HttpRequestParametersHashModel(request));
		fmModel.putAll(model);
		return fmModel;
	}

	/**
	 * Build a FreeMarker {@link HttpSessionHashModel} for the given request,
	 * detecting whether a session already exists and reacting accordingly.
	 * @param request current HTTP request
	 * @param response current servlet response
	 * @return the FreeMarker HttpSessionHashModel
	 */
	private HttpSessionHashModel buildSessionModel(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return new HttpSessionHashModel(session, getObjectWrapper());
		}
		else {
			return new HttpSessionHashModel(null, request, response, getObjectWrapper());
		}
	}

	/**
	 * Retrieve the FreeMarker template for the given locale,
	 * to be rendering by this view.
	 * <p>By default, the template specified by the "url" bean property
	 * will be retrieved.
	 * @param locale the current locale
	 * @return the FreeMarker template to render
	 * @throws IOException if the template file could not be retrieved
	 * @see #setUrl
	 * @see #getTemplate(String, java.util.Locale)
	 */
	protected Template getTemplate(Locale locale) throws IOException {
		String url = getUrl();
		Assert.state(url != null, "'url' not set");
		return getTemplate(url, locale);
	}

	/**
	 * Retrieve the FreeMarker template specified by the given name,
	 * using the encoding specified by the "encoding" bean property.
	 * <p>Can be called by subclasses to retrieve a specific template,
	 * for example to render multiple templates into a single view.
	 * @param name the file name of the desired template
	 * @param locale the current locale
	 * @return the FreeMarker template
	 * @throws IOException if the template file could not be retrieved
	 */
	protected Template getTemplate(String name, Locale locale) throws IOException {
		return (getEncoding() != null ?
				obtainConfiguration().getTemplate(name, locale, getEncoding()) :
				obtainConfiguration().getTemplate(name, locale));
	}

	/**
	 * Process the FreeMarker template to the servlet response.
	 * <p>Can be overridden to customize the behavior.
	 * @param template the template to process
	 * @param model the model for the template
	 * @param response servlet response (use this to get the OutputStream or Writer)
	 * @throws IOException if the template file could not be retrieved
	 * @throws TemplateException if thrown by FreeMarker
	 * @see freemarker.template.Template#process(Object, java.io.Writer)
	 */
	protected void processTemplate(Template template, SimpleHash model, HttpServletResponse response)
			throws IOException, TemplateException {

		template.process(model, response.getWriter());
	}


	/**
	 * Simple adapter class that extends {@link GenericServlet}.
	 * Needed for JSP access in FreeMarker.
	 */
	@SuppressWarnings("serial")
	private static class GenericServletAdapter extends GenericServlet {

		@Override
		public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
			// no-op
		}
	}


	/**
	 * Internal implementation of the {@link ServletConfig} interface,
	 * to be passed to the servlet adapter.
	 */
	private class DelegatingServletConfig implements ServletConfig {

		@Override
		@Nullable
		public String getServletName() {
			return FreeMarkerView.this.getBeanName();
		}

		@Override
		@Nullable
		public ServletContext getServletContext() {
			return FreeMarkerView.this.getServletContext();
		}

		@Override
		@Nullable
		public String getInitParameter(String paramName) {
			return null;
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			return Collections.enumeration(Collections.emptySet());
		}
	}

}
