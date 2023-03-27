package project.splitify.domain



abstract class BadRequest(msg : String) : Exception(msg)

abstract class NotFound(msg : String) : Exception(msg)

abstract class UnauthorizedRequest(msg : String) : Exception(msg)

abstract class Forbidden(msg : String) : Exception(msg)

class FriendYourself : BadRequest("Can't be friends with yourself")

class EmailAlreadyInUse : BadRequest("Email already in Use")

class PhoneAlreadyInUse : BadRequest("Phone number already in use")

class TripNotExists : NotFound("This trip does not exists")

class NotInThisTrip : Forbidden("User not is this trip")

class NotFriends : Forbidden("Users Not Friends")

class AlreadyFriends : BadRequest("Already Friends with this User")

class AlreadyInThisTrip : BadRequest("User already in this trip")

class Unauthorized : UnauthorizedRequest("Unauthorized")

class WeakPassword : BadRequest("Password too weak")

class UserNotExists : NotFound("User does Not exists")

class BadEmail : BadRequest("Bad Email")

class BadPhone : BadRequest("Bad Phone")

class NotBuyer : UnauthorizedRequest("Not the buyer of this Purchase")

class AlreadyPayed : BadRequest("Alreday Payed this Purchase")

class PurchaseNotExists : NotFound("This Purchase does not exists")

class PurchaseNotInATripOfUser : Forbidden("Purchase not in a trip of User")









