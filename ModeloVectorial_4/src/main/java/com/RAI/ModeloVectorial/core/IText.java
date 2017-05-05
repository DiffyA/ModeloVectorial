package com.RAI.ModeloVectorial.core;

/**
 * Interface representing any text handled by the vector space model, 
 * be it the HTML documents that are indexed or the queries that are processed
 * in the model.
 * 
 * @author vdegou
 *
 */
public interface IText {
	
	/**
	 * Retrieves the human-readable content of a document, without html tags.
	 * @return
	 */
	public String getCleanContent();
}
