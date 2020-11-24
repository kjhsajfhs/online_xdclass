package xyz.kyjef.online_xdclass.utils;

public class StringUtit {
	public static boolean isNotEmptyString(Object o){
		if(o!=null){
			if(o.toString().trim().length()>0){
				return true;
			}else return false;
		}else
			return false;
	}
	public static String valueOf(Object obj)
    {
        return obj == null ? "" : obj.toString().trim();
    }

    public static String removeAllBlank(String s){
		String result = "";
		if(null!=s && !"".equals(s)){
			result = s.replaceAll("[　*| *| *|//s*]*", "");
		}
		return result;
	}

	/**
	 * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
	 * @param s
	 * @return
	 */
	public static String trim(String s){
		String result = "";
		if(null!=s && !"".equals(s)){
			result = s.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
		}
		return result;
	}


	public static String trimChar(String s){
		String result = "";
		if(null!=s && !"".equals(s)){
			result = s.replaceAll("　", " ");
		}
		return result;
	}
	public static void main(String[] args) {
		String s="ssss　sdfasdf";
			System.out.println(trimChar(s));
	}
}
