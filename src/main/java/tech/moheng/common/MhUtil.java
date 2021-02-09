package tech.moheng.common;

import java.util.Collections;
import java.util.List;

import tech.moheng.chain.abi.FunctionEncoder;
import tech.moheng.chain.abi.datatypes.Function;

/**
 * MOHENG common functions.
 */
public class MhUtil {
    
	
	/**
	 * 获取合约方法的data
	 * @param funcName 合约方法名 例 "balanceOf"
	 * @param list 合约方法及参数 列表 例  List list = new ArrayList(); list.add(new Address("0x44c10f4cd26dbb33b0cc3bd8d9fb4e313498cfa0"));
	 * 合约方法参数类型对应JAVA类 参照 com.moheng.chain.abi.datatypes 包下
	 * @return
	 */
	public static String getData(String funcName,List list){
		Function function = new Function(funcName,list,Collections.emptyList());
		String data = FunctionEncoder.encode(function);
		return data;
	}
}
