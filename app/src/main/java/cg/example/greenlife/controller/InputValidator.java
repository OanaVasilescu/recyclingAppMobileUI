package cg.example.greenlife.controller;

import android.widget.EditText;

public class InputValidator {
    public boolean doStringsMatch(String firstString, String secondString) {
        if (firstString == secondString)
            return true;
        return false;
    }

    public void setFieldError(EditText field, String errorMessage){
        field.setError(errorMessage);
        field.requestFocus();
        return; // not sure if  this is needed
    }
}
