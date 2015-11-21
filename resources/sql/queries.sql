-- name: create-temperature!
-- creates a new temp record
INSERT INTO temperature
(read_time, temp_celcius)
VALUES (:read_time, :temp_celcius)

-- name: get-all-temps
-- retrieve all temperatures
SELECT read_time, temp_celcius FROM temperature

-- name: get-latest-temps
-- retrieve temperatures later than provided date/time
SELECT read_time, temp_celcius FROM temperature
WHERE read_time > :later_than

-- name: get-temps-in-range
-- retrieve temperatures within the provided date range
SELECT read_time, temp_celcius FROM temperature
WHERE read_time > :later_than and read_time < :earlier_than

-- name: delete-user!
-- delete a user given the id
DELETE FROM temperature
WHERE read_time = :read_time
