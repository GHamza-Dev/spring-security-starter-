package flat.io.springsecuritystarter.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloWorld {

    @GetMapping("/hello")
    public Principal hello(Principal principal){
        return principal;
    }

    @GetMapping("/hello-user")
    private String helloUser(){
        return "<h1>Hello user</h1>";
    }

    @GetMapping("/hello-admin")
    private String helloAdmin(){
        return "<h1>Hello admin</h1>";
    }
}
