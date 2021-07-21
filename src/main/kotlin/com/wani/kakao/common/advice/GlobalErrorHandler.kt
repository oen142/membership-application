package com.wani.kakao.common.advice

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.wani.kakao.common.dto.response.CommonResponse
import com.wani.kakao.common.dto.response.ErrorResponse
import com.wani.kakao.common.exception.GeneralHttpException
import com.wani.kakao.common.exception.client.IllegalRequestException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Controller
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException

@Controller
@RestControllerAdvice
class GlobalErrorHandler : ErrorController {

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleNoController(
        req: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<CommonResponse<ErrorResponse>> {
        return handleError(
            req, GeneralHttpException.createPlain(
                HttpStatus.NOT_FOUND,
                "${req.method} ${req.requestURI} 해당 서버를 지원하지 않거나 찾지 못했습니다.",
                ex
            )
        )
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationFailure(
        req: HttpServletRequest,
        ex: ValidationException
    ): ResponseEntity<CommonResponse<ErrorResponse>> {
        val message = ex.message

        return handleError(
            req, if (message.isNullOrEmpty()) {
                IllegalRequestException(cause = ex)
            } else {
                IllegalRequestException(message = message, cause = ex)
            }
        )
    }

    @ExceptionHandler(InvalidFormatException::class)
    fun handleValidationFailure(
        req: HttpServletRequest,
        ex: InvalidFormatException
    ): ResponseEntity<CommonResponse<ErrorResponse>> {
        val message = "${ex.message} 을 읽을수 없습니다."

        return handleError(
            req, if (message.isNullOrEmpty()) {
                IllegalRequestException(cause = ex)
            } else {
                IllegalRequestException(message = message, cause = ex)

            }
        )
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleSpring404(req: HttpServletRequest): ResponseEntity<CommonResponse<ErrorResponse>> {
        return handleError(
            req, GeneralHttpException.create(HttpStatus.NOT_FOUND, req.requestURI ?: "")
        )
    }

    @Suppress("LongMethod", "ReturnCount")
    @ExceptionHandler(Exception::class)
    fun handleError(
        req: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<CommonResponse<ErrorResponse>> {
        val status: HttpStatus
        val response = when (ex) {
            is GeneralHttpException -> {
                logError(req, "Handled exception:", ex)
                status = ex.httpStatus
                CommonResponse.error(status.value(), ex::class.simpleName ?: "")
            }
            is HttpMediaTypeNotSupportedException -> {
                if (ex.cause is Exception) {
                    return handleError(req, ex.cause as Exception)
                }

                logError(req, "Spring handled exception:", ex)
                status = HttpStatus.BAD_REQUEST
                CommonResponse.error(status.value(), ex::class.simpleName ?: "")
            }
            is HttpMessageNotReadableException -> {
                if (ex.cause is Exception) {
                    return handleError(req, ex.cause as Exception)
                }

                logError(req, "Spring handled exception:", ex)
                status = HttpStatus.BAD_REQUEST
                CommonResponse.error(status.value(), "HttpMessageNotReadable Exception")
            }
            is InvalidFormatException -> {
                if (ex.cause is Exception) {
                    return handleError(req, ex.cause as Exception)
                }

                logError(req, "JSON parsing exception: ${ex.message}", ex)
                status = HttpStatus.BAD_REQUEST

                CommonResponse.error(
                    status.value(),
                    "해당 request 또는 response를 json으로 parsing하는데 실패하셨습니다."
                )
            }
            is JsonProcessingException -> {
                if (ex.cause is Exception) {
                    return handleError(req, ex.cause as Exception)
                }

                logError(req, "JSON parsing exception: ${ex.message}", ex)
                status = HttpStatus.BAD_REQUEST

                CommonResponse.error(
                    status.value(),
                    "해당 request 또는 response를 json으로 parsing하는데 실패하셨습니다."
                )
            }
            is MethodArgumentNotValidException -> {
                LOG.error(
                    "{} {}: 클라이언트로부터의 예외입니다 :",
                    req.method,
                    req.requestURI
                )
                val msgs = ex.bindingResult.allErrors.map {
                    LOG.error("  {}", it.defaultMessage)
                    return@map it.defaultMessage
                }
                val msg = msgs.joinToString()
                return handleError(
                    req,
                    if (msg.isEmpty()) {
                        IllegalRequestException(cause = ex)
                    } else {
                        IllegalRequestException(msg, ex)
                    }
                )
            }
            else -> {
                if (ex.cause is Exception) {
                    return handleError(req, ex.cause as Exception)
                } else {
                    logError(req, "Unhandled exception: $ex", ex)
                    status = getStatus(req)
                    CommonResponse.error(
                        status.value(),
                        "지원하지 않는 서버입니다 : ${ex.message}"
                    )
                }
            }
        }
        return ResponseEntity(response, status)
    }

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): ResponseEntity<CommonResponse<ErrorResponse>> {
        (request.getAttribute("javax.servlet.error.exception") as? Exception)?.let {
            return handleError(request, it)
        }

        return handleError(request, GeneralHttpException.create(getStatus(request)))
    }


    private fun getStatus(request: HttpServletRequest): HttpStatus {
        (request.getAttribute("javax.servlet.error.status_cod") as? Int)?.let {
            return HttpStatus.valueOf(it)
        }

        return HttpStatus.SERVICE_UNAVAILABLE
    }

    private fun logError(req: HttpServletRequest, message: String, ex: Exception) =
        LOG.error("{} {}: {}", req.method, req.requestURI, message, ex)

    companion object {
        private val LOG = LoggerFactory.getLogger(GlobalErrorHandler::class.java)
    }

}