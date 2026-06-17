package com.samwise.altamira.health;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }

    @GetMapping("/api/hello/{name}")
    public Map<String, String> hello(@PathVariable String name) {
        return Map.of("message", "Hello, " + name);
    }

    @GetMapping("/api/echo")
    public Map<String, String> echo(@RequestParam String message) {
        return Map.of("echo", message);
    }

    @PostMapping("/api/ping")
    public Map<String, String> ping(@RequestBody PingRequest request) {
        return Map.of("received", request.getMessage());
    }
}