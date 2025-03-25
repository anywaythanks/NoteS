//package com.notes.exceptions.handler;
//
//import com.notes.exceptions.GeneralAccountExistsException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.sql.SQLException;
//
//@ControllerAdvice
//public class ExceptionHandlingController {
//    @ExceptionHandler(GeneralAccountExistsException.class)
//    public ResponseEntity<GeneralAccountExistsException> alreadyExists(GeneralAccountExistsException generalAccountExistsException) {
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .location(ServletUriComponentsBuilder
//                        .fromCurrentRequest().path("/{name}")
//                        .buildAndExpand(generalAccountExistsException
//                                .getGeneralAccount()
//                                .getName()
//                                .getName()).toUri())
//                .body(generalAccountExistsException);
//    }
//
//    @ExceptionHandler(SQLException.class)
//    public ResponseEntity<String> alreadyExists() {
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Database error.");
//    }
//}
