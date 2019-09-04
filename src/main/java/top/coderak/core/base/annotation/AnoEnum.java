package top.coderak.core.base.annotation;

// 类型
public enum AnoEnum {

	ADD, UPDATE, DELETE, SELECT, DEFAULT;

	public static String getTypeString(AnoEnum str) {

		switch (str) {

		case ADD:

			return "新增...!@#$%^&";

		case SELECT:

			return "查询...!@#$%^&";

		case UPDATE:

			return "更新...!@#$%^&";

		case DELETE:

			return "删除...!@#$%^&";

		case DEFAULT:

			return "进入Controller";

		default:

			return "进入Controller";
		}
	}
}