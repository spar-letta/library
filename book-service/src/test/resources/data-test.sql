-- users
insert into library.users (id, public_id, user_name, deleted, contact_email, first_name) values (1,'7a40fd66-36a6-4487-9509-e9b12a61bff9','testuser',false,'admin@gmail.com','peterKason');
insert into library.users (id, public_id, user_name, deleted, contact_email, first_name) values (2,'2d7c44c0-3e3c-415d-b4e3-feb799f41e04','Cao',false,'xueqin@gmail.com','Xueqin');
insert into library.users (id, public_id, user_name, deleted, contact_email, first_name) values (3,'054e1a02-9cd4-476b-bb4b-22fda2ef02df','Charles',false,'dickens@gmail.com','Dickens');
insert into library.users (id, public_id, user_name, deleted, contact_email, first_name) values (4,'7ba79c3b-f16b-44da-9696-0174e36d7021','Agatha',false,'christie@gmail.com','Christie');

insert into library.books(book_id, public_id, deleted, title, description, type, date_published, price) values (100, '7a40fd66-36a6-4487-9509-e9b12a61bff1', false, 'New Covent', 'description', 'Trade_Books', now(), 120.00);
insert into library.book_author(book_id, user_id)values (100, 1)