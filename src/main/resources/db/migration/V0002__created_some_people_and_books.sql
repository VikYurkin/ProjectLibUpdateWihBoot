insert into person(name, year_birth) VALUES ('Иванов Иван Иванович', 1970);
insert into person(name, year_birth) VALUES ('Петров Петр Петрович', 1960);
insert into person(name, year_birth) VALUES ('Алексеев Алексей Алексеевич', 1989);
insert into person(name, year_birth) VALUES ('Познер Владимир Владимирович', 1944);
insert into person(name, year_birth) VALUES ('Федоров Мирон Янович', 1985);

insert into book(person_id, name_book, name_author, year_written, date_of_person) values (null, 'Над пропастью во ржи', 'Джером Сэлинджер', 1951, null);
insert into book(person_id, name_book, name_author, year_written, date_of_person) values (null, 'Психопатология обыденной жизни', 'Фрейд Зигмунд', 1904, null);
insert into book(person_id, name_book, name_author, year_written, date_of_person) values (null, 'Игра в бисер', 'Герман Гессе', 1943, null);
insert into book(person_id, name_book, name_author, year_written, date_of_person) values (null, 'Бытие и время', 'Мартин Хайдеггер', 1927, null);
insert into book(person_id, name_book, name_author, year_written, date_of_person) values (null, 'Тайные виды на гору Фудзи', 'Владимир Пелевин', 2018, null);
insert into book(person_id, name_book, name_author, year_written, date_of_person) values (1, 'День опричника', 'Владимир Сорокин', 2006, '2023-04-10 20:54:50.000000');
insert into book(person_id, name_book, name_author, year_written, date_of_person) values (1, 'Философия Java', 'Брюс Эккель', 2018, '2023-03-01 20:54:50.000000');