package com.sun.springbootdemo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/11/16 16:37
 */
@Log4j2
@Component
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("/v1/test")
public class Test {


    @Path(value = "/{id}")
    @GET
    public String test(@PathParam(value = "id") String id) {
        log.info("JerseyController控制器生效了!");
        return id;
    }

    @Path(value = "world")
    @GET
    public String test1() {
        return "Hello World!";
    }
}
