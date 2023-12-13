-- liquibase formatted sql

-- changeset egor:1

create table comments (
    id int primary key,
    foreign key (authorId) references users (id),
    createdAt int,
    text varchar
)


create table users (
    id int primary key,
    email varchar,
    password varchar,
    firstName varchar,
    lastName varchar,
    phone varchar,
    role varchar,
    imageLink varchar
)

create table ads (
    id int primary key,
    foreign key (authorId) references users (id),
    email varchar,
    description varchar,
    imageLink varchar,
    price int,
    title varchar
)