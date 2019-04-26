package com.kp.util.kpabe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import it.unisa.dia.gas.jpbc.Element;



public class Policy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int k;//阈值
	public Policy[] children;
	
	public int gama;
	public Element di;
	
	
	//解密时使用
	private Boolean satisfy = null;//此节点是否满足解密条件
	private Integer[] satisfyMinIndex = null;//满足时解密需要的最小属性个数的孩子节点index
	private Integer satisfyNum = null;//满足时解密需要的最小属性个数
	private Integer SKAttrIndex = null;//私钥对应属性位置（叶子节点）
	
	protected void setChildren(Policy[] children){
		this.children = children;
	}
	
	public void sharePolicy(PK pk, MK mk,Element y) {
		// TODO Auto-generated method stub
		if (this.children == null || this.children.length == 0 || this.gama!=-1) {
			this.di = pk.g.duplicate().powZn(y.duplicate().div(mk.ti[this.gama-1]));
		} else {
			Polynomial poly = new Polynomial(this.k-1, y);
			Element index = pk.pairing.getZr().newElement();
			for(int i=0; i<this.children.length; i++){
				index.set(i+1);
				this.children[i].sharePolicy(pk,mk, poly.evalPolynomial(index));
			}
			
		}
	}

	public boolean checkSatisfy(CT ct) {
		// TODO Auto-generated method stub
		this.satisfy=false;
		int i;
		if (this.children == null || this.children.length == 0) {
			for(i=0; i<ct.comps.length; i++){
				if(this.gama == ct.comps[i].gama){
					this.satisfy = true;
					this.SKAttrIndex = i;
					this.satisfyMinIndex = null;
					this.satisfyNum = 1;
					break;
				}
			}
		}else {
			int satisfy=0;
			for (i = 0; i < this.children.length; i++) {
				if (this.children[i].checkSatisfy(ct)) {
					++satisfy;
				}
			}
			this.satisfy = satisfy >= this.k ? true : false;
			if(this.satisfy){//如果满足，则计算哪些孩子节点需要计算的属性较少
				ArrayList<Integer> c = new ArrayList<Integer>();
				for (i = 0; i < this.children.length; i++){
					c.add(new Integer(i));
				}
				Collections.sort(c, new IntegerComparator(this));
				this.satisfyMinIndex = new Integer[this.k];
				this.satisfyNum = 0;
				for(i=0; i<this.k; i++){
					this.satisfyMinIndex[i] = c.get(i);
					this.satisfyNum += this.children[this.satisfyMinIndex[i]].satisfyNum;
				}
			}
		}
		return this.satisfy;
	}
	/**
	 * 最小属性数量比较器
	 */
	private static class IntegerComparator implements Comparator<Integer> {
		Policy policy;
		public IntegerComparator(Policy p) {
			this.policy = p;
		}
		@Override
		public int compare(Integer o1, Integer o2) {
			if(policy.children[o1].satisfy && policy.children[o2].satisfy){
				return policy.children[o1].satisfyNum < policy.children[o2].satisfyNum ? -1 : 1;
			}else{
				return policy.children[o1].satisfy ? -1 : 1;
			}
		}
	}
	public Element decrypt(PK pk, CT ct) {
		if (this.children == null || this.children.length == 0) {
			return pk.pairing.pairing(this.di,ct.comps[SKAttrIndex].ei);
		}else {
			Element y = pk.pairing.getGT().newOneElement();
			for(int i=0; i<this.k; i++){
				Element r = this.children[this.satisfyMinIndex[i]].decrypt(pk, ct);
				Element lc =  Polynomial.lagrangeCoef(this.satisfyMinIndex, this.satisfyMinIndex[i], pk.pairing.getZr().newElement());
				r.powZn(lc);
				y.mul(r);
			}
			return y;
		}
	}
}
