select * from oc_customer ;
select * from oc_order ;
SELECT * FROM oc_order_history WHERE order_id = 1 ORDER BY date_added DESC;
SELECT quantity FROM oc_product WHERE product_id = 40;
SELECT title, value FROM oc_order_total WHERE order_id = 1 AND code = 'shipping';
UPDATE oc_address SET city='NewCity' WHERE address_id = 1;
SELECT city FROM oc_address WHERE address_id = 1;


SELECT * FROM oc_order WHERE order_id = 3;
SELECT product_id, quantity, price FROM oc_order_product WHERE order_id = 3;
SELECT shipping_method, payment_method, shipping_address_1 FROM oc_order WHERE order_id = 3;
SELECT price, frequency, cycle FROM oc_order_subscription WHERE order_id = 3;
SELECT COUNT(*) FROM oc_order_subscription s JOIN oc_product p ON s.product_id = p.product_id WHERE s.order_id = 3;
UPDATE oc_order_subscription SET trial_price = 10.00 WHERE order_id = 3;
DELETE FROM oc_order_subscription WHERE order_id = 3;
SELECT product, model, quantity FROM oc_return WHERE return_id = 1;
SELECT r.customer_id, c.email FROM oc_return r JOIN oc_customer c ON r.customer_id = c.customer_id WHERE r.return_id = 1;
SELECT * FROM oc_return WHERE order_id = 2;
SELECT firstname, lastname, email, status FROM oc_customer WHERE customer_id = 2;
SELECT password FROM oc_customer WHERE customer_id = 1;
SELECT * FROM oc_customer WHERE email = '[New Email]';
UPDATE oc_customer SET status = 0 WHERE customer_id = 1;
UPDATE oc_customer SET email = 'test1@gmail.com' WHERE customer_id = 2;
