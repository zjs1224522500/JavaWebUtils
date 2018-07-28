package tech.shunzi.model.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: ObjectFieldEmptyUtil <br/>
 * <br/>
 * Description: check fields in object if null
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class ObjectFieldEmptyUtil
{

    private static Logger logger = LoggerFactory.getLogger(ObjectFieldEmptyUtil.class);
    
    private static final int MAX_LENGTH_OF_STRING = 99;

    public static List<String> findEmptyFields(Object object, List<String> whiteListField)
    {
        List<String> emptyFieldNames = new ArrayList<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            String fieldName = field.getName();
            if (CollectionUtils.isNotEmpty(whiteListField) && whiteListField.contains(fieldName))
            {
                continue;
            }

            try
            {
                field.setAccessible(true);
                if (null == field.get(object) || "".equals(field.get(object)))
                {
                    emptyFieldNames.add(fieldName);
                }
                field.setAccessible(false);
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                logger.error("Reflect error.{}", e);
                throw new RuntimeException("Reflect error!");
            }
        }
        return emptyFieldNames;
    }

    public static Object setEmptyFields(Object object, List<String> whiteListField)
    {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            String fieldName = field.getName();
            if (CollectionUtils.isNotEmpty(whiteListField) && whiteListField.contains(fieldName))
            {
                continue;
            }

            try
            {
                field.setAccessible(true);
                if (null != field.get(object))
                {
                    field.set(object, null);
                }
                field.setAccessible(false);
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                logger.error("Reflect error.{}", e);
                throw new RuntimeException("Reflect error!");
            }
        }
        return object;
    }
    
    public static List<String> findInvalidLengthFields(Object object, List<String> whiteListField)
    {
        List<String> invalidLengthFields = new ArrayList<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            String fieldName = field.getName();
            if ((CollectionUtils.isNotEmpty(whiteListField) && whiteListField.contains(fieldName)) || String.class.equals(field.getClass()))
            {
                continue;
            }

            try
            {
                field.setAccessible(true);
                String value = (String) field.get(object);
                if (null != value && value.length() > MAX_LENGTH_OF_STRING)
                {
                    invalidLengthFields.add(fieldName);
                }
                field.setAccessible(false);
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                logger.error("Reflect error.{}", e);
                throw new RuntimeException("Reflect error!");
            }
        }
        return invalidLengthFields;
    }
}
