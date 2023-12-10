-- liquibase formatted sql

-- changeset egor:1

create table comments (
    id int,
    authorId int,
    authorFirstName varchar,
    authorImgLink varchar,
    createdAt int,
    text varchar
)


create table users (
    id int,
    email varchar,
    password varchar,
    firstName varchar,
    lastName varchar,
    phone varchar,
    role varchar,
    imageLink varchar
)

create table ads (
    id int,
    authorFirstName varchar,
    authorLastName varchar,
    authorId int,
    email varchar,
    description varchar,
    imageLink varchar,
    price int,
    title varchar
)