package com.kp.util.kpabe;

import java.io.ByteArrayInputStream;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.parameters.PropertiesParameters;



public class Kpabe {
	
	private static String curveParams = "type a\n"
			+ "q 87807107996633125224377819847540498158068831994142082"
			+ "1102865339926647563088022295707862517942266222142315585"
			+ "8769582317459277713367317481324925129998224791\n"
			+ "h 12016012264891146079388821366740534204802954401251311"
			+ "822919615131047207289359704531102844802183906537786776\n"
			+ "r 730750818665451621361119245571504901405976559617\n"
			+ "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";
	
	/**
	 * 初始化
	 * @param pk
	 * @param mk
	 */
	public static void setup(PK pk, MK mk) {
		
		int[] U={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		PairingParameters params = new PropertiesParameters().load(new ByteArrayInputStream(curveParams.getBytes()));
		pk.pairingDesc = curveParams;//双线性参数
		pk.pairing = PairingFactory.getPairing(params);
		pk.g = pk.pairing.getG1().newRandomElement();
		mk.y=pk.pairing.getZr().newRandomElement();
		pk.g_hat_y=pk.pairing.pairing(pk.g, pk.g.duplicate().powZn(mk.y));
		mk.ti=new Element[U.length];
		pk.gti=new Element[U.length];
		for (int i = 0; i < U.length; i++) {
			mk.ti[i]=pk.pairing.getZr().newRandomElement();
			pk.gti[i]=pk.g.duplicate().powZn(mk.ti[i]);
		}
		
	}
	
	/**
	 * 密文生成
	 * @param m
	 * @param gama
	 * @param pk
	 * @return
	 */
	public static CT encrypt(Element m, int[] gama, PK pk) {
		CT ct=new CT();
		Element s=pk.pairing.getZr().newRandomElement();
		ct.e = m.duplicate().mul(pk.g_hat_y.duplicate().powZn(s));
		
		//属性
		ct.comps=new CTComp[gama.length];
		for (int i = 0; i < gama.length; i++) {
			ct.comps[i]=new CTComp();
			ct.comps[i].gama=gama[i];
			ct.comps[i].ei=pk.gti[gama[i]-1].duplicate().powZn(s);
			
		}	
		return ct;
		
	}
	
	/**
	 * 私钥获取
	 * @param policy
	 * @param mk
	 * @return
	 */
	public static SK keyGen(String policy,MK mk,PK pk) {
		SK sk=new SK();
		sk.policy=KpabeUtil.generatePolicy(policy);//构建策略树		
		sk.policy.sharePolicy(pk, mk, mk.y);
		return sk;
		
	}
	
	/**
	 * 解密
	 * @param ct
	 * @param sk
	 * @param pk
	 * @return
	 */
	public static Element decrypt(CT ct,SK sk,PK pk) {
		if (sk.policy.checkSatisfy(ct)) {
			Element g_hat_sy=sk.policy.decrypt(pk,ct);
			Element y_s=g_hat_sy.duplicate().invert();
			return ct.e.duplicate().mul(y_s);
		}
		return null;
		
	}
}
