ALTER sequence  dbo.user_id_seq RESTART WITH 1;
ALTER sequence  dbo.trip_id_seq RESTART WITH 1;
truncate dbo.user, dbo.trip, dbo.purchase, dbo.user_purchase_payed, dbo.user_trip, dbo.friend_list, dbo.friend_request;