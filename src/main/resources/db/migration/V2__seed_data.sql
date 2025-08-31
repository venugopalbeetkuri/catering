INSERT INTO restaurant (id, name, takeaway, delivery, hours_json)
VALUES (1, 'My Restaurant', TRUE, TRUE, '');

INSERT INTO menu_item (name, price, prep_mins, active, restaurant_id) VALUES
('Masala Dosa', 80, 10, TRUE, 1),
('Veg Biryani', 160, 20, TRUE, 1);
