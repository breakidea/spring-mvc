package com.alibaba.rigel.web.support.velocity;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.velocity.app.event.implement.EscapeHtmlReference;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.util.StringUtils;

public class NoEscapeHtmlReference extends EscapeHtmlReference {

	private Perl5Util perl = new Perl5Util();

	private String matchRegExp = null;

	/**
	 * Escape the provided text if it not matches the configured regular expression.
	 *
	 * @param reference
	 * @param value
	 * @return Escaped text.
	 */
	@Override
	public Object referenceInsert(String reference, Object value) {
		if (value == null || !(value instanceof String)) {
			return value;
		}
		if (matchRegExp == null) {
			return escape(value);
		} else if (!perl.match(matchRegExp, reference)) {
			return escape(value);
		} else {
			return value;
		}
	}

	@Override
	public void setRuntimeServices(RuntimeServices rs) {
		super.setRuntimeServices(rs);
		matchRegExp = StringUtils.nullTrim(rs.getConfiguration().getString(getMatchAttribute()));
	}

	/**
	 * @return attribute "eventhandler.noescape.html.match"
	 */
	@Override
	protected String getMatchAttribute() {
		return "eventhandler.noescape.html.match";
	}
}