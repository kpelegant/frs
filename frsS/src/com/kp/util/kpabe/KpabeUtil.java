package com.kp.util.kpabe;

import java.util.ArrayList;

public class KpabeUtil {
	public static Policy generatePolicy(String s) {
		ArrayList<Policy> stack=new ArrayList<Policy>();
		String[] toks = s.split(" ");
		for (int index = 0; index < toks.length; index++) {
			if (!toks[index].contains("of")) {
				stack.add(baseNode(1,Integer.parseInt(toks[index])));
			} else {
				String[] k_n = toks[index].split("of"); 
				int k = Integer.parseInt(k_n[0]);
				int n = Integer.parseInt(k_n[1]);
				if (k < 1) {
					System.out.println("error parsing " + s
							+ ": trivially satisfied operator " + toks[index]);
					return null;
				} else if (k > n) {
					System.out.println("error parsing " + s
							+ ": unsatisfiable operator " + toks[index]);
					return null;
				} else if (n == 1) {
					System.out.println("error parsing " + s
							+ ": indentity operator " + toks[index]);
					return null;
				} else if (n > stack.size()) {
					System.out.println("error parsing " + s
							+ ": stack underflow at " + toks[index]);
					return null;
				}
				Policy node=baseNode(k,-1);
				node.children=new Policy[n];
				for (int i = n - 1; i >= 0; i--){
					node.children[i] = stack.remove(stack.size() - 1);
				}
				stack.add(node);
			}
		}
		
		if (stack.size() > 1) {
			System.out.println("error parsing " + s
					+ ": extra node left on the stack");
			return null;
		} else if (stack.size() < 1) {
			System.out.println("error parsing " + s + ": empty policy");
			return null;
		}
		return stack.get(0);
		
	}
	
	private static Policy baseNode(int k,int gama) {
		Policy policy=new Policy();
		policy.k=k;
		policy.gama=gama;
		return policy;
		
	}
}
