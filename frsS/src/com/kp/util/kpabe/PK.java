package com.kp.util.kpabe;

import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 *
 * 系统公钥
 *
 */
public class PK implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	public String pairingDesc;
	public Pairing pairing;
	public Element g;
	public Element[] gti;
	public Element g_hat_y; 
}
