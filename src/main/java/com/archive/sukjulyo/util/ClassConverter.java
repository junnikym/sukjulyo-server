package com.archive.sukjulyo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassConverter {

	/**
	 * The data is transferred by matching properties of the same name.
	 *
	 * @param source : Data to be transferred.
	 * @param destination : Destination to be moved object
	 *
	 * @param <S_T> : Source (Parameter) Type
	 * @param <D_T> : Destination (parameter) Type
	 *
	 * @return Data transferred.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static <S_T, D_T> D_T convertWithoutNull (S_T source, D_T destination) throws InvocationTargetException, IllegalAccessException {
		Class sourceClass = source.getClass();
		Class destinationClass = destination.getClass();

		String fieldNames[] = Arrays
				.stream(sourceClass.getDeclaredFields())
				.map(Field::getName)
				.toArray(String[]::new);

		Map<String, Method> sourceMethods = Arrays
				.stream(sourceClass.getDeclaredMethods())
				.collect(Collectors.toMap(Method::getName, x->x));

		Map<String, Field> destinationFields = Arrays
				.stream(destinationClass.getDeclaredFields())
				.collect(Collectors.toMap(Field::getName, x->x));

		for (String it : fieldNames) {

			// Find Source's Getter
			Method getter = sourceMethods.get(
					"get"+it.substring(0, 1).toUpperCase() + it.substring(1));
			if(getter == null)
				continue;

			// Check it is NULL.
			Object object = getter.invoke(source);
			if(object == null)
				continue;

			// Update Destination's properties
			Field target = destinationFields.get(it);
			if(target == null)
				continue;

			target.setAccessible(true);
			target.set(destination, object);
		}

		return destination;
	}

}
