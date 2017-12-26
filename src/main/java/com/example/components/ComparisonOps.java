package com.example.components;

import com.eclipsesource.v8.V8Array;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "compare")
public class ComparisonOps {

	@JSRunnable
	public int findMax(Integer... a) {
		Integer ret = a[0];
		for(Integer i: a)
			if(ret < i)
				ret = i;

		return ret;
	}
	@JSRunnable
	public int findMin(Integer... a) {
		Integer ret = a[0];
		for(Integer i: a)
			if(ret > i)
				ret = i;

		return ret;
	}

}
