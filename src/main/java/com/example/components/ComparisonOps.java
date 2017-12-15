package com.example.components;

import com.eclipsesource.v8.V8Array;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "compare")
public class ComparisonOps {

	@JSRunnable
	public int findMax(V8Array a) {
		int[] list = a.getIntegers(0, a.length()-1);
		int max = 0;
		for(int l: list) {
			if(l > max)
				max = l;
		}
		return max;
	}
	@JSRunnable
	public int findMin(int... a) {
		return 1;
	}

}
