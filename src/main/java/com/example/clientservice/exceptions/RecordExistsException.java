package com.example.clientservice.exceptions;

public class RecordExistsException extends RuntimeException {

   public RecordExistsException(String message)
   {
       super(message);
   }
}
