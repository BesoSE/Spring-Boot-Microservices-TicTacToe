package com.microservices.RegistrationLogin.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="email-service")
public interface EmailProxy {
    @GetMapping("/sendMail/{email}/{text}")
    public String  retriveDataForEmail(@PathVariable String email,@PathVariable String text);

    @GetMapping("/sendMail2")
    public String  retriveDataForEmail2();
}
