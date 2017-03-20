package com.RAI.ModeloVectorial.core;

import com.RAI.ModeloVectorial.Interface.ITexto;

public class Consulta implements ITexto{
	private String content;

	public Consulta(String consulta) {
		this.content = consulta;
	}
	
	public String getCleanContent() {
		return content;
	}
}
