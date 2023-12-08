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

create table ads (
    id int,
    authorId int,
    imageLink varchar,
    price int,
    title varchar
)

create table ext_ads (
    id int,
    price int,
    authorFirstName varchar,
    authorLastName varchar,
    description varchar,
    email varchar,
    imageLink varchar,
    title varchar
)

create table users (
    id int,
    email varchar,
    firstName varchar,
    lastName varchar,
    phone varchar,
    role varchar,
    imageLink varchar
)