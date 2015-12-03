package com.nowtv.pav.test.steps;

import com.carrotsearch.hppc.CharIntOpenHashMap;

import java.util.Arrays;

public abstract class NumberSystem {
	
    protected char[] digits;
	protected int base;
	protected CharIntOpenHashMap map;
	
	protected NumberSystem(char[] digits) {
		this.digits = digits;
		this.base = digits.length;
		
		this.map = new CharIntOpenHashMap(base);
		for (int i=0; i<base; i++) {
			map.put(digits[i], i);
		}
	}
	
	public char[] toBase(long n, int len) {
		char[] buf = new char[len];
		Arrays.fill(buf, digits[0]);
		int charPos = len;
    	for (long d=n; d!=0; d=d/base) {
    		buf[--charPos] = digits[(int) (d%base)];
    	}
    	return buf;
	}
	
	// returns -1 instead of NumberFormatException (~twice as fast)
	public long fromBase(char[] buf) {
		long l = 0L;
        for (char aBuf : buf) {
            l *= base;
            int val = map.getOrDefault(aBuf, -1);
            if (val == -1) {
            	return -1L;
            }
            l += val;
        }
		return l;
	}

	public int getBase() {
		return base;
	}
	
}
