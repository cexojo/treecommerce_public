package ca.pivotree.treecommerce.controller.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import org.drools.core.util.StringUtils;

/**
 * Created by cexojo on 20/04/2020
 */
@Provider
public class LocalDateConverter implements ParamConverterProvider {
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
		if (!aClass.isAssignableFrom(LocalDate.class)) {
			return null;
		}
		return new ParamConverter<T>() {
			@Override
			@SuppressWarnings("unchecked")
			public T fromString(final String value) {
				if (StringUtils.isEmpty(value)) {
					return null;
				}
				return (T) LocalDate.parse(value);
			}

			@Override
			public String toString(final T value) {
				return ((LocalDate) value).format(DateTimeFormatter.ISO_LOCAL_DATE);
			}
		};
	}
}
