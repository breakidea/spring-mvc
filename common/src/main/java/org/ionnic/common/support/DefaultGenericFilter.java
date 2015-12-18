package org.ionnic.common.support;import java.io.IOException;import javax.servlet.FilterChain;import javax.servlet.ServletException;import javax.servlet.ServletRequest;import javax.servlet.ServletResponse;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;import org.springframework.web.filter.GenericFilterBean;/** * @author apple * */public class DefaultGenericFilter extends GenericFilterBean {    private String encoding = AppConfig.CHARSET;    private boolean forceEncoding = true;    @Override    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {        final HttpServletRequest request = (HttpServletRequest) req;        final HttpServletResponse response = (HttpServletResponse) resp;        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {            request.setCharacterEncoding(this.encoding);            if (this.forceEncoding) {                response.setCharacterEncoding(this.encoding);            }        }        chain.doFilter(request, response);    }    @Override    protected void initFilterBean() throws ServletException {        getServletContext();    }    /**     * @param encoding     *            the encoding to set     */    public void setEncoding(String encoding) {        this.encoding = encoding;    }    /**     * @param forceEncoding     *            the forceEncoding to set     */    public void setForceEncoding(boolean forceEncoding) {        this.forceEncoding = forceEncoding;    }}