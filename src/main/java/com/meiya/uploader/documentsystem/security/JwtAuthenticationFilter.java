package com.meiya.uploader.documentsystem.security;

import com.meiya.uploader.documentsystem.core.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";
    private static final String HEADER = "Authorization";
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private AdminService adminService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (null == jwt) {
                jwt = request.getParameter("token");
            }

            if (StringUtils.hasText(jwt) && tokenProvider.validate(jwt)) {
                UserDetails login;
                Pair<String, String> entry = tokenProvider.parse(jwt);

                login = customUserDetailsService.loadUserByUsername(entry.getSecond());
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication(request, login));

            }
        } catch (Exception ex) {
            logger.info("authentication fail");
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authentication(HttpServletRequest request, UserDetails login) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                login, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
