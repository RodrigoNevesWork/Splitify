package project.splitify.http.tripController

import project.splitify.http.PurchaseControler.Purchase

data class TripPurchases(val trip : Trip, val purchases : List<Purchase>)