package com.RAI.ModeloVectorial.core;

import java.util.HashSet;
import java.util.Set;

import com.RAI.ModeloVectorial.Interface.ITexto;
import com.RAI.ModeloVectorial.transformacion.Indizador;

public class Consulta implements ITexto{
	private String content;
	private Set<Term> terms = new HashSet<Term>();

	public Consulta(String consulta) {
		this.content = consulta;
		Indizador.processQuery(this);
	}
	
	public String getCleanContent() {
		return content;
	}
	
	public Set<Term> getTerms() {
		return terms;
	}
	
}
