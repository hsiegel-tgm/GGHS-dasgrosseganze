package com.example.myproject2;

import com.vaadin.data.validator.AbstractValidator;

public class CustomValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;

    public CustomValidator(String errorMessage) {
    	super(errorMessage);
    }

    @Override
    protected boolean isValidValue(String value) {
    	if(value.equals("")){
    		return false;
    	}
    	else
    		return true;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}