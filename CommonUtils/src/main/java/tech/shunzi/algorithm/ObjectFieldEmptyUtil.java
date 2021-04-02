package tech.shunzi.algorithm;

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

    /**
     * Description: Find empty field in a model by reflecting.
     * @param object The object need to be judged.
     * @param whiteListField Some field which do not need to be judge.
     * @return Empty Field Names List.
     */
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
                ///////////////////////////////////////////////////////////////////////////////
                // The two ways to get the value of field.
                ///////////////////////////////////////////////////////////////////////////////
                // 1. Change the access of field. Private -> Public. And reflect get value.
                // 2. Build the name of getXXX(), and call get method of field.
                ///////////////////////////////////////////////////////////////////////////////
                // The first way is as follows.
                //////////////////////////////////////////////////////////////////////////////
                // Tip: After getting value by changing the access, you'd better to change the access as before.
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

    /**
     * Make the fields of object be empty.
     * @param object Need to empty model.
     * @param whiteListField Some fields which needn't to empty.
     * @return Object including empty fields.
     */
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

    /**
     * Validate the length of String field and return invalid fields names.
     * @param object object need to be validated
     * @param whiteListField some fields which needn't to be validated
     * @return Invalid fields names.
     */
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
