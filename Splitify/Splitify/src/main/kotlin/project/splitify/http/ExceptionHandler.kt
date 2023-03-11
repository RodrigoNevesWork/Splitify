package project.splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import project.splitify.domain.*

data class ProblemJson(val message : String, val code : Int)

private fun Exception.toProblemJson() : ProblemJson = when(this){
    is EmailAlreadyInUse -> ProblemJson("Email Already Registered", 409)
    is PhoneAlreadyInUse -> ProblemJson("Phone Already Registered", 409)
    is TripNotExists -> ProblemJson("This trip does not exists", 404)
    is NotInThisTrip -> ProblemJson("You are not in this trip", 401)
    is Unauthorized -> ProblemJson("Unauthorized", 401)
    is WeakPassword -> ProblemJson("Weak Password", 401)
    is BadEmail -> ProblemJson("Email does not Exists", 401)
    is BadPhone -> ProblemJson("This Phone does not Exists", 401)
    is AlreadyInThisTrip -> ProblemJson("User Already in this trip", 401)
    else -> ProblemJson(this.message ?: "Internal Error", 500)
}


private fun Exception.toResponseProblem() : ResponseEntity<String> {
    val response = this.toProblemJson()
    return ResponseEntity.status(response.code).body(response.message)

}

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [Exception::class])
    protected fun handleConflict( error : Exception) = error.toResponseProblem()
}