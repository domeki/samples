--
-- author: tgiunipero
-- date: 17 july 2009
--

--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Sample data for table `category`
--

INSERT INTO `category` (name) VALUES ('dairy'),('meats'),('bakery'),('fruitVeg');


--
-- Sample data for table `product`
--

TRUNCATE `product`;
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('milk', 1.70, 'semi skimmed (1L)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('cheese', 2.39, 'mild cheddar (330g)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('butter', 1.09, 'unsalted (250g)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('eggs', 1.76, 'free range, medium (6 eggs)', 1);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('meatPatties', 2.29, 'rolled in fresh herbs (2 patties)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('parmaHam', 3.49, 'matured, organic (70g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('chicken', 2.59, 'free range chicken leg (250g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('sausages', 3.55, 'reduced fat pork sausages x3 (350g)', 2);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('loaf', 1.89, '600g', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('bagel', 1.19, '(4 bagels)', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('bun', 1.15, '(4 buns)', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('cookie', 2.39, 'contain peanuts (3 cookies)', 3);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('corn', 1.59, '2 pieces', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('berries', 2.49, '150g', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('broccoli', 1.29, '500g', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('watermelon', 1.49, '250g', 4);