package com.springboot.app.interceptor.springboot_interceptor.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("timeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    // Cuando devuelve TRUE
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod methodControllerName = ((HandlerMethod) handler);
//        logger.info("LoadingTimeInterceptor: preHandle() entrando .... " + methodControllerName.getMethod().getName());
//
//        long start = System.currentTimeMillis();
//        request.setAttribute("start", start);
//
//        Random random = new Random();
//        int delay = random.nextInt(500);
//        Thread.sleep(delay);
//        return true;
//   }

    // Cuando devuelve FALSE
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod methodControllerName = ((HandlerMethod) handler);
        logger.info("LoadingTimeInterceptor: preHandle() entrando .... " + methodControllerName.getMethod().getName());

        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        Random random = new Random();
        int delay = random.nextInt(500);
        Thread.sleep(delay);

        // Crea un mapa JSON con un mensaje de error y la fecha actual, lo convierte a una cadena JSON,
        Map<String, String> json = new HashMap<>();
        json.put("error", "No tienes acceso a esta página");
        json.put("date", new Date().toString());

        // Establece la respuesta HTTP como JSON con un estado 401 (No autorizado) y escribe la cadena JSON en la respuesta.
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(jsonString);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        long end =  System.currentTimeMillis();
        long start = (long) request.getAttribute("start");
        long result = end - start;
        logger.info("Tiempo transcurrido: " + result + " milisegundos!");
        logger.info("LoadingTimeInterceptor: postHandle() saliendo .... " + ((HandlerMethod) handler).getMethod().getName());
    }
}
