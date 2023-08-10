package io.github.sashirestela.openai.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import io.github.sashirestela.openai.exception.UncheckedException;

public class ReflectUtil {
  private static ReflectUtil reflection = null;

  private ReflectUtil() {
  }

  public static ReflectUtil get() {
    if (reflection == null) {
      reflection = new ReflectUtil();
    }
    return reflection;
  }

  @SuppressWarnings("unchecked")
  public <T> T createProxy(Class<T> interfaceClass, InvocationHandler handler) {
    T proxy = (T) Proxy.newProxyInstance(
        interfaceClass.getClassLoader(),
        new Class<?>[] { interfaceClass },
        handler);
    return proxy;
  }

  public Class<? extends Annotation> getFirstAnnotTypeInList(Method method,
      List<Class<? extends Annotation>> listAnnotType) {
    Class<? extends Annotation> annotType = listAnnotType
        .stream()
        .filter(type -> type != null && method.isAnnotationPresent(type))
        .findFirst()
        .orElse(null);
    return annotType;
  }

  public Object getAnnotAttribValue(AnnotatedElement element, Class<? extends Annotation> annotType,
      String annotMethodName) {
    Object value = null;
    Annotation annotation = element.getAnnotation(annotType);
    if (annotation != null) {
      Method annotMethod = null;
      try {
        annotMethod = annotType.getMethod(annotMethodName);
      } catch (NoSuchMethodException | SecurityException e) {
        throw new UncheckedException("Cannot found the method {0} in the annotation {1}.", annotMethodName,
            annotType.getName(), e);
      }
      try {
        value = annotMethod.invoke(annotation, (Object[]) null);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new UncheckedException("Cannot execute the method {0} in the annotation {1}.", annotMethodName,
            annotType.getName(), e);
      }
    }
    return value;
  }

  public MethodElement getMethodElementAnnotatedWith(Method method, Object[] arguments,
      Class<? extends Annotation> annotType, boolean shouldReadAnnotAttrValue) {
    MethodElement methodElement = null;
    int i = 0;
    Parameter[] parameters = method.getParameters();
    for (Parameter parameter : parameters) {
      if (parameter.isAnnotationPresent(annotType)) {
        Object defAnnotValue = null;
        if (shouldReadAnnotAttrValue) {
          defAnnotValue = getAnnotAttribValue(parameter, annotType, Constant.DEF_ANNOT_ATTRIB);
        }
        methodElement = new MethodElement(parameter, defAnnotValue, arguments[i]);
        break;
      }
      i++;
    }
    return methodElement;
  }

  public List<MethodElement> getMethodElementsAnnotatedWith(Method method, Object[] arguments,
      Class<? extends Annotation> annotType, boolean shouldReadAnnotAttrValue) {
    List<MethodElement> methodElements = new ArrayList<>();
    int i = 0;
    Parameter[] parameters = method.getParameters();
    for (Parameter parameter : parameters) {
      if (parameter.isAnnotationPresent(annotType)) {
        Object defAnnotValue = null;
        if (shouldReadAnnotAttrValue) {
          defAnnotValue = getAnnotAttribValue(parameter, annotType, Constant.DEF_ANNOT_ATTRIB);
        }
        methodElements.add(new MethodElement(parameter, defAnnotValue, arguments[i]));
      }
      i++;
    }
    return methodElements;
  }

  public Class<?> getBaseClassOf(Method method) {
    String className = method.getGenericReturnType().getTypeName();
    className = CommonUtil.get().findInnerGroup(className, Constant.REGEX_GENERIC_CLASS);
    Class<?> methodReturnClass = null;
    try {
      methodReturnClass = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new UncheckedException("Cannot found the base class {0} for the method {1}.", className,
          method.getName(), e);
    }
    return methodReturnClass;
  }

  public void executeSetMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object object, Object value) {
    Method method = null;
    try {
      method = clazz.getMethod(methodName, paramTypes);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new UncheckedException("Cannot found the method {0} in the class {1}", methodName, clazz.getSimpleName(),
          e);
    }
    try {
      method.invoke(object, value);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new UncheckedException("Cannot execute the method {0} in the class {1}", methodName, clazz.getSimpleName(),
          e);
    }
  }
}