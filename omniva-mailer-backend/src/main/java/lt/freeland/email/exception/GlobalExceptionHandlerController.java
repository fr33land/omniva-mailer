package lt.freeland.email.exception;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.freeland.email.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author r.sabaliauskas
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> requestBodyValidationException(HttpServletRequest request, Exception ex, HandlerMethod handlerMethod) {
        String message = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex)
                    .getBindingResult()
                    .getFieldErrors();
            message = ((MethodArgumentNotValidException) ex)
                    .getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(e -> e.getField() + " " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        }
        log.error("RequestBodyValidationException exception: {}", ex.getMessage());
        return buildRespsonseEntity(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(HttpServletRequest request, EntityNotFoundException ex, HandlerMethod handlerMethod) {
        log.error("EntityNotFoundException exception: {}", ex.getMessage());
        return buildRespsonseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }
    
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(HttpServletRequest request, IllegalArgumentException ex, HandlerMethod handlerMethod) {
        log.error("IllegalArgumentException exception: {}", ex.getMessage());
        return buildRespsonseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildRespsonseEntity(HttpStatus status, String message, String path) {
        return ResponseEntity
                .status(status)
                .body(
                        new ErrorResponse()
                                .timeStamp(OffsetDateTime.now())
                                .status(Integer.toString(status.value()))
                                .error(status.getReasonPhrase())
                                .message(message)
                                .path(path)
                );
    }
}
