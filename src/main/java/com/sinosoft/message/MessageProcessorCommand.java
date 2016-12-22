package com.sinosoft.message;

public interface MessageProcessorCommand {
	public boolean execute(String messageBody);
}
