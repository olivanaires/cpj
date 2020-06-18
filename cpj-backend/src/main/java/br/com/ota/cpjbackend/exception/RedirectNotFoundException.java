package br.com.ota.cpjbackend.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectNotFoundException {

    @RequestMapping( method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "{_:^(?!index\\.html|api).*$}")
    public String forwardAngularPaths() {
        return "forward:/index.html";
    }

}
