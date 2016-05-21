/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

import com.sanjnan.server.pojos.constants.H2OConstants;
import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

/**
 * The type Global exception controller.
 *
 * @author nikhilvs9999
 */
/*o/.
  This class is responsible of handling all exceptions, like giving proper response back
  to client, whether to log exception or not
*/
@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

  private static final Logger logger = Logger.getLogger(GlobalExceptionController.class);

  private String getExceptionDetails(Exception ex) {
//    StringWriter errors = new StringWriter();
//    ex.printStackTrace(new PrintWriter(errors));

    return ex.getLocalizedMessage().isEmpty()?ex.toString():ex.getLocalizedMessage();
  }


  /**
   * Handle exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = NullPointerException.class)
  @ResponseBody
  public ResponseEntity<Object> handleNullPointerException(WebRequest request, Exception hbe) {
    ExceptionResponse e;
    logger.error(hbe, hbe);
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
            .setCode(status.value())
            .setMessage(message)
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseEntity<Object> handleException(WebRequest request, Exception hbe) {
    ExceptionResponse e;
    logger.error(hbe, hbe);
    HttpStatus status = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
        .setMessage(message)
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle hubble base exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = HubbleBaseException.class)
  @ResponseBody
  public ResponseEntity<Object> handleHubbleBaseException(WebRequest request, HubbleBaseException hbe) {
    ExceptionResponse e;
    logger.error(hbe, hbe);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
        .setMessage(message)
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(hbe.getErrorCode())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle infrastructure platform exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = InfrastructurePlatformException.class)
  @ResponseBody
  public ResponseEntity<Object> handleInfrastructurePlatformException(WebRequest request, InfrastructurePlatformException hbe) {
    ExceptionResponse e;
    logger.error(hbe, hbe);
    HttpStatus status = HttpStatus.FAILED_DEPENDENCY;
    e = new ExceptionResponseBuilder()
            .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle transaction system exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = TransactionSystemException.class)
  @ResponseBody
  public ResponseEntity<Object> handleTransactionSystemException(WebRequest request, TransactionSystemException hbe) {
    ExceptionResponse e;
    logger.error(hbe, hbe);
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    e = new ExceptionResponseBuilder()
            .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle patching exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = PatchingException.class)
  @ResponseBody
  public ResponseEntity<Object> handlePatchingException(WebRequest request, PatchingException hbe) {
    ExceptionResponse e;
    logger.error(hbe, hbe);
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle persistence exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = PersistenceException.class)
  @ResponseBody
  public ResponseEntity<Object> handlePersistenceException(WebRequest request, PersistenceException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle authentication exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = AuthenticationException.class)
  @ResponseBody
  public ResponseEntity<Object> handleAuthenticationException(WebRequest request, AuthenticationException hbe) {
    ExceptionResponse e;
    HttpStatus status = org.springframework.http.HttpStatus.UNAUTHORIZED;
    e = new ExceptionResponseBuilder()
            .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle not found exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = NotFoundException.class)
  @ResponseBody
  public ResponseEntity<Object> handleNotFoundException(WebRequest request, NotFoundException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.NOT_FOUND;
    e = new ExceptionResponseBuilder()
        .setCode(hbe.getErrorCode())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle entity not found exception response entity.
   *
   * @param request the request
   * @param enfe    the enfe
   * @return the response entity
   */
  @ExceptionHandler(value = EntityNotFoundException.class)
  @ResponseBody
  public ResponseEntity<Object> handleEntityNotFoundException(WebRequest request, EntityNotFoundException enfe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.NOT_FOUND;
    e = new ExceptionResponseBuilder()
        .setCode(enfe.getErrorCode())
            .setMessage(getExceptionDetails(enfe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(enfe, e, headers, status, request);
  }

  /**
   * Handle unprocessable exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = UnprocessableException.class)
  @ResponseBody
  public ResponseEntity<Object> handleUnprocessableException(WebRequest request, UnprocessableException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    e = new ExceptionResponseBuilder()
            .setCode(hbe.getErrorCode())
            .setMessage(getExceptionDetails(hbe))
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle camera service exceptions response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = CameraServiceException.class)
  @ResponseBody
  public ResponseEntity<Object> handleCameraServiceExceptions(WebRequest request, CameraServiceException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.BAD_REQUEST;
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.TEXT_HTML);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle bad request exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = BadRequestException.class)
  @ResponseBody
  public ResponseEntity<Object> handleBadRequestException(WebRequest request, BadRequestException hbe) {
    ExceptionResponse e;
    HttpStatus status = org.springframework.http.HttpStatus.BAD_REQUEST;
    e = new ExceptionResponseBuilder()
        .setCode(hbe.getCode())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, hbe.getCode()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle bad request exception response entity.
   *
   * @param request the request
   * @param bce     the bce
   * @return the response entity
   */
  @ExceptionHandler(value = BadCredentialsException.class)
  @ResponseBody
  public ResponseEntity<Object> handleBadCredentialsException(WebRequest request, BadCredentialsException bce) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    e = new ExceptionResponseBuilder()
            .setCode(status.value())
            .setMessage(getExceptionDetails(bce))
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(bce, e, headers, status, request);
  }

  /**
   * Handle access denied request exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = AccessDeniedException.class)
  @ResponseBody
  public ResponseEntity<Object> handleAccessDeniedRequestException(WebRequest request, AccessDeniedException hbe) {
    ExceptionResponse e;
    HttpStatus status = org.springframework.http.HttpStatus.BAD_REQUEST;
    e = new ExceptionResponseBuilder()
        .setCode(hbe.getCode())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, hbe.getCode()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle authorization exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = AuthorizationException.class)
  @ResponseBody
  public ResponseEntity<Object> handleAuthorizationException(WebRequest request, AuthorizationException hbe) {
    ExceptionResponse e;
    HttpStatus status = org.springframework.http.HttpStatus.FORBIDDEN;
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle generic jdbc exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = GenericJDBCException.class)
  @ResponseBody
  public ResponseEntity<Object> handleGenericJDBCException(WebRequest request, GenericJDBCException hbe) {
    ExceptionResponse e;
    HttpStatus status = org.springframework.http.HttpStatus.BAD_REQUEST;
    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
        .setMessage(message)
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle resource not found exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = ResourceNotFoundException.class)
  @ResponseBody
  public ResponseEntity<Object> handleResourceNotFoundException(WebRequest request, ResourceNotFoundException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.NOT_FOUND;
    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
        .setCode(status.value())
        .setMessage(message)
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle failed dependency exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = FailedDependencyException.class)
  @ResponseBody
  public ResponseEntity<Object> handleFailedDependencyException(WebRequest request, FailedDependencyException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.NOT_FOUND;
    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
        .setCode(hbe.getErrorCode())
        .setMessage(message)
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, hbe.getErrorCode()))
        .setStatus(hbe.getErrorCode())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }

  /**
   * Handle message exception response entity.
   *
   * @param request the request
   * @param hbe     the mse
   * @return the response entity
   */
  @ExceptionHandler(value = FailDependencyMessageException.class)
  @ResponseBody
  public ResponseEntity<Object> handleMessageException(WebRequest request, FailDependencyMessageException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.NOT_FOUND;
    String message = getExceptionDetails(hbe);
    e = new ExceptionResponseBuilder()
            .setCode(hbe.getErrorCode())
            .setMessage(message)
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, hbe.getErrorCode()))
            .setStatus(hbe.getErrorCode())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe, e, headers, status, request);
  }


  /**
   * Handle recurly hubble exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value = BusinessInterfaceException.class)
  @ResponseBody
  public ResponseEntity<Object> handleRecurlyHubbleException(WebRequest request, BusinessInterfaceException hbe) {
    ExceptionResponse e;
    HttpStatus status = HttpStatus.FAILED_DEPENDENCY;
    e = new ExceptionResponseBuilder()
        .setCode(hbe.getErrorCode())
            .setMessage(getExceptionDetails(hbe))
        .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, status.value()))
        .setStatus(status.value())
        .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe,e, headers,status, request);
  }

  /**
   * Handle internal error with data exception response entity.
   *
   * @param request the request
   * @param hbe     the hbe
   * @return the response entity
   */
  @ExceptionHandler(value=InternalErrorWithDataException.class)
  @ResponseBody
  public ResponseEntity<Object> handleInternalErrorWithDataException(WebRequest request,InternalErrorWithDataException hbe){
    ExceptionResponse e;
    HttpStatus status = HttpStatus.FAILED_DEPENDENCY;
    e = new ExceptionResponseBuilder()
            .setCode(hbe.getErrorCode())
            .setMessage(getExceptionDetails(hbe))
            .setMoreInfo(String.format(H2OConstants.EXCEPTION_URL, hbe.getErrorCode()))
            .setStatus(status.value())
            .createExceptionResponse();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(hbe,e, headers,status, request);
  }
}
