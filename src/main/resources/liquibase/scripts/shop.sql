-- liquibase formatted sql

-- changeset egor:1

create table comments (
    id int primary key,
    created_at int8,
    text varchar,
    ad_id int,
    user_id int
);


create table users (
    id int primary key,
    email varchar,
    password varchar,
    first_name varchar,
    last_name varchar,
    phone varchar,
    role varchar,
    avatar_id int
);

create table ads (
    id int primary key,
    description varchar,
    email varchar,
    price int,
    title varchar,
    user_id int,
    image_id int
);

create table avatars (
    id int primary key,
    name varchar,
    original_file_name varchar,
    size int8,
    content_type varchar,
    "bytes" oid NULL,
    user_id int
);

create table images (
    id int primary key,
    name varchar,
    original_file_name varchar,
    size int8,
    content_type varchar,
    "bytes" oid NULL,
    ad_id int
);
