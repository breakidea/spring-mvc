package org.ionnic.common.view.tool;import java.io.StringWriter;import java.util.Map;import javax.servlet.http.HttpServletRequest;import org.apache.velocity.VelocityContext;import org.apache.velocity.app.VelocityEngine;import org.apache.velocity.context.Context;import org.apache.velocity.exception.VelocityException;import org.apache.velocity.tools.Scope;import org.apache.velocity.tools.config.DefaultKey;import org.apache.velocity.tools.config.ValidScope;import org.apache.velocity.tools.view.ViewToolContext;import org.ionnic.common.support.Security;/** * @author apple * */@DefaultKey("page")@ValidScope(Scope.REQUEST)public class PageTool {	private ViewToolContext context;	private VelocityEngine engine;	/**	 * @return	 */	public String getToken() {		HttpServletRequest request = context.getRequest();		return Security.generateToken(request);	}	/**	 * @return	 */	public String getTokenName() {		return Security.PARAMETER_NAME;	}	/**	 * @return	 */	public String getTokenField() {		String token = getToken();		return "<input type=\"hidden\" name=\"" + Security.PARAMETER_NAME + "\" value=\"" + token + "\" />";	}	/**	 * @param urlName	 * @param param	 * @return	 */	public String getURI(String urlName) {		HttpServletRequest req = context.getRequest();		if (!urlName.startsWith("/")) {			urlName = "/" + urlName;		}		return req.getContextPath() + urlName;	}	/**	 * 请求级别的初始化函数	 *	 * @param obj	 * @throws Exception	 */	public void init(Object object) {		context = (ViewToolContext) object;		context.getRequest();		// 缓存起来		engine = context.getVelocityEngine();	}	/**	 * @param templateName	 * @return	 */	public StringWriter load(String templateName) {		try {			return render(templateName, null);		} catch (VelocityException e) {		}		return null;	}	/**	 * @param templateName	 * @param map	 * @return	 */	public StringWriter load(String templateName, Map<String, Object> map) {		try {			return render(templateName, map);		} catch (VelocityException e) {		}		return null;	}	/**	 * @param ext	 * @param name	 * @param data	 * @return	 */	private StringWriter render(String templateName, Map<String, Object> map) {		StringWriter writer = new StringWriter();		Context data = null;		if (map != null) {			map.putAll(context.getToolbox());		}		data = new VelocityContext(map);		engine.getTemplate(templateName).merge(data, writer);		return writer;	}}