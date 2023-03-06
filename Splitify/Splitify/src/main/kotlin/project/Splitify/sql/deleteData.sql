ALTER sequence  dbo.user_id_seq RESTART WITH 1;
ALTER sequence  dbo.trip_id_seq RESTART WITH 1;
truncate dbo.user, dbo.trip, dbo.purchase;