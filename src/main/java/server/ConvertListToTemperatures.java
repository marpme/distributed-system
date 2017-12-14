package server;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConvertListToTemperatures<T> extends AbstractBeanField<T> {

    /**
     * Method for converting from a string to the proper datatype of the
     * destination field.
     * This method must be specified in all non-abstract derived classes.
     *
     * @param value The string from the selected field of the CSV file
     * @return An {@link Object} representing the input data converted
     * into the proper type
     * @throws CsvDataTypeMismatchException    If the input string cannot be converted into
     *                                         the proper type
     * @throws CsvConstraintViolationException When the internal structure of
     *                                         data would be violated by the data in the CSV file
     */
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value == null) throw new CsvConstraintViolationException();

        String[] temps = value.split("%");
        return Arrays.stream(temps).collect(Collectors.toList());
    }
}
