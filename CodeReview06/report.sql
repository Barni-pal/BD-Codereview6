USE CR6;

SELECT name, surname
FROM class
JOIN students USING(class_id)
WHERE class.class_id = 3;

SELECT name
FROM class
JOIN students USING(class_id)
WHERE class.name = "1.b";