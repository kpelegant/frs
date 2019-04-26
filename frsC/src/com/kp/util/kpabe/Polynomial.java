package com.kp.util.kpabe;

import it.unisa.dia.gas.jpbc.Element;

public class Polynomial {
	private Element[] coef;
	/**
	 * 构造随机多项式
	 * @param deg
	 * @param zero
	 */
	public Polynomial(int deg, Element zero) {
		// TODO Auto-generated constructor stub
		this.coef = new Element[deg+1];
		this.coef[0] = zero;
		for(int i=1; i<deg+1; i++){
			this.coef[i] = zero.duplicate().setToRandom();
		}
	}
	/**
	 * 计算多项式的值
	 * @param x
	 * @return
	 */
	public Element evalPolynomial(Element x) {
		// TODO Auto-generated method stub
		Element xs = x.duplicate().setToOne();
		Element y = this.coef[0].duplicate();
		for(int i=1; i<coef.length; i++){
			xs.mul(x);
			y.add(this.coef[i].duplicate().mul(xs));
		}
		return y;
	}
	
	
	/**
	 * 计算拉格朗日系数
	 * @param xs 从0开始
	 * @param l 从0开始
	 * @return
	 */
	public static Element lagrangeCoef(Integer[] xs, int i, Element s){
		int j = 0;
		Element t = s.duplicate();
		s.setToOne();
		i = i +1;
		for(int k=0; k<xs.length; k++){
			j = xs[k].intValue() + 1;
			if(j != i){
				t.set(-j);
				s.mul(t);
				t.set(i - j);
				t.invert();
				s.mul(t);
			}
		}
		return s;
	}

}
