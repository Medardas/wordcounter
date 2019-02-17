package org.task.wordcounter.interceptor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.task.wordcounter.exception.StorageException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class FileSecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (isMethodReceiverOfMultipartFiles(handlerMethod.getMethod()))
            for (Part multipartFile : (request.getParts()))
                if (isFileNameUnsafe(multipartFile.getSubmittedFileName()))
                    throw new StorageException("Unsafe file name");

        return true;
    }

    private boolean isFileNameUnsafe(String fileName) {
        return fileName.contains("..") || !fileName.endsWith(".txt");
    }

    private boolean isMethodReceiverOfMultipartFiles(Method method) {
        if (!method.isAnnotationPresent(PostMapping.class))
            return false;

        Parameter[] parameters = method.getParameters();
        for (Parameter param : parameters)
            if (param.getParameterizedType().getTypeName().endsWith(MultipartFile.class.getName() + "[]")) //jei tai būtu didesnė aplikacija reikėtų pasirūpinti ir ne masyvų tikrinimu.
                return true;

        return false;
    }
}
