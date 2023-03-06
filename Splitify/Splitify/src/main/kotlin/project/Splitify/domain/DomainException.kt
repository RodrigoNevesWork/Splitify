package project.Splitify.domain


abstract class DomainException : Exception()

class EmailAlreadyInUse : DomainException()

class PhoneAlreadyInUse : DomainException()

class TripNotExists : DomainException()

class NotInThisTrip : DomainException()

class Unauthorized : DomainException()

class WeakPassword : DomainException()






