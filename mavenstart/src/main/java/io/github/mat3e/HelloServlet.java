package io.github.mat3e;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Hello", urlPatterns = {"/api/*"})

public class HelloServlet extends HttpServlet{
    public static final String NAME_PARAM = "name";
    public static final String LANG_PARAM = "lang";
    private final Logger logger = LoggerFactory.getLogger(HelloServlet.class);
    private HelloService service;
    /**
     * Service do użycia jetty wymaga konstruktora bezparamterowego
     */
    @SuppressWarnings("unused")
    public HelloServlet(){
        this(new HelloService());
    }

    HelloServlet(HelloService service){
       this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Request got");
        resp.getWriter().write(service.prepareGreeting(req.getParameter(NAME_PARAM),req.getParameter(LANG_PARAM)));
    }
}
