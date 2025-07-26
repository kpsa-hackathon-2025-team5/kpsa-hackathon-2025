package io.github.yaksenseback.common.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentMember.class)
               && (parameter.getParameterType().equals(String.class)
                   || parameter.getParameterType().equals(Long.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String memberIdStr = (String) request.getAttribute("memberId");
        if (memberIdStr == null) {
            return null;
        }
        if (parameter.getParameterType().equals(Long.class)) {
            try {
                return Long.parseLong(memberIdStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid memberId format: " + memberIdStr);
            }
        }
        return memberIdStr;
    }

}
