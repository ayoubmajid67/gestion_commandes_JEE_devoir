package utils;

import utils.interfaces.objectConverter.ObjectConverter;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SaveUtil<T> {
    private final ObjectConverter<T> factory;

    public SaveUtil(ObjectConverter<T> factory) {
        this.factory = factory;
    }

    public T saveFormData(Map<String, String> formData) throws Exception {
        // Create a new instance of the object using the factory
        T obj = factory.convertObject();

        // Iterate over the form data and set the fields dynamically
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();

            // Convert the field name to the corresponding setter method name
            String setterName = "set" + capitalize(fieldName);
            // Find the setter method for the field
            Method setter = findSetterMethod(obj.getClass(), setterName, value);

            if (setter != null) {
                // Convert the value to the appropriate type and invoke the setter
                Object convertedValue = convertValue(value, setter.getParameterTypes()[0]);
                setter.invoke(obj, convertedValue);
            } else {
                throw new NoSuchMethodException("Setter method not found for field: " + fieldName);
            }
        }

        return obj;
    }

    // Helper method to capitalize the first letter of a string
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Helper method to find the setter method for a field
    private Method findSetterMethod(Class<?> clazz, String setterName, String value) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                return method;
            }
        }
        return null;
    }

    // Helper method to convert a string value to the appropriate type
    private Object convertValue(String value, Class<?> type) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == Date.class) {
            // Try to parse the date from the value (which is a string)
            try {
                // Trim the value to remove any whitespace
                value = value.trim();
                // Define the expected date format.
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.parse(value); // This will parse the string value into a Date object
            } catch (ParseException e) {
                // If the date format is incorrect, throw an exception with a descriptive message
                throw new IllegalArgumentException("Invalid date format: " + value, e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}