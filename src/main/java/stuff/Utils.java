package stuff;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class Utils {

    public static String getSelectedString(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        if (editor == null) {
            return null;
        }
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        return primaryCaret.getSelectedText();
    }

    public static final String NESTED_PROPERTY_SEPARATOR = ".";

    public static Field getPropertyField(Class<?> beanClass, String property) throws NoSuchFieldException {
        if (beanClass == null)
            throw new IllegalArgumentException("beanClass cannot be null");

        Field field = null;
        try {
            field = beanClass.getDeclaredField(property);
        } catch (NoSuchFieldException e) {
            if (beanClass.getSuperclass() == null)
                throw e;
            // look for the field in the superClass
            field = getPropertyField(beanClass.getSuperclass(), property);
        }
        return field;
    }

    public static Field getField(Class<?> beanClass, String propertyPath) throws NoSuchFieldException {
        if (beanClass == null)
            throw new IllegalArgumentException("beanClass cannot be null");

        if (propertyPath.indexOf("[") != -1)
            propertyPath = propertyPath.substring(0, propertyPath.indexOf("["));

        // if the property path is simple then look for it directly on the class.
        if (propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR) == -1) {
            // look if the field is declared in this class.
            return getPropertyField(beanClass, propertyPath);
        } else {
            // if the property is a compound one then split it and look for the first field.
            // and recursively locate fields of fields.
            String propertyName = propertyPath.substring(0, propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR));
            Field field = getField(beanClass, propertyName);

            // try to locate sub-properties
            return getField(getTargetType(field),
                    propertyPath.substring(propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR) + 1));
        }
    }

    public static Class<?> getTargetType(Field field) {
        // Generic type, case when we have a Collection of ?
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            if (type.getActualTypeArguments().length == 1 && type.getActualTypeArguments()[0] instanceof Class)
                return (Class<?>) type.getActualTypeArguments()[0];
        }

        return field.getType();
    }

    public static Class<?> getPropertyClass(Class<?> beanClass, String propertyPath) {
        try {
            Field field = getField(beanClass, propertyPath);
            return (getTargetType(field));
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(propertyPath + " is not a property of " + beanClass.getName());
        }
    }

    public static boolean isFieldDeclared(Class<?> beanClass, String propertyPath) {
        try {
            return getField(beanClass, propertyPath) != null;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public static Object getPropertyValue(Object bean, String propertyPath) throws NoSuchFieldException {
        if (bean == null)
            throw new IllegalArgumentException("bean cannot be null");
        Field field = Utils.getField(bean.getClass(), propertyPath);
        field.setAccessible(true);
        try {
            return (field.get(bean));
        } catch (IllegalAccessException e) {
            return (null);
        }
    }
}
