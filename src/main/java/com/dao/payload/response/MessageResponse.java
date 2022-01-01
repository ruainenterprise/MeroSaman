package com.dao.payload.response;

import java.util.List;

public class MessageResponse {
  private String message;

  private String returnNotes;
  
  private int returncode;
  
  private List<?> returnValue;
  
  public MessageResponse() {
	super();
  }

  private Object valueObject;
  
  public String getReturnNotes() {
	return returnNotes;
}

public void setReturnNotes(String returnNotes) {
	this.returnNotes = returnNotes;
}

public int getReturncode() {
	return returncode;
}

public void setReturncode(int returncode) {
	this.returncode = returncode;
}

public List<?> getReturnValue() {
	return returnValue;
}

public void setReturnValue(List<?> returnValue) {
	this.returnValue = returnValue;
}

public Object getValueObject() {
	return valueObject;
}

public void setValueObject(Object valueObject) {
	this.valueObject = valueObject;
}

public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
