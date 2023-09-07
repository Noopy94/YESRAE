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
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

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
        "/api/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//            .cors(AbstractHttpConfigurer::disable)
//            .headers(c -> c.frameOptions(FrameOptionsConfig::disable).disable())
//            .authorizeHttpRequests(auth -> auth
//            .requestMatchers(WHITE_LIST).permitAll()
//            .anyRequest().authenticated())
//            .sessionManagement(manager -> manager
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .oauth2Login()
//            .addFilterBefore(new JwtExceptionFilter(),
//                OAuth2LoginAuthenticationFilter.class)

//        http
//
//            // [PART 1]
//            .formLogin().disable() // FormLogin 사용 X
//            .httpBasic().disable() // httpBasic 사용 X
//            .csrf().disable() // csrf 보안 사용 X
//            .headers().frameOptions().disable()
//            .and()
//
//            // 세션 사용하지 않으므로 STATELESS로 설정
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//            .and()
//
//            // [PART 2]
//            //== URL별 권한 관리 옵션 ==//
//            .authorizeRequests()
//
//            // 아이콘, css, js 관련
//            // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
//            .dispatcherTypeMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico",
//                "/h2-console/**").permitAll()
//            .dispatcherTypeMatchers("/sign-up").permitAll() // 회원가입 접근 가능
//            .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
//            .and()
//
//            // [PART 3]
//            //== 소셜 로그인 설정 ==//
//            .oauth2Login()
//            .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
//            .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정
//            .userInfoEndpoint().userService(customOAuth2UserService); // customUserService 설정
//
//        // [PART4]
//        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
//        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
//        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
//        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
//        http.addFilterBefore(jwtAuthenticationProcessingFilter(),
//            CustomJsonUsernamePasswordAuthenticationFilter.class);

        http
            // [PART 1]
            .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용 X
            .formLogin(AbstractHttpConfigurer::disable) // FormLogin 사용 X
            .csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X
            .cors(Customizer.withDefaults()) // cors 보안 사용 X
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
}
