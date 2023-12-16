-- liquibase formatted sql

-- changeset egor:1

create table comments (
    id int primary key,
    author_id int,
    created_at int8,
    text varchar
);


create table users (
    id int primary key,
    email varchar,
    password varchar,
    first_name varchar,
    last_name varchar,
    phone varchar,
    role varchar,
    image_link varchar
);

create table ads (
    id int primary key,
    author_id int,
    email varchar,
    description varchar,
    image_link varchar,
    price int,
    title varchar
);
