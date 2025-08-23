package eu.klavenessdigital.directory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileParsingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFileParsing(FileParsingException ex) {
        return "File parsing error: " + ex.getMessage();
    }

    @ExceptionHandler(InvalidCsvFormatException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidCsv(InvalidCsvFormatException ex) {
        return "Invalid CSV format: " + ex.getMessage();
    }

    @ExceptionHandler(TreeBuildingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleTreeBuilding(TreeBuildingException ex) {
        return "Tree building failed: " + ex.getMessage();
    }

    @ExceptionHandler(FilterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFilter(FilterException ex) {
        return "Filter error: " + ex.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalState(IllegalStateException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneral(Exception ex) {
        return "Unexpected error: " + ex.getMessage();
    }
}

