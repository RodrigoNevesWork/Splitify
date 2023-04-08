package project.splitify.http.tripController.tripDTO

import project.splitify.http.purchaseControler.purchaseDTO.Purchase

data class TripPurchases(val trip : Trip, val purchases : List<Purchase>)