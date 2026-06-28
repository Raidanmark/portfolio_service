package portfolioservice.di;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DiContainer {

    private final Set<Class<?>> registeredClasses = new HashSet<>();
    private final Map<Class<?>, Object> singletons = new HashMap<>();

    public void register(Class<?> clazz) {
        registeredClasses.add(clazz);
    }

    public <T> T get(Class<T> clazz) {
        Object existingObject = singletons.get(clazz);

        if (existingObject != null) {
            return clazz.cast(existingObject);
        }

        if (!registeredClasses.contains(clazz)) {
            throw new RuntimeException("Class " + clazz.getName() + " is not registered in the DI container.");
        }

        T createdObject = createObject(clazz);
        singletons.put(clazz, createdObject);

        return createdObject;
    }

    private <T> T createObject(Class<T> clazz) {
        try {
            Constructor<?> constructor = getConstructor(clazz);

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = get(parameterTypes[i]);
            }

            Object object = constructor.newInstance(dependencies);

            return clazz.cast(object);
        } catch (Exception exception) {
            throw new RuntimeException("Cannot create object: " + clazz.getName(), exception);
         }
    }

    private Constructor<?> getConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length != 1) {
            throw new RuntimeException(
                    "Class must have exactly one constructor: " + clazz.getName()
            );
        }

        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);

        return constructor;
    }



}
