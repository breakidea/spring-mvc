package com.breakidea.noah.support;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.web.util.WebUtils;

public abstract class AbstractWebController extends WebContentGenerator implements Controller {

	public static final String ERROR_NAME = "errorMsg";

	public static final String REQUEST_ATTRIBUTE = "Request";

	public static final String SESSION_ATTRIBUTE = "Session";

	public static final String DATA_ATTRIBUTE = "Data";

	public static final String RESPONSE_ATTRIBUTE = "Response";

	public static final String ACTION = "Action";

	public static final String VIEW_NAME = View.class.getName() + ".viewName";

	private boolean synchronizeOnSession = false;

	/**
	 * Create a new AbstractWebController which supports HTTP methods GET, HEAD and
	 * POST by default.
	 */
	public AbstractWebController() {
		this(true);
	}

	/**
	 * Create a new AbstractWebController.
	 *
	 * @param restrictDefaultSupportedMethods {@code true} if this controller should
	 *                                        support HTTP methods GET, HEAD and
	 *                                        POST by default, or {@code false} if
	 *                                        it should be unrestricted
	 * @since 4.3
	 */
	public AbstractWebController(boolean restrictDefaultSupportedMethods) {
		super(restrictDefaultSupportedMethods);
	}

	/**
	 * Set if controller execution should be synchronized on the session, to
	 * serialize parallel invocations from the same client.
	 * <p>
	 * More specifically, the execution of the {@code handleRequestInternal} method
	 * will get synchronized if this flag is "true". The best available session
	 * mutex will be used for the synchronization; ideally, this will be a mutex
	 * exposed by HttpSessionMutexListener.
	 * <p>
	 * The session mutex is guaranteed to be the same object during the entire
	 * lifetime of the session, available under the key defined by the
	 * {@code SESSION_MUTEX_ATTRIBUTE} constant. It serves as a safe reference to
	 * synchronize on for locking on the current session.
	 * <p>
	 * In many cases, the HttpSession reference itself is a safe mutex as well,
	 * since it will always be the same object reference for the same active logical
	 * session. However, this is not guaranteed across different servlet containers;
	 * the only 100% safe way is a session mutex.
	 *
	 * @see AbstractWebController#handleRequestInternal
	 * @see org.springframework.web.util.HttpSessionMutexListener
	 * @see org.springframework.web.util.WebUtils#getSessionMutex(javax.servlet.http.HttpSession)
	 */
	public final void setSynchronizeOnSession(boolean synchronizeOnSession) {
		this.synchronizeOnSession = synchronizeOnSession;
	}

	public void setTemplateViewName(ModelMap model, String viewName) {
		if (model.containsKey(VIEW_NAME) == false) {
			model.put(VIEW_NAME, viewName);
		}
	}

	/**
	 * Return whether controller execution should be synchronized on the session.
	 */
	public final boolean isSynchronizeOnSession() {
		return synchronizeOnSession;
	}

	@Override
	public final ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			response.setHeader("Allow", getAllowHeader());
			return null;
		}

		// Delegate to WebContentGenerator for checking and preparing.
		checkRequest(request);
		prepareResponse(response);

		// Execute handleRequestInternal in synchronized block if required.
		if (synchronizeOnSession) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				Object mutex = WebUtils.getSessionMutex(session);
				synchronized (mutex) {
					return handleRequestInternal(request, response);
				}
			}
		}

		return handleRequestInternal(request, response);
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		ModelMap model = new ModelMap();

		mv.addObject(REQUEST_ATTRIBUTE, request);
		mv.addObject(RESPONSE_ATTRIBUTE, response);
		mv.addObject(SESSION_ATTRIBUTE, request.getSession(false));
		mv.addObject(DATA_ATTRIBUTE, model);

		try {
			handleRequestInternal(model, request, response);
			if (model.containsKey(VIEW_NAME)) {
				mv.setViewName((String) model.get(VIEW_NAME));
			}
		} catch (ServletException e) {
			mv.addObject(ERROR_NAME, e.getMessage());
			logger.error(e);
		}

		return mv;
	}

	/**
	 * Template method. Subclasses must implement this.
	 */
	public abstract void handleRequestInternal(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException;

}
