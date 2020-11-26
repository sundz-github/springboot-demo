package com.sun.springbootdemo.controller.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/11/16 16:29
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("/v0")  //加在接口和实现类上的路径只能生效其中之一
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface JerseyControllerApi {

    @Path(value = "/{id}")
    @GET
    String test(@PathParam(value = "id") String id);

    @Path(value = "/world")
    @GET
    String test1();

    @POST
    @Path("/collect")
    Set<String> collect(Set<String> set);
}
