package com.ssafy.yesrae.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.yesrae.config.jwt.filter.JwtAuthenticationProcessingFilter;
import com.ssafy.yesrae.config.jwt.service.JwtService;
import com.ssafy.yesrae.config.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.ssafy.yesrae.config.login.handler.LoginFailureHandler;
import com.ssafy.yesrae.config.login.handler.LoginSuccessHandler;
import com.ssafy.yesrae.config.login.service.LoginService;
import com.ssafy.yesrae.config.oauth2.handler.OAuth2LoginFailureHandler;
import com.ssafy.yesrae.config.oauth2.handler.OAuth2LoginSuccessHandler;
import com.ssafy.yesrae.config.oauth2.service.CustomOAuth2UserService;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private static final String[] WHITE_LIST = {
        "/api/auth/**", "/template/**", "/tournament/auth/**", "/user/**", "/oauth2/**",
        "playlist/find/**", "playlist/songs/**", "playlist/tags/**", "playlist/findtag",
        "playlist/findtitle",
        "playlist/best20likecnt", "playlist/best20viewcnt"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // [PART 1]
            .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용 X
            .formLogin(AbstractHttpConfigurer::disable) // FormLogin 사용 X
            .csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X
            .cors(
                httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
                    corsConfigurationSource())) // cors 보안 사용 X
            .headers(httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer
                    .frameOptions(FrameOptionsConfig::disable)
                    .disable())
            // 세션 사용하지 않으므로 stateless 설정
            .sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // [PART 2]
            // URL별 권한 관리 옵션
            // TODO: 접근 허용하는 url 설정 해야함
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated())
            // [PART 3]
            // 소셜 로그인 설정
            .oauth2Login(
                httpSecurityOAuth2LoginConfigurer ->
                    httpSecurityOAuth2LoginConfigurer
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(
                            userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        // [PART4]
        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(),
            CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // [PART 1]
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * [PART 2] AuthenticationManager 설정 후 등록 PasswordEncoder를 사용하는 AuthenticationProvider 지정
     * (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용) FormLogin(기존 스프링 시큐리티 로그인)과 동일하게
     * DaoAuthenticationProvider 사용 UserDetailsService는 커스텀 LoginService로 등록 또한, FormLogin과 동일하게
     * AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    /**
     * [PART 3] 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }


    /**
     * [PART 4] CustomJsonUsernamePasswordAuthenticationFilter 빈 등록 커스텀 필터를 사용하기 위해 만든 커스텀 필터를
     * Bean으로 등록 setAuthenticationManager(authenticationManager())로 위에서 등록한
     * AuthenticationManager(ProviderManager) 설정 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한
     * handler 설정
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
            = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(
            loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(
            loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    // [PART 5]
    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(
            jwtService, userRepository);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
