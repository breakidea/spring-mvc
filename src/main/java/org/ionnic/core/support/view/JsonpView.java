package org.ionnic.core.support.view;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class JsonpView extends MappingJacksonJsonView {

	public static final String DEFAULT_CONTENT_TYPE = "application/javascript";

	private String callbackKey;

	private String varKey;

	private String defaultCallback = "callback";

	/**
	 * @return
	 */
	public String getCallbackKey() {
		return callbackKey;
	}

	@Override
	public String getContentType() {
		return DEFAULT_CONTENT_TYPE;
	}

	/**
	 * @return
	 */
	public String getDefaultCallback() {
		return defaultCallback;
	}

	/**
	 * @return
	 */
	public String getVarKey() {
		return varKey;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// TODO Auto-generated method stub
		OutputStream out = response.getOutputStream();
		if ("GET".equals(request.getMethod().toUpperCase())) {

			String callback = request.getParameter(callbackKey);
			String var = request.getParameter(varKey);

			if (StringUtils.hasText(var)) {
				out.write(new String(var + " = ").getBytes());
				super.render(model, request, response);
				out.write(new String(";").getBytes());
			} else {
				if (!StringUtils.hasText(callback)) {
					callback = defaultCallback;
				}
				out.write(new String(callback + "(").getBytes());
				super.render(model, request, response);
				out.write(new String(");").getBytes());
			}
		} else {
			out.write(new String("void(0);").getBytes());
		}
	}

	/**
	 * @param callbackKey
	 */
	public void setCallbackKey(String callbackKey) {
		this.callbackKey = callbackKey;
	}

	/**
	 * @param defaultCallback
	 */
	public void setDefaultCallback(String defaultCallback) {
		this.defaultCallback = defaultCallback;
	}

	/**
	 * @param varKey
	 */
	public void setVarKey(String varKey) {
		this.varKey = varKey;
	}
}