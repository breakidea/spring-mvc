package org.ionnic.app.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ionnic.app.util.CreateImage;
import org.ionnic.app.util.OutputModel;
import org.ionnic.config.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author apple
 *
 */
@Controller
@RequestMapping("/test")
public class Test extends ActionSupport {

	@Autowired
	HttpServletRequest request;

	/**
	 * @param body
	 * @param model
	 */
	@RequestMapping("/basic")
	public void basic(@RequestBody OutputModel body, Model model) {
		ModelMap data = new ModelMap();

		data.addAttribute("method", request.getMethod());
		data.addAttribute("controller", this.getClass().getName());

		model.addAttribute("body", body);
		model.addAttribute("data", data);
	}

	@RequestMapping("/code")
	public void code(HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpg");

<<<<<<< HEAD
		CreateImage vCode = new CreateImage(75, 20, 4, 4);
=======
		CreateImage vCode = new CreateImage(120, 30, 4, 10);
>>>>>>> bf55ed2f25c1483f4e3cf87ded6aa6aa299016cf
		response.addHeader("Content-Type", "image/png");

		vCode.write(response.getOutputStream());
		request.getSession(true).setAttribute(CreateImage.class.getName() + ".IMAGE_NAME", vCode.getCode());
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/error")
	public Map<String, Object> error() throws Exception {
		throw new ServletException("<!-- status-ok --> PageException");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exception", produces = "application/json")
	@ResponseBody
	public Map<String, Object> exception() throws Exception {
		throw new Exception("<!-- status-ok --> UserException");
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public Object index(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ip", "11");
		data.put("search", new Date());
		return data;
	}

	@RequestMapping("log.js")
	public void index(@RequestParam String version, @RequestParam int sid, Model model) {
		model.addAttribute("siteId", sid);
		model.addAttribute("version", version);
	}

	/**
	 * @param body
	 * @param search
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search/{search}", method = RequestMethod.POST)
	@ResponseBody
	public Object search(@RequestBody Map<String, Object> body, @PathVariable() String search) throws Exception {
		ModelMap data = new ModelMap();

		data.addAttribute("biz", body);
		data.addAttribute("search", search);
		data.addAttribute("status", 0);

		return data;
	}

	@RequestMapping("/status")
	public void status(HttpServletResponse response) throws Exception {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.getOutputStream().write("OK<!-- status-ok -->".getBytes());
	}

}
