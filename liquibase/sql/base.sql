CREATE TYPE role_enum AS ENUM (
  'admin',
  'instructor',
  'student'
);
CREATE TABLE "user"
(
    "id"       uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "username" varchar UNIQUE                              NOT NULL,
    "password" varchar                                     NOT NULL
);
CREATE TABLE "role"
(
    "id"   uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "name" role_enum UNIQUE                            NOT NULL
);
CREATE TABLE "course"
(
    "id"   uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "name" varchar UNIQUE                              NOT NULL
);
CREATE TABLE "lesson"
(
    "id"        uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "name"      varchar UNIQUE                              NOT NULL,
    "course_id" uuid                                        NOT NULL
);
CREATE TABLE "homework"
(
    "id"         uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "file"       bytea                                       NOT NULL,
    "student_id" uuid                                        NOT NULL,
    "lesson_id"  uuid                                        NOT NULL
);
CREATE TABLE "mark"
(
    "id"            uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "mark"          integer                                     NOT NULL,
    "instructor_id" uuid                                        NOT NULL,
    "student_id"    uuid                                        NOT NULL,
    "lesson_id"     uuid                                        NOT NULL
);
CREATE TABLE "feedback"
(
    "id"            uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    "feedback"      varchar                                     NOT NULL,
    "student_id"    uuid,
    "instructor_id" uuid,
    "course_id"     uuid
);
CREATE TABLE "student_course"
(
    "student_id" uuid,
    "course_id"  uuid
);
CREATE TABLE "instructor_course"
(
    "instructor_id" uuid,
    "course_id"     uuid
);
CREATE TABLE "user_role"
(
    "role_id"    uuid,
    "user_id"    uuid,
    "predefined" boolean default false
);
ALTER TABLE "homework"
    ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");
ALTER TABLE "homework"
    ADD FOREIGN KEY ("lesson_id") REFERENCES "lesson" ("id");
ALTER TABLE "mark"
    ADD FOREIGN KEY ("instructor_id") REFERENCES "user" ("id");
ALTER TABLE "mark"
    ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");
ALTER TABLE "mark"
    ADD FOREIGN KEY ("lesson_id") REFERENCES "lesson" ("id");
ALTER TABLE "feedback"
    ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");
ALTER TABLE "feedback"
    ADD FOREIGN KEY ("instructor_id") REFERENCES "user" ("id");
ALTER TABLE "feedback"
    ADD FOREIGN KEY ("course_id") REFERENCES "course" ("id");
ALTER TABLE "student_course"
    ADD FOREIGN KEY ("student_id") REFERENCES "user" ("id");
ALTER TABLE "student_course"
    ADD FOREIGN KEY ("course_id") REFERENCES "course" ("id");
ALTER TABLE "instructor_course"
    ADD FOREIGN KEY ("instructor_id") REFERENCES "user" ("id");
ALTER TABLE "instructor_course"
    ADD FOREIGN KEY ("course_id") REFERENCES "course" ("id");
ALTER TABLE "user_role"
    ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");
ALTER TABLE "user_role"
    ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
