package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho API
                .authorizeHttpRequests(auth -> auth
                        // 1. Ai cũng có thể xem trang chủ HTML (nếu có)
                        .requestMatchers("/", "/index.html", "/static/**").permitAll()

                        // 2. USER: Chỉ được xem (GET)
                        // Lưu ý: ADMIN cũng cần quyền xem, nên ta cho phép cả 2 role
                        .requestMatchers(HttpMethod.GET, "/posts/**").hasAnyRole("user", "admin")

                        // 3. ADMIN: Được quyền Thêm, Sửa, Xóa (POST, PUT, DELETE)
                        .requestMatchers(HttpMethod.POST, "/posts/**").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/posts/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").hasRole("admin")

                        // Các request khác bắt buộc phải đăng nhập
                        .anyRequest().authenticated()
                )
                // Cấu hình Resource Server sử dụng JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    // Hàm chuyển đổi role từ Keycloak sang định dạng Spring Security (ROLE_...)
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> authorities = new ArrayList<>();

            // --- 1. TÌM TRONG REALM ACCESS (Role toàn cục - Quan trọng nhất) ---
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                Collection<String> roles = (Collection<String>) realmAccess.get("roles");
                roles.forEach(role -> {
                    // Spring Security yêu cầu prefix "ROLE_"
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    System.out.println("DEBUG: Tìm thấy Realm Role: ROLE_" + role);
                });
            }

            // --- 2. TÌM TRONG RESOURCE ACCESS (Client Role - Dự phòng) ---
            // Thay "blog-app" bằng đúng Client ID bạn đặt trong Keycloak
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess != null && resourceAccess.containsKey("blog-app")) {
                Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("blog-app");
                if (clientAccess != null && clientAccess.containsKey("roles")) {
                    Collection<String> clientRoles = (Collection<String>) clientAccess.get("roles");
                    clientRoles.forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                        System.out.println("DEBUG: Tìm thấy Client Role: ROLE_" + role);
                    });
                }
            }

            // In ra danh sách quyền cuối cùng để debug
            System.out.println("DEBUG: => Tổng quyền hạn user có: " + authorities);

            return authorities;
        });
        return converter;
    }
}