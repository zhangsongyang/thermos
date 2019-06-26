package cn.zsy.thermos.service;

import cn.zsy.thermos.domain.User;
import cn.zsy.thermos.dubbo.service.RestService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service(version = "1.0.0", protocol = {"dubbo", "rest"})
@Path("/")
public class StandardRestService implements RestService {


    @Override
    @Path("param")
    @GET
    public String param(@QueryParam("param") String param) {
        log("/param", param);
        return param;
    }

    @Override
    @Path("params")
    @POST
    public String params(@QueryParam("a") int a, @QueryParam("b") String b) {
        log("/params", a + b);
        return a + b;
    }

    @Override
    @Path("headers")
    @GET
    public String headers(@HeaderParam("h") String header,
                          @HeaderParam("h2") String header2,
                          @QueryParam("v")
                                  Integer param) {
        String result = header + " , " + header2 + " , " + param;
        log("/headers", result);
        return result;
    }

    @Override
    @Path("path-variables/{p1}/{p2}")
    @GET
    public String pathVariables(@PathParam("p1") String path1,
                                @PathParam("p2") String path2,
                                @QueryParam("v") String param) {
        String result = path1 + " , " + path2 + " , " + param;
        log("/path-variables", result);
        return result;
    }

    // @CookieParam does not support : https://github.com/OpenFeign/feign/issues/913
    // @CookieValue also does not support

    @Override
    @Path("form")
    @POST
    public String form(@FormParam("f") String form) {
        return String.valueOf(form);
    }

    @Override
    @Path("request/body/map")
    @POST
    @Produces(APPLICATION_JSON_VALUE)
    public User requestBodyMap(Map<String, Object> data, @QueryParam("param") String param) {
        User user = new User();
        user.setId(((Integer) data.get("id")).longValue());
        user.setName((String) data.get("name"));
        user.setAge((Integer) data.get("age"));
        log("/request/body/map", param);
        return user;
    }

    @Path("request/body/user")
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, Object> requestBodyUser(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("age", user.getAge());
        return map;
    }

    public static void log(String url, Object result) {
        String message = String.format("The client[%s] uses '%s' protocol to call %s : %s",
                RpcContext.getContext().getRemoteHostName(),
                RpcContext.getContext().getUrl() == null ? "N/A" : RpcContext.getContext().getUrl().getProtocol(),
                url,
                result
        );
        System.out.println(message);
    }
}
