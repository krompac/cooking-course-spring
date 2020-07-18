package com.ag04smarts.scc.validators;

class ErrorCodes {

    static final String ERROR_NOT_FOUND = "not.found";

    static final String ERROR_FIRST_NAME_NULL = "first.name.null";
    static final String ERROR_FIRST_NAME_SHORT = "first.name.short";
    static final String ERROR_FIRST_NAME_LONG = "first.name.long";

    static final String ERROR_LAST_NAME_NULL = "last.name.null";
    static final String ERROR_LAST_NAME_SHORT = "last.name.short";
    static final String ERROR_LAST_NAME_LONG = "last.name.long";

    static final String ERROR_EMAIL_NULL = "email.null";
    static final String ERROR_EMAIL_INVALID = "email.invalid";

    static final String ERROR_PHONE_NUMBER_NULL = "phone.number.null";
    static final String ERROR_PHONE_NUMBER_INVALID = "phone.number.invalid";

    static final String ERROR_AGE_NULL = "age.null";
    static final String ERROR_AGE_YOUNG = "age.too.young";
    static final String ERROR_AGE_OLD = "age.too.old";

    static final String ERROR_GENDER_NULL = "gender.null";
    static final String ERROR_GENDER_INVALID = "gender.invalid";

    static final String ERROR_MENTOR_ID_NULL = "mentor.id.null";
    static final String ERROR_MENTOR_NULL = "mentor.null";

    static final String ERROR_REGISTRATION_NULL = "registration.null";
    static final String ERROR_REGISTRATION_DATE_NULL = "registration.date.null";
    static final String ERROR_REGISTRATION_DATE_BEFORE_TODAY = "registration.date.before.today";

    static final String ERROR_COURSE_NULL = "course.null";
    static final String ERROR_COURSE_TYPE_NULL = "course.type.null";
    static final String ERROR_COURSE_ALREADY_FULL = "course.already.full";

    static final String ERROR_COURSE_NAME_NULL = "course.name.null";
    static final String ERROR_COURSE_NAME_SHORT = "course.name.short";
    static final String ERROR_COURSE_NAME_LONG = "course.name.long";

    static final String ERROR_NUMBER_OF_STUDENTS_NULL = "number.of.students.null";
    static final String ERROR_NUMBER_OF_STUDENTS_LESS_THAN_ONE = "number.of.students.less.than.one";

    static final String ERROR_LECTURERS_NOT_ADDED = "lecturers.not.added";
}
