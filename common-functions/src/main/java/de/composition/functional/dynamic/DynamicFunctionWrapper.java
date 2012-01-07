package de.composition.functional.dynamic;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.google.common.base.Function;

/**
 * Creates a {@link Function} which delegates to a specified method of its
 * argument and returns that method's return value.
 * <p>
 * 
 * <pre>
 * <code>
 * Function&lt;FooClass, Value&gt; function = newFunction(delegatingTo(FooClass.class).getValue());
 * Value value = new Value("test");
 * FooClass input = new FooClass(value);
 * assertEquals(value, function.apply(input))
 * </code>
 * </pre>
 * 
 * Unfortunately this only works if the function argument and return types are
 * non-final and have a default no-args constructor.
 * 
 */
public class DynamicFunctionWrapper {

	private interface MethodInfo {

		Method getMethod();

		Object[] getArgs();
	}

	private static class MethodInfoImpl implements MethodInfo {
		private final Method method;
		private final Object[] args;

		private MethodInfoImpl(Method method, Object[] args) {
			this.method = method;
			this.args = args;
		}

		public Method getMethod() {
			return method;
		}

		public Object[] getArgs() {
			return args;
		}

	}

	public static class MethodInfoEnhancer implements MethodInterceptor {

		public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy)
				throws Throwable {
			try {
				MethodInfo info = new MethodInfoImpl(method, args);
				return createProxyWithMethodInfo(method.getReturnType(), info);
			} catch (Throwable t) {
				throw t;
			} finally {
			}
		}

		private Object createProxyWithMethodInfo(Class<? extends Object> clazz, final MethodInfo info)
				throws InstantiationException, IllegalAccessException {
			return createProxy(clazz, new MethodInterceptor() {

				public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
						throws Throwable {
					if (method.getName().equals("getMethod")) {
						return info.getMethod();
					} else if (method.getName().equals("getArgs")) {
						return info.getArgs();
					}
					throw new IllegalArgumentException("Only invocations of getMethod and getArgs expected");
				}
			});
		}

	}

	public static <T> T delegatingTo(Class<T> clazz) {
		try {
			return createProxy(clazz, new MethodInfoEnhancer());
		} catch (Exception e) {
			throw new DynamicFunctionWrapperException("could not create proxy", e);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T createProxy(Class<T> clazz, MethodInterceptor interceptor) throws InstantiationException,
			IllegalAccessException {
		Enhancer enhancer = new Enhancer() {
			@SuppressWarnings("rawtypes")
			@Override
			protected void filterConstructors(Class sc, List constructors) {
			}
		};
		enhancer.setSuperclass(clazz);
		enhancer.setInterfaces(new Class[] { MethodInfo.class });
		enhancer.setCallback(interceptor);
		return (T) enhancer.create();
	}

	public static <F, T> Function<F, T> newFunction(final T exampleProxy) {
		return new Function<F, T>() {

			@SuppressWarnings("unchecked")
			public T apply(F input) {
				try {
					MethodInfo info = (MethodInfo) exampleProxy;
					return (T) info.getMethod().invoke(input, info.getArgs());
				} catch (Exception e) {
					throw new DynamicFunctionWrapperException("could not create function", e);
				}
			}
		};
	}

}
