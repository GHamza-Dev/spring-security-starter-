package flat.io.springsecuritystarter.authentication;

import flat.io.springsecuritystarter.JwtUtil;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class CustomAuthentication {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public CustomAuthentication(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    private ResponseEntity<?> authenticate(@RequestBody AuthRequest request){
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(token);
        }catch (BadCredentialsException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(403).body(e.getMessage());
        }

    }
}
