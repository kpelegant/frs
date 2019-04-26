package com.kp.util.kpabe;

import java.io.Serializable;


import it.unisa.dia.gas.jpbc.Element;

/**
 * 密文
 * @author ASUS
 *
 */
public class CT implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	public Element e;
	public CTComp[] comps;
	
}
