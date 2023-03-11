package project.splitify.domain


abstract class DomainException : Exception()

class EmailAlreadyInUse : DomainException()

class PhoneAlreadyInUse : DomainException()

class TripNotExists : DomainException()

class NotInThisTrip : DomainException()

class AlreadyInThisTrip : DomainException()

class Unauthorized : DomainException()

class WeakPassword : DomainException()

class UserNotExists : DomainException()

class BadEmail : DomainException()

class BadPhone : DomainException()

class NotBuyer : DomainException()

class AlreadyPayed : DomainException()








