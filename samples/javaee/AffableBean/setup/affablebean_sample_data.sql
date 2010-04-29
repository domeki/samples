-- Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
--
-- You may not modify, use, reproduce, or distribute this software
-- except in compliance with the terms of the license at:
-- http://developer.sun.com/berkeley_license.html
--
-- author: tgiunipero
--


--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Sample data for table `category`
--

INSERT INTO `category` (name) VALUES ('dairy'),('meats'),('bakery'),('fruitVeg');
--INSERT INTO `category` (name) VALUES ('dairy'),('meats'),('bakery'),('fruit & veg');


--
-- Sample data for table `product`
--

TRUNCATE `product`;
--INSERT INTO `product` (`name`, price, category_id) VALUES ('milk', 1.70, 1);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('cheese', 2.39, 1);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('butter', 1.09, 1);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('eggs', 1.76, 1);
--
--INSERT INTO `product` (`name`, price, category_id) VALUES ('meatPatties', 2.29, 2);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('parmaHam', 3.49, 2);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('chicken', 2.59, 2);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('sausages', 3.55, 2);
--
--INSERT INTO `product` (`name`, price, category_id) VALUES ('loaf', 1.89, 3);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('bagel', 1.19, 3);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('bun', 1.15, 3);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('cookie', 2.39, 3);
--
--INSERT INTO `product` (`name`, price, category_id) VALUES ('corn', 1.59, 4);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('berries', 2.49, 4);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('broccoli', 1.29, 4);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('watermelon', 1.49, 4);


INSERT INTO `product` (`name`, price, description, category_id) VALUES ('milk', 1.70, 'semi skimmed (1L)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('cheese', 2.39, 'mild cheddar (330g)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('butter', 1.09, 'unsalted (250g)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('eggs', 1.76, 'medium-sized (6 eggs)', 1);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('meat patties', 2.29, 'rolled in fresh herbs<br>2 patties (250g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('parma ham', 3.49, 'matured, organic (70g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('chicken', 2.59, 'free range (250g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('sausages', 3.55, 'reduced fat, pork<br>3 sausages (350g)', 2);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('loaf', 1.89, '600g', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('bagel', 1.19, '4 bagels', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('bun', 1.15, '4 buns', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('cookie', 2.39, 'contain peanuts<br>(3 cookies)', 3);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('corn', 1.59, '2 pieces', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('berries', 2.49, '150g', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('broccoli', 1.29, '500g', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('watermelon', 1.49, '250g', 4);